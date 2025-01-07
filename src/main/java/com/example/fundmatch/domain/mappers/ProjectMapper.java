package com.example.fundmatch.domain.mappers;

import com.example.fundmatch.domain.dtos.request.project.CreateProjectRequestDto;
import com.example.fundmatch.domain.entities.Project;
import com.example.fundmatch.domain.vm.ProjectResponseVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    Project toEntity(CreateProjectRequestDto dto);

    ProjectResponseVM toDto(Project project);

    List<ProjectResponseVM> toDtoList(List<Project> projects);
}

