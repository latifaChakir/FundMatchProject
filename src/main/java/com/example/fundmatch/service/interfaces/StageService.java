package com.example.fundmatch.service.interfaces;


import com.example.fundmatch.domain.dtos.request.stage.StageRequest;
import com.example.fundmatch.domain.vm.StageResponseVM;

import java.util.List;

public interface StageService {
    StageResponseVM saveStage(StageRequest stageRequest);
    StageResponseVM getStageById(Long id);
    StageResponseVM updateStage(StageRequest stageRequest, Long id);
    void deleteStage(Long id);
    List<StageResponseVM> getStages();

}
