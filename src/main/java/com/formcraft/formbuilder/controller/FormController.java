package com.formcraft.formbuilder.controller;

import com.formcraft.formbuilder.model.Form;
import com.formcraft.formbuilder.model.SaveFormRequest;
import com.formcraft.formbuilder.model.SaveFormResponse;
import com.formcraft.formbuilder.service.FormService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

/**
 * Maps all routes from the Node.js/Express server.js to Spring MVC:
 *
 *   GET  /                    → renders builder view        (was res.render('builder'))
 *   POST /api/forms/save      → save a form                 (was app.post)
 *   GET  /api/forms           → list all forms              (was app.get)
 *   GET  /api/forms/:id       → get one form                (was app.get)
 *   GET  /preview/:id         → renders preview view        (was res.render('preview'))
 */
@Controller
public class FormController {

    private final FormService formService;
    private final ObjectMapper objectMapper;

    public FormController(FormService formService, ObjectMapper objectMapper) {
        this.formService = formService;
        this.objectMapper = objectMapper;
    }

    // -----------------------------------------------------------------------
    // Page routes
    // -----------------------------------------------------------------------

    /**
     * GET /  →  renders builder.html (Thymeleaf, equivalent to EJS builder.ejs)
     */
    @GetMapping("/")
    public String builderPage() {
        return "builder";
    }

    /**
     * GET /preview/:id  →  renders preview.html with the form model
     * Mirrors: res.render('preview', { form })
     */
    @GetMapping("/preview/{id}")
    public String previewPage(@PathVariable String id, Model model) {
        return formService.findById(id)
                .map(form -> {
                    model.addAttribute("form", form);

                    // Pre-serialize schema to JSON string for inline <script> injection,
                    // equivalent to EJS's <%- JSON.stringify(form.schema) %>
                    try {
                        model.addAttribute("schemaJson",
                                objectMapper.writeValueAsString(form.getSchema()));
                    } catch (JsonProcessingException e) {
                        model.addAttribute("schemaJson", "[]");
                    }
                    return "preview";
                })
                .orElse("error/404");
    }

    // -----------------------------------------------------------------------
    // REST API routes
    // -----------------------------------------------------------------------

    /**
     * POST /api/forms/save
     * Body: { id?, name?, schema, formsJson? }
     * Returns: { success: true, id: "..." }
     */
    @PostMapping("/api/forms/save")
    @ResponseBody
    public ResponseEntity<SaveFormResponse> saveForm(@RequestBody SaveFormRequest request) {
        Form saved = formService.save(request);
        return ResponseEntity.ok(new SaveFormResponse(true, saved.getId()));
    }

    /**
     * GET /api/forms
     * Returns all forms as a JSON array.
     * Mirrors: res.json(Object.values(forms))
     */
    @GetMapping("/api/forms")
    @ResponseBody
    public ResponseEntity<Collection<Form>> listForms() {
        return ResponseEntity.ok(formService.findAll());
    }

    /**
     * GET /api/forms/:id
     * Returns one form or 404.
     * Mirrors: if (!form) return res.status(404).json(...)
     */
    @GetMapping("/api/forms/{id}")
    @ResponseBody
    public ResponseEntity<?> getForm(@PathVariable String id) {
        return formService.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).body(Map.of("error", "Form not found")));
    }
}
