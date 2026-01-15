package com.example.springboot_check;

import jakarta.persistence.*;

@Entity
public class ProjectRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String requester; // username who clicked request
    private String owner;     // project owner
    private Long projectId;   // project ID
    private String status;    // "pending", "accepted", "rejected"

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getRequester() { return requester; }
    public void setRequester(String requester) { this.requester = requester; }

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

    public Long getProjectId() { return projectId; }
    public void setProjectId(Long projectId) { this.projectId = projectId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
