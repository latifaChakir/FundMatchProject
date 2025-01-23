package com.example.fundmatch.service.interfaces;

import com.example.fundmatch.domain.dtos.request.user.RoleRequest;
import com.example.fundmatch.domain.vm.RoleResponseVM;

import java.util.List;

public interface RoleService  {
    RoleResponseVM saveRole(RoleRequest roleRequest);
    RoleResponseVM getRoleById(Long id);
    RoleResponseVM updateRole(RoleRequest roleRequest, Long id);
    void deleteRole(Long id);
    List<RoleResponseVM> getRoles();

}
