package com.formcraft.formbuilder.service;

import com.formcraft.formbuilder.model.Form;
import com.formcraft.formbuilder.model.SaveFormRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**

 *
 * Note: Data is held in JVM memory and is lost on restart.
 * To persist across restarts, replace the LinkedHashMap with a JPA repository
 * backed by H2 / PostgreSQL / MySQL (see README for migration guide).
 */
@Service
public class FormService {

    /** Insertion-ordered map so GET /api/forms returns forms in creation order. */
    private final Map<String, Form> forms = new LinkedHashMap<>();

    /**
     * Save (create or update) a form.
     * Mirrors: forms[formId] = { id, name, schema, formsJson, updatedAt }
     */
    public Form save(SaveFormRequest req) {
        String formId = (req.getId() != null && !req.getId().isBlank())
                ? req.getId()
                : "form_" + System.currentTimeMillis();

        Form form = new Form();
        form.setId(formId);
        form.setName(req.getName() != null && !req.getName().isBlank()
                ? req.getName() : "Untitled Form");
        form.setSchema(req.getSchema());
        form.setformsJson(req.getformsJson());
        form.setUpdatedAt(Instant.now());

        forms.put(formId, form);
        return form;
    }

    /**
     * Return all forms. Mirrors: Object.values(forms)
     */
    public Collection<Form> findAll() {
        return forms.values();
    }

    /**
     * Find a form by ID. Mirrors: forms[req.params.id]
     */
    public Optional<Form> findById(String id) {
        return Optional.ofNullable(forms.get(id));
    }
}
