package com.formcraft.formbuilder.model;

import com.fasterxml.jackson.databind.JsonNode;

import java.time.Instant;

/**
 * Represents a saved form,
 * { id, name, schema, formsJson, updatedAt }
 *
 * Getters/setters are written explicitly — no Lombok — so the class
 * compiles on any Java version including Java 25.
 */
public class Form {

    private String id;
    private String name;
    private JsonNode schema;
    private JsonNode formsJson;
    private Instant updatedAt;

    public Form() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public JsonNode getSchema() { return schema; }
    public void setSchema(JsonNode schema) { this.schema = schema; }

    public JsonNode getformsJson() { return formsJson; }
    public void setformsJson(JsonNode formsJson) { this.formsJson = formsJson; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
