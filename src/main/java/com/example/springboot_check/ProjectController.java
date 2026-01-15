package com.example.springboot_check;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for project-related endpoints.
 * Uses DTOs to avoid exposing JPA entities directly.
 */
@RestController
@RequestMapping("/projects")
@CrossOrigin(origins = "*") // allow frontend to access
public class ProjectController {
    
    private final ProjectService projectService;
    private final ProjectRequestService requestService;

    public ProjectController(ProjectService projectService, ProjectRequestService requestService) {
        this.projectService = projectService;
        this.requestService = requestService;
    }

    /**
     * Upload a new project.
     * Uses ProjectCreateRequest DTO with validation instead of exposing ProjectEntity.
     */
    @PostMapping("/upload")
    public ProjectDTO uploadProject(@Valid @RequestBody ProjectCreateRequest request) {
        return projectService.saveProject(request);
    }

    /**
     * Get all projects uploaded by a specific user.
     * Returns ProjectDTO list instead of exposing ProjectEntity.
     */
    @GetMapping("/user/{owner}")
    public List<ProjectDTO> getUserProjects(@PathVariable String owner) {
        return projectService.getProjectsByOwner(owner);
    }

    /**
     * Send a collaboration request for a project.
     * Uses CollaborationRequestDTO with validation instead of Map<String, String>.
     */
    @PostMapping("/request")
    public CollaborationRequestResponseDTO sendRequest(@Valid @RequestBody CollaborationRequestDTO requestDTO) {
        return requestService.createOrUpdateRequest(requestDTO);
    }

    /**
     * Get all projects with request status for a requester.
     * Already uses ProjectResponse DTO (no change needed).
     */
    @GetMapping("/all")
    public List<ProjectResponse> getAllProjectsWithStatus(@RequestParam String requester) {
        // Delegate to service layer for complex business logic
        return projectService.getAllProjectsWithRequestStatus(requester);
    }

    /**
     * Get all collaboration requests for a project owner.
     * Returns CollaborationRequestResponseDTO list instead of exposing ProjectRequest entity.
     */
    @GetMapping("/requests/{owner}")
    public List<CollaborationRequestResponseDTO> getRequestsForOwner(@PathVariable String owner) {
        return requestService.getRequestsForOwner(owner);
    }

    /**
     * Accept a collaboration request.
     * Returns CollaborationRequestResponseDTO instead of exposing ProjectRequest entity.
     */
    @PutMapping("/requests/accept/{id}")
    public CollaborationRequestResponseDTO acceptRequest(@PathVariable Long id) {
        return requestService.acceptRequest(id);
    }

    /**
     * Reject a collaboration request.
     * Returns CollaborationRequestResponseDTO instead of exposing ProjectRequest entity.
     */
    @PutMapping("/requests/reject/{id}")
    public CollaborationRequestResponseDTO rejectRequest(@PathVariable Long id) {
        return requestService.rejectRequest(id);
    }

}



