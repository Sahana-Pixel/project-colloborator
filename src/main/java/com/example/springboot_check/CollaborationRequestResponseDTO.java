package com.example.springboot_check;

/**
 * Response DTO for collaboration request information.
 * Used in REST API to avoid exposing JPA entities directly.
 */
public class CollaborationRequestResponseDTO {
    private Long id;
    private String requester;
    private String owner;
    private Long projectId;
    private String status; // "pending", "accepted", "rejected"

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
