package com.example.fundmatch.api.controller;

import com.example.fundmatch.api.wrapper.ApiResponse;
import com.example.fundmatch.domain.dtos.request.project.CreateProjectRequestDto;
import com.example.fundmatch.domain.vm.ProjectResponseVM;
import com.example.fundmatch.service.interfaces.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;
    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<ProjectResponseVM>> saveProject(
            @Valid @ModelAttribute CreateProjectRequestDto projectRequest,
            @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
        ProjectResponseVM response = projectService.saveProject(projectRequest,file);
        ApiResponse<ProjectResponseVM> apiResponse = ApiResponse.success(response, "/api/projects/save");
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProjectResponseVM>> updateProject(@PathVariable long id , @Valid @RequestBody CreateProjectRequestDto ProjectRequest) {
        ProjectResponseVM response = projectService.updateProject(ProjectRequest, id);
        ApiResponse<ProjectResponseVM> apiResponse = ApiResponse.success(response, "/api/projects");
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProjectResponseVM>> getProject(@PathVariable long id) {
        ProjectResponseVM response = projectService.getProjectById(id);
        ApiResponse<ProjectResponseVM> apiResponse = ApiResponse.success(response, "/api/projects");
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProject(@PathVariable long id) {
        projectService.deleteProject(id);
        ApiResponse<Void> apiResponse = ApiResponse.success(null, "/api/projects");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(apiResponse);
    }
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProjectResponseVM>>> getAllProjects() {
        List<ProjectResponseVM> response = projectService.getProjects();
        ApiResponse<List<ProjectResponseVM>> apiResponse = ApiResponse.success(response, "/api/projects");
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("/updateStatus/{id}")
    public ResponseEntity<ApiResponse<ProjectResponseVM>> updateStatus(@PathVariable long id) {
        ProjectResponseVM response = projectService.updateStatus(id);
        ApiResponse<ProjectResponseVM> apiResponse = ApiResponse.success(response, "/api/projects");
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
    @GetMapping("/{startupId}/projects")
    public ResponseEntity<List<ProjectResponseVM>> getProjectsByStartup(@PathVariable Long startupId) {
        List<ProjectResponseVM> projects = projectService.getProjectsByStartupId(startupId);
        return ResponseEntity.ok(projects);
    }

}
