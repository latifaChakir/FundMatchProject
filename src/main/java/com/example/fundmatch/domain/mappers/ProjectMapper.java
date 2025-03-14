package com.example.fundmatch.domain.mappers;

import com.example.fundmatch.domain.dtos.request.project.CreateProjectRequestDto;
import com.example.fundmatch.domain.entities.Project;
import com.example.fundmatch.domain.vm.ProjectResponseVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    StartupMapper INSTANCE = Mappers.getMapper(StartupMapper.class);

    @Mapping(source = "imagePath", target = "imagePath", qualifiedByName = "mapImagePath")
    Project toEntity(CreateProjectRequestDto dto);

    @Mapping(source = "startup", target = "startup")
    @Mapping(source = "imagePath", target = "imagePath", qualifiedByName = "mapImagePathFromEntity")
    ProjectResponseVM toDto(Project project);
    List<ProjectResponseVM> toDtoList(List<Project> projects);

    @Named("mapImagePath")
    default String mapImagePath(MultipartFile file) {
        return file != null ? file.getOriginalFilename() : null;
    }

    @Named("mapImagePathFromEntity")
    default String mapImagePathFromEntity(String imagePath) {
        return imagePath != null ? "http://localhost:9091/api/files/" + imagePath : null;
    }
}

