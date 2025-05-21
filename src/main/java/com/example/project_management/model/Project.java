// src/main/java/com/example/project_management/model/Project.java
package com.example.project_management.model;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "projects")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Unique project key (e.g. “PROJ”) */
    @Column(name = "project_key", unique = true, nullable = false)
    private String projectKey;

    /** Human-readable name */
    @Column(nullable = false)
    private String name;

    /** Optional description */
    @Column(length = 2000)
    private String description;

    /** The user who leads/owns this project */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "lead_id", nullable = false)
    private User lead;

    /** Who created this project */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    /** When it was created */
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
    }
}
