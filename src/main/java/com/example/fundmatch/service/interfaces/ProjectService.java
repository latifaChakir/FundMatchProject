package com.example.fundmatch.service.interfaces;

import com.example.fundmatch.domain.dtos.request.project.CreateProjectRequestDto;
import com.example.fundmatch.domain.vm.ProjectResponseVM;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProjectService  {
    ProjectResponseVM saveProject(CreateProjectRequestDto projectRequest ,MultipartFile file) throws IOException;
    ProjectResponseVM getProjectById(Long id);

    ProjectResponseVM updateProject(CreateProjectRequestDto projectRequest, Long id, MultipartFile file) throws IOException;

    void deleteProject(Long id);
    List<ProjectResponseVM> getProjects();
    ProjectResponseVM updateStatus(Long projectId);

    List<ProjectResponseVM> getProjectsByStartupId(Long startupId);

    List<ProjectResponseVM> getProjectsForCurrentUser();
}
