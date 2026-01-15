package com.example.springboot_check;

public class ProjectResponse {
    private Long id;
    private String title;
    private String link;
    private String description;
    private String tech;
    private String owner;
    private String requestStatus; // "pending", "accepted", "rejected", "none"

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getTech() { return tech; }
    public void setTech(String tech) { this.tech = tech; }

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

    public String getRequestStatus() { return requestStatus; }
    public void setRequestStatus(String requestStatus) { this.requestStatus = requestStatus; }
}
