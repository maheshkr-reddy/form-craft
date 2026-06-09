package com.formcraft.formbuilder.model;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Request body for POST /api/forms/save.
 * Maps 1-to-1 to the body the Node.js server accepted:
 * { id?, name?, schema, formsJson? }
 */
public class SaveFormRequest {

    private String id;
    private String name;
    private JsonNode schema;
    private JsonNode formsJson;

    public SaveFormRequest() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public JsonNode getSchema() { return schema; }
    public void setSchema(JsonNode schema) { this.schema = schema; }

    public JsonNode getformsJson() { return formsJson; }
    public void setformsJson(JsonNode formsJson) { this.formsJson = formsJson; }
}
