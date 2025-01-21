package com.example.fundmatch.service.interfaces;

import com.example.fundmatch.domain.dtos.request.project.CreateProjectRequestDto;
import com.example.fundmatch.domain.vm.ProjectResponseVM;

import java.util.List;

public interface ProjectService  {
    ProjectResponseVM saveProject(CreateProjectRequestDto projectRequest);
    ProjectResponseVM getProjectById(Long id);
    ProjectResponseVM updateProject(CreateProjectRequestDto projectRequest, Long id);
    void deleteProject(Long id);
    List<ProjectResponseVM> getProjects();

}
