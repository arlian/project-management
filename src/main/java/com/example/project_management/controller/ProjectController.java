package com.example.project_management.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project_management.model.Project;
import com.example.project_management.payload.request.CreateProjectRequest;
import com.example.project_management.payload.response.CreateProjectResponse;
import com.example.project_management.payload.response.ProjectSummaryResponse;
import com.example.project_management.security.MyUserDetails;
import com.example.project_management.service.ProjectService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<CreateProjectResponse> createProject(
            @Valid @RequestBody CreateProjectRequest req) {

        MyUserDetails me = (MyUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        Project project = projectService.createProject(req, me.getId());

        CreateProjectResponse resp = new CreateProjectResponse(
                project.getId(),
                project.getProjectKey(),
                project.getName(),
                project.getDescription(),
                project.getLead().getId(),
                project.getCreatedBy().getId(),
                project.getCreatedAt()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(resp);
    }

    @GetMapping
    public ResponseEntity<Page<ProjectSummaryResponse>> listProjects(
            @PageableDefault(size = 20, sort = "createdAt", direction = org.springframework.data.domain.Sort.Direction.ASC) Pageable pageable) {

        Page<Project> page = projectService.listProjects(pageable);

        Page<ProjectSummaryResponse> dtoPage = page.map(p -> new ProjectSummaryResponse(
                p.getId(),
                p.getProjectKey(),
                p.getName(),
                p.getDescription(),
                p.getLead().getId(),
                p.getLead().getName(), // ← lead's name
                p.getCreatedBy().getId(),
                p.getCreatedBy().getName(), // ← creator's name
                p.getCreatedAt()
        ));

        return ResponseEntity.ok(dtoPage);
    }

    @GetMapping("/{id}")

    public ResponseEntity<ProjectSummaryResponse> getProject(@PathVariable Long id) {
        Project p = projectService.getProject(id);
        return ResponseEntity.ok(new ProjectSummaryResponse(
                p.getId(),
                p.getProjectKey(),
                p.getName(),
                p.getDescription(),
                p.getLead().getId(),
                p.getLead().getName(),
                p.getCreatedBy().getId(),
                p.getCreatedBy().getName(),
                p.getCreatedAt()
        ));
    }
}
