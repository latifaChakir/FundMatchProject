package com.example.fundmatch.domain.mappers;

import com.example.fundmatch.domain.dtos.request.startup.CreateStartupRequestDto;
import com.example.fundmatch.domain.entities.Startup;
import com.example.fundmatch.domain.vm.StartupResponseVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface StartupMapper {

    StartupMapper INSTANCE = Mappers.getMapper(StartupMapper.class);

    @Mapping(source = "imagePath", target = "imagePath", qualifiedByName = "mapImagePath")
    Startup toEntity(CreateStartupRequestDto dto);

    @Mapping(source = "stages", target = "stages")
    @Mapping(source = "user", target = "user")
    @Mapping(source = "sectors", target = "sectors")
    @Mapping(source = "imagePath", target = "imagePath", qualifiedByName = "mapImagePathFromEntity")
    StartupResponseVM toDto(Startup entity);

    List<StartupResponseVM> toDtoList(List<Startup> startups);

    @Named("mapImagePath")
    default String mapImagePath(MultipartFile file) {
        return file != null ? file.getOriginalFilename() : null;
    }

    @Named("mapImagePathFromEntity")
    default String mapImagePathFromEntity(String imagePath) {
        return imagePath != null ? "http://localhost:9091/api/files/" + imagePath : null;
    }
}


