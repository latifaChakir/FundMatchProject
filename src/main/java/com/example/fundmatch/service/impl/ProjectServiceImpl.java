package com.example.fundmatch.service.impl;

import com.example.fundmatch.domain.dtos.request.project.CreateProjectRequestDto;
import com.example.fundmatch.domain.entities.Project;
import com.example.fundmatch.domain.entities.Startup;
import com.example.fundmatch.domain.entities.User;
import com.example.fundmatch.domain.enums.ProjectStatus;
import com.example.fundmatch.domain.mappers.ProjectMapper;
import com.example.fundmatch.domain.vm.ProjectResponseVM;
import com.example.fundmatch.repository.ProjectRepository;
import com.example.fundmatch.repository.StartupRepository;
import com.example.fundmatch.service.interfaces.AuthService;
import com.example.fundmatch.service.interfaces.ProjectService;
import com.example.fundmatch.shared.exception.ProjectNotFoundException;
import com.example.fundmatch.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final AuthService authService;
    private final StartupRepository startupRepository;

    @Override
    public ProjectResponseVM saveProject(CreateProjectRequestDto projectRequest) {
        User currentUser = authService.getAuthenticatedUser();
        System.out.println(currentUser);
        Startup startup = startupRepository.findByUserId(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No startup associated with the current user."));
        Project project = projectMapper.toEntity(projectRequest);
        project.setStartup(startup);
        project.setStatus(ProjectStatus.PENDING);
        Project savedProject = projectRepository.save(project);
        return projectMapper.toDto(savedProject);
    }

    @Override
    public ProjectResponseVM getProjectById(Long id) {
        Optional<Project> project = projectRepository.findById(id);
        if (project.isEmpty()) {
            throw new ProjectNotFoundException("Project not found.");
        }
        return projectMapper.toDto(project.get());
    }

    @Override
    public ProjectResponseVM updateProject(CreateProjectRequestDto projectRequest, Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found."));
        project.setTitle(projectRequest.getTitle());
        project.setDescription(projectRequest.getDescription());
        project.setFundingAmount(projectRequest.getFundingAmount());
        project.setCreatedAt(projectRequest.getCreatedAt());
        project.setStage(projectRequest.getStage());
        project.setViewCount(projectRequest.getViewCount());

        Project updatedProject = projectRepository.save(project);
        return projectMapper.toDto(updatedProject);
    }

    @Override
    public void deleteProject(Long id) {
        Optional<Project> project = projectRepository.findById(id);
        if (project.isEmpty()) {
            throw new ProjectNotFoundException("Project not found.");
        }
        projectRepository.delete(project.get());
    }

    @Override
    public List<ProjectResponseVM> getProjects() {
        List<Project> projects = projectRepository.findAll();
        return projectMapper.toDtoList(projects);
    }
    @Override
    public ProjectResponseVM updateStatus(Long projectId){
        System.out.println("id"+projectId);
        Optional<Project> projectOptional = projectRepository.findById(projectId);
        if (projectOptional.isEmpty()) {
            throw new ProjectNotFoundException("Projet introuvable");
        }
        Project project = projectOptional.get();
        if (project.getStatus()!= ProjectStatus.PENDING) {
            throw new IllegalStateException("Impossible de modifier de status d'un projet déjà validée");
        }
        project.setStatus(ProjectStatus.COMPLETED);
        projectRepository.save(project);
        return projectMapper.toDto(project);
    }
}
