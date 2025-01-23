package com.example.fundmatch.domain.mappers;

import com.example.fundmatch.domain.dtos.request.user.RoleRequest;
import com.example.fundmatch.domain.entities.Role;
import com.example.fundmatch.domain.vm.RoleResponseVM;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role toEntity(RoleRequest roleRequest);
    RoleResponseVM toDto(Role role);
    List<RoleResponseVM> toDtoList(List<Role> roles);
}

