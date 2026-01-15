package com.example.springboot_check;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service layer for project collaboration request business logic.
 * Handles request creation, retrieval, and status management.
 */
@Service
public class ProjectRequestService {

    private final ProjectRequestRepository requestRepository;
    private final ProjectRepository projectRepository;

    public ProjectRequestService(ProjectRequestRepository requestRepository, ProjectRepository projectRepository) {
        this.requestRepository = requestRepository;
        this.projectRepository = projectRepository;
    }

    /**
     * Creates or updates a collaboration request for a project.
     * 
     * Business Logic Moved from ProjectController.sendRequest():
     * - Validates that project exists
     * - Checks if request already exists for this requester and project
     * - If existing request is "rejected", updates it to "pending"
     * - If existing request is "pending" or "accepted", returns existing request
     * - If no request exists, creates a new request with "pending" status
     * - Converts entity to DTO for response
     * 
     * This encapsulates the complex logic of handling duplicate requests and status transitions.
     * 
     * @param requestDTO CollaborationRequestDTO containing requester and projectId
     * @return CollaborationRequestResponseDTO (newly created or existing)
     * @throws RuntimeException if project not found
     */
    public CollaborationRequestResponseDTO createOrUpdateRequest(CollaborationRequestDTO requestDTO) {
        String requester = requestDTO.getRequester();
        Long projectId = requestDTO.getProjectId();
        
        // Fetch project to get owner
        ProjectEntity project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        // Check if request already exists
        ProjectRequest existing = requestRepository.findByProjectIdAndRequester(projectId, requester);

        if (existing != null) {
            if (existing.getStatus().equals("rejected")) {
                // Update rejected request to pending (allows re-requesting)
                existing.setStatus("pending");
                ProjectRequest updated = requestRepository.save(existing);
                return convertToDTO(updated);
            } else {
                // Already pending or accepted → return existing request
                return convertToDTO(existing);
            }
        }

        // Create new request if none exists
        ProjectRequest request = new ProjectRequest();
        request.setRequester(requester);
        request.setOwner(project.getOwner());
        request.setProjectId(projectId);
        request.setStatus("pending");

        ProjectRequest saved = requestRepository.save(request);
        return convertToDTO(saved);
    }
    
    /**
     * Converts ProjectRequest entity to CollaborationRequestResponseDTO.
     */
    private CollaborationRequestResponseDTO convertToDTO(ProjectRequest request) {
        CollaborationRequestResponseDTO dto = new CollaborationRequestResponseDTO();
        dto.setId(request.getId());
        dto.setRequester(request.getRequester());
        dto.setOwner(request.getOwner());
        dto.setProjectId(request.getProjectId());
        dto.setStatus(request.getStatus());
        return dto;
    }

    /**
     * Retrieves all collaboration requests for a project owner.
     * 
     * Business Logic Moved from ProjectController.getRequestsForOwner():
     * - Query requests by owner
     * - Converts entities to DTOs
     * 
     * @param owner Username of the project owner
     * @return List of CollaborationRequestResponseDTO objects
     */
    public List<CollaborationRequestResponseDTO> getRequestsForOwner(String owner) {
        List<ProjectRequest> requests = requestRepository.findByOwner(owner);
        return requests.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Accepts a collaboration request by updating its status to "accepted".
     * 
     * Business Logic Moved from ProjectController.acceptRequest():
     * - Validates request exists
     * - Updates status to "accepted"
     * - Converts entity to DTO
     * 
     * @param requestId ID of the request to accept
     * @return Updated CollaborationRequestResponseDTO
     * @throws RuntimeException if request not found
     */
    public CollaborationRequestResponseDTO acceptRequest(Long requestId) {
        ProjectRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        request.setStatus("accepted");
        ProjectRequest updated = requestRepository.save(request);
        return convertToDTO(updated);
    }

    /**
     * Rejects a collaboration request by updating its status to "rejected".
     * 
     * Business Logic Moved from ProjectController.rejectRequest():
     * - Validates request exists
     * - Updates status to "rejected"
     * - Converts entity to DTO
     * 
     * @param requestId ID of the request to reject
     * @return Updated CollaborationRequestResponseDTO
     * @throws RuntimeException if request not found
     */
    public CollaborationRequestResponseDTO rejectRequest(Long requestId) {
        ProjectRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        request.setStatus("rejected");
        ProjectRequest updated = requestRepository.save(request);
        return convertToDTO(updated);
    }
}
