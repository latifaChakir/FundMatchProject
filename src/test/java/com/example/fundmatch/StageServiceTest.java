package com.example.fundmatch;

import com.example.fundmatch.domain.dtos.request.stage.StageRequest;
import com.example.fundmatch.domain.entities.Stage;
import com.example.fundmatch.domain.mappers.StageMapper;
import com.example.fundmatch.domain.vm.StageResponseVM;
import com.example.fundmatch.repository.StageRepository;
import com.example.fundmatch.service.impl.StageServiceImpl;
import com.example.fundmatch.shared.exception.StageNameAlreadyExistsException;
import com.example.fundmatch.shared.exception.StageNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class StageServiceTest {
    @InjectMocks
    private StageServiceImpl stageService;
    @Mock
    private StageRepository stageRepository;
    @Mock
    private StageMapper stageMapper;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void saveStgae_whenStageNameExist_ThrowsStageNameAlreadyExistsException(){
        StageRequest stageRequest = new StageRequest();
        stageRequest.setName("Stage 1");
        when(stageRepository.existsByName("Stage 1")).thenReturn(true);
        assertThrows(StageNameAlreadyExistsException.class, () ->stageService.saveStage(stageRequest));
        verify(stageRepository, never()).save(any());
    }
    @Test
    void saveStage_whenValidRequest(){
        StageRequest stageRequest = new StageRequest();
        stageRequest.setName("Stage 1");
        Stage stage = new Stage();
        stage.setName("Stage 1");
        StageResponseVM stageResponseVM = new StageResponseVM();
        stageResponseVM.setName("Stage 1");
        when(stageRepository.existsByName("Stage 1")).thenReturn(false);
        when(stageMapper.toEntity(stageRequest)).thenReturn(stage);
        when(stageRepository.save(stage)).thenReturn(stage);
        when(stageMapper.toDto(stage)).thenReturn(stageResponseVM);
        StageResponseVM stageResponse = stageService.saveStage(stageRequest);
        Assertions.assertEquals("Stage 1", stageResponse.getName());
    }
    @Test
    void updateStage_whenStageNotFound_ThrowsEntityNotFoundException(){
        Long id = 1L;
        when(stageRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(StageNotFoundException.class, () -> stageService.updateStage(new StageRequest(), id));
        verify(stageRepository, never()).save(any());
    }
    @Test
    void updateStage_whenValidRequest(){
        Long id = 1L;
        StageRequest stageRequest = new StageRequest();
        stageRequest.setName("Stage 1");
        Stage stage = new Stage();
        stage.setId(id);
        stage.setName("Stage 1");
        StageResponseVM stageResponseVM = new StageResponseVM();
        stageResponseVM.setName("Stage 1");
        when(stageRepository.findById(id)).thenReturn(Optional.of(stage));
        when(stageMapper.toEntity(stageRequest)).thenReturn(stage);
        when(stageRepository.save(stage)).thenReturn(stage);
        when(stageMapper.toDto(stage)).thenReturn(stageResponseVM);
        StageResponseVM stageResponse = stageService.updateStage(stageRequest, id);
        Assertions.assertEquals("Stage 1", stageResponse.getName());
    }
    @Test
    void deleteStage_whenStageNotFound_ThrowsEntityNotFoundException(){
        Long id = 1L;
        when(stageRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(StageNotFoundException.class, () -> stageService.deleteStage(id));
        verify(stageRepository, never()).deleteById(id);
    }
    @Test
    void deleteStage_whenValidRequest(){
        Long id = 1L;
        Stage stage = new Stage();
        stage.setId(id);
        when(stageRepository.findById(id)).thenReturn(Optional.of(stage));
        stageService.deleteStage(id);
        verify(stageRepository).delete(stage);
    }
    @Test
    void getStages_whenNoStagesFound_returnsEmptyList(){
        when(stageRepository.findAll()).thenReturn(java.util.Collections.emptyList());
        Assertions.assertEquals(0, stageService.getStages().size());
    }
    @Test
    void getStages_whenStagesFound_returnsStageResponseVMList(){
        Stage stage = new Stage();
        stage.setName("Stage 1");
        Stage stage2 = new Stage();
        stage2.setName("Stage 2");
        List<Stage> stages = Arrays.asList(stage, stage2);
        StageResponseVM responseVM = new StageResponseVM();
        responseVM.setName("Stage 1");
        StageResponseVM responseVM2 = new StageResponseVM();
        responseVM2.setName("Stage 2");
        when(stageRepository.findAll()).thenReturn(stages);
        when(stageMapper.toDtoList(stages)).thenReturn(Arrays.asList(responseVM,responseVM2));
        List<StageResponseVM> stageResponseVMList = stageService.getStages();
        Assertions.assertEquals(2, stageResponseVMList.size());
        Assertions.assertEquals("Stage 1", stageResponseVMList.get(0).getName());
        Assertions.assertEquals("Stage 2", stageResponseVMList.get(1).getName());
        }

    @Test
    void getStageById_WhenStageNotFound_ThrowsStageNotFoundException() {
        Long id = 1L;
        when(stageRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(StageNotFoundException.class, () -> stageService.getStageById(id));
    }
    @Test
    void getStageById_WhenStageFound_ReturnsStageResponseVM() {
        Long id = 1L;
        Stage stage = new Stage();
        stage.setId(id);
        stage.setName("Stage 1");
        StageResponseVM responseVM = new StageResponseVM();
        responseVM.setName("Stage 1");
        when(stageRepository.findById(id)).thenReturn(Optional.of(stage));
        when(stageMapper.toDto(stage)).thenReturn(responseVM);
        StageResponseVM stageResponseVM = stageService.getStageById(id);
        Assertions.assertEquals("Stage 1", stageResponseVM.getName());
        verify(stageRepository).findById(id);
    }
}
