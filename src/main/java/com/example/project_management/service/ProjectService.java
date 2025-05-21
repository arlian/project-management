package com.example.project_management.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project_management.exception.InvalidOperationException;
import com.example.project_management.exception.ResourceNotFound;
import com.example.project_management.model.Project;
import com.example.project_management.model.User;
import com.example.project_management.payload.request.CreateProjectRequest;
import com.example.project_management.repository.ProjectRepository;
import com.example.project_management.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Transactional
    public Project createProject(CreateProjectRequest req, Long userId) {
        if (projectRepository.existsByProjectKey(req.projectKey())) {
            throw new InvalidOperationException(
                    "Project key already in use: " + req.projectKey());
        }

        // get a lazy proxy to avoid an immediate SELECT
        User user = userRepository.getReferenceById(userId);

        Project project = Project.builder()
                .projectKey(req.projectKey().toUpperCase())
                .name(req.name())
                .description(req.description())
                .lead(user)
                .createdBy(user)
                .build();

        return projectRepository.save(project);
    }

    @Transactional(readOnly = true)
    public Page<Project> listProjects(Pageable pageable) {
        return projectRepository.findAll(pageable);
    }

    @Transactional
    public Project getProject(Long id){
        return projectRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Project", id));
    }
}
