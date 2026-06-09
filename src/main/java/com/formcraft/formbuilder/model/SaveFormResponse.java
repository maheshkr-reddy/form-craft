package com.formcraft.formbuilder.model;

/**
 * Response body for POST /api/forms/save.
 * Mirrors Node.js: { success: true, id: "..." }
 */
public class SaveFormResponse {

    private boolean success;
    private String id;

    public SaveFormResponse() {}

    public SaveFormResponse(boolean success, String id) {
        this.success = success;
        this.id = id;
    }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
}
