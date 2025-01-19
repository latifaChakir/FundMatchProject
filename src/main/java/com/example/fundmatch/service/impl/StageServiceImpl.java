package com.example.fundmatch.service.impl;

import com.example.fundmatch.domain.dtos.request.stage.StageRequest;
import com.example.fundmatch.domain.entities.Stage;
import com.example.fundmatch.domain.mappers.StageMapper;
import com.example.fundmatch.domain.vm.StageResponseVM;
import com.example.fundmatch.repository.StageRepository;
import com.example.fundmatch.service.interfaces.StageService;
import com.example.fundmatch.shared.exception.StageNameAlreadyExistsException;
import com.example.fundmatch.shared.exception.StageNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StageServiceImpl implements StageService {
    private final StageRepository stageRepository;
    private final StageMapper stageMapper;
    @Override
    public StageResponseVM saveStage(StageRequest stageRequest) {
        if (stageRepository.existsByName(stageRequest.getName())) {
            throw new StageNameAlreadyExistsException("Stage name already exists.");
        }
        Stage stage = stageMapper.toEntity(stageRequest);
        Stage savedStage = stageRepository.save(stage);
        return stageMapper.toDto(savedStage);
    }

    @Override
    public StageResponseVM getStageById(Long id) {
        Optional<Stage> stage = stageRepository.findById(id);
        if (stage.isEmpty()) {
            throw new StageNotFoundException("Stage not found.");
        }
        return stageMapper.toDto(stage.get());
    }

    @Override
    public StageResponseVM updateStage(StageRequest stageRequest, Long id) {
        Optional<Stage> optionalStage = stageRepository.findById(id);
        if (optionalStage.isEmpty()) {
            throw new StageNotFoundException("Stage not found.");
        }

        Stage existingStage = optionalStage.get();
        if (!existingStage.getName().equals(stageRequest.getName()) &&
                stageRepository.existsByName(stageRequest.getName())) {
            throw new StageNameAlreadyExistsException("Stage name already exists.");
        }

        existingStage.setName(stageRequest.getName());
        Stage updatedStage = stageRepository.save(existingStage);
        return stageMapper.toDto(updatedStage);
    }


    @Override
    public void deleteStage(Long id) {
        Optional<Stage> stage = stageRepository.findById(id);
        if (stage.isEmpty()) {
            throw new StageNotFoundException("Stage not found.");
        }
        stageRepository.delete(stage.get());
    }

    @Override
    public List<StageResponseVM> getStages() {
        List<Stage> stages = stageRepository.findAll();
        return stageMapper.toDtoList(stages);
    }

}
