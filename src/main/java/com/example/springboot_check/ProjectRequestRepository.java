package com.example.springboot_check;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProjectRequestRepository extends JpaRepository<ProjectRequest, Long> {
    List<ProjectRequest> findByOwner(String owner);
    List<ProjectRequest> findByProjectId(Long projectId);
    ProjectRequest findByProjectIdAndRequester(Long projectId, String requester);
}
