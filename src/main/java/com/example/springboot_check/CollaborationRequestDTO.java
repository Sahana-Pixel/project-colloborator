package com.example.springboot_check;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO for creating a collaboration request.
 * Used in REST API to avoid exposing JPA entities directly.
 */
public class CollaborationRequestDTO {

    @NotBlank(message = "Requester username is required")
    private String requester;

    @NotNull(message = "Project ID is required")
    private Long projectId;

    // Getters and Setters
    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}
