package com.example.fundmatch.api.controller;

import com.example.fundmatch.api.wrapper.ApiResponse;
import com.example.fundmatch.domain.dtos.request.user.RoleRequest;
import com.example.fundmatch.domain.vm.RoleResponseVM;
import com.example.fundmatch.service.interfaces.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;
    @PostMapping("/save")
    public ResponseEntity<ApiResponse<RoleResponseVM>> saveRole(@Valid @RequestBody RoleRequest RoleRequest) {
        RoleResponseVM response = roleService.saveRole(RoleRequest);
        ApiResponse<RoleResponseVM> apiResponse = ApiResponse.success(response, "/api/roles/save");
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<RoleResponseVM>> updateRole(@PathVariable long id , @Valid @RequestBody RoleRequest RoleRequest) {
        RoleResponseVM response = roleService.updateRole(RoleRequest, id);
        ApiResponse<RoleResponseVM> apiResponse = ApiResponse.success(response, "/api/roles");
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RoleResponseVM>> getRole(@PathVariable long id) {
        RoleResponseVM response = roleService.getRoleById(id);
        ApiResponse<RoleResponseVM> apiResponse = ApiResponse.success(response, "/api/roles");
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteRole(@PathVariable long id) {
        roleService.deleteRole(id);
        ApiResponse<Void> apiResponse = ApiResponse.success(null, "/api/roles");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(apiResponse);
    }
    @GetMapping
    public ResponseEntity<ApiResponse<List<RoleResponseVM>>> getAllRoles() {
        List<RoleResponseVM> response = roleService.getRoles();
        ApiResponse<List<RoleResponseVM>> apiResponse = ApiResponse.success(response, "/api/roles");
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
