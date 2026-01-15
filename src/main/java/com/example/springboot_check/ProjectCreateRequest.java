package com.example.springboot_check;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO for creating a new project.
 * Used in REST API to avoid exposing JPA entities directly.
 */
public class ProjectCreateRequest {

    @NotBlank(message = "Project title is required")
    @Size(max = 200, message = "Title must not exceed 200 characters")
    private String title;

    @NotBlank(message = "GitHub repository link is required")
    @Size(max = 500, message = "Link must not exceed 500 characters")
    private String link;

    @NotBlank(message = "Project description is required")
    @Size(max = 2000, message = "Description must not exceed 2000 characters")
    private String description;

    @NotBlank(message = "Tech stack is required")
    @Size(max = 200, message = "Tech stack must not exceed 200 characters")
    private String tech;

    @NotBlank(message = "Owner username is required")
    private String owner;

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTech() {
        return tech;
    }

    public void setTech(String tech) {
        this.tech = tech;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
