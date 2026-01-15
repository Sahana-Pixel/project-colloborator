package com.example.springboot_check;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service layer for project-related business logic.
 * Handles project creation, retrieval, and status aggregation operations.
 */
@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectRequestRepository requestRepository;

    public ProjectService(ProjectRepository projectRepository, ProjectRequestRepository requestRepository) {
        this.projectRepository = projectRepository;
        this.requestRepository = requestRepository;
    }

    /**
     * Saves a new project to the database.
     * 
     * Business Logic Moved from ProjectController.uploadProject():
     * - Converts DTO to entity
     * - Saves project to database
     * - Returns DTO representation
     * 
     * @param request ProjectCreateRequest DTO
     * @return ProjectDTO representation of saved project
     */
    public ProjectDTO saveProject(ProjectCreateRequest request) {
        // Convert DTO to entity
        ProjectEntity project = new ProjectEntity();
        project.setTitle(request.getTitle());
        project.setLink(request.getLink());
        project.setDescription(request.getDescription());
        project.setTech(request.getTech());
        project.setOwner(request.getOwner());
        
        // Save to database
        ProjectEntity savedProject = projectRepository.save(project);
        
        // Convert entity to DTO
        return convertToDTO(savedProject);
    }
    
    /**
     * Converts ProjectEntity to ProjectDTO.
     */
    private ProjectDTO convertToDTO(ProjectEntity project) {
        ProjectDTO dto = new ProjectDTO();
        dto.setId(project.getId());
        dto.setTitle(project.getTitle());
        dto.setLink(project.getLink());
        dto.setDescription(project.getDescription());
        dto.setTech(project.getTech());
        dto.setOwner(project.getOwner());
        return dto;
    }

    /**
     * Retrieves all projects uploaded by a specific owner.
     * 
     * Business Logic Moved from ProjectController.getUserProjects():
     * - Query projects by owner
     * - Converts entities to DTOs
     * 
     * @param owner Username of the project owner
     * @return List of ProjectDTO objects owned by the specified user
     */
    public List<ProjectDTO> getProjectsByOwner(String owner) {
        List<ProjectEntity> projects = projectRepository.findByOwner(owner);
        return projects.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all projects with their collaboration request status for a specific requester.
     * 
     * Business Logic Moved from ProjectController.getAllProjectsWithStatus():
     * - Fetches all projects from database
     * - For each project, checks if requester has sent a collaboration request
     * - Builds ProjectResponse DTOs with project info and request status
     * - Returns "none" if no request exists, otherwise returns request status
     * 
     * This is complex business logic that combines project and request data.
     * 
     * @param requester Username of the user requesting the projects
     * @return List of ProjectResponse objects with project info and request status
     */
    public List<ProjectResponse> getAllProjectsWithRequestStatus(String requester) {
        List<ProjectEntity> projects = projectRepository.findAll();
        List<ProjectResponse> responseList = new ArrayList<>();

        for (ProjectEntity project : projects) {
            ProjectResponse response = new ProjectResponse();
            response.setId(project.getId());
            response.setTitle(project.getTitle());
            response.setDescription(project.getDescription());
            response.setTech(project.getTech());
            response.setOwner(project.getOwner());
            response.setLink(project.getLink());

            // Fetch request if exists for this requester
            ProjectRequest request = requestRepository.findByProjectIdAndRequester(project.getId(), requester);

            if (request != null) {
                response.setRequestStatus(request.getStatus());   // pending / accepted / rejected
            } else {
                response.setRequestStatus("none");                // no request yet
            }

            responseList.add(response);
        }

        return responseList;
    }

    /**
     * Finds a project by ID.
     * 
     * @param projectId Project ID
     * @return ProjectEntity
     * @throws RuntimeException if project not found
     */
    public ProjectEntity findProjectById(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
    }
}
