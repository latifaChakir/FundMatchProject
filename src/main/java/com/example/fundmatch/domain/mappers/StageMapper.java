package com.example.fundmatch.domain.mappers;

import com.example.fundmatch.domain.dtos.request.stage.StageRequest;
import com.example.fundmatch.domain.entities.Stage;
import com.example.fundmatch.domain.vm.StageResponseVM;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StageMapper {
    Stage toEntity(StageRequest stageRequest);
    StageResponseVM toDto(Stage stage);

    List<StageResponseVM> toDtoList(List<Stage> stages);
}
