package com.example.fundmatch.service.impl;

import com.example.fundmatch.domain.dtos.request.user.RoleRequest;
import com.example.fundmatch.domain.entities.Role;
import com.example.fundmatch.domain.mappers.RoleMapper;
import com.example.fundmatch.domain.vm.RoleResponseVM;
import com.example.fundmatch.repository.RoleRepository;
import com.example.fundmatch.service.interfaces.RoleService;
import com.example.fundmatch.shared.exception.RoleNameAlreadyExistsException;
import com.example.fundmatch.shared.exception.RoleNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public RoleResponseVM saveRole(RoleRequest roleRequest) {
        if (roleRepository.existsByName(roleRequest.getName())) {
            throw new RoleNameAlreadyExistsException("Role name already exists.");
        }
        Role role = roleMapper.toEntity(roleRequest);
        Role savedRole = roleRepository.save(role);
        return roleMapper.toDto(savedRole);
    }

    @Override
    public RoleResponseVM getRoleById(Long id) {
        Optional<Role> role = roleRepository.findById(id);
        if (role.isEmpty()) {
            throw new RoleNotFoundException("Role not found.");
        }
        return roleMapper.toDto(role.get());
    }

    @Override
    public RoleResponseVM updateRole(RoleRequest roleRequest, Long id) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        if (optionalRole.isEmpty()) {
            throw new RoleNotFoundException("Role not found.");
        }

        Role existingRole = optionalRole.get();
        if (!existingRole.getName().equals(roleRequest.getName()) &&
                roleRepository.existsByName(roleRequest.getName())) {
            throw new RoleNameAlreadyExistsException("Role name already exists.");
        }

        existingRole.setName(roleRequest.getName());
        Role updatedRole = roleRepository.save(existingRole);
        return roleMapper.toDto(updatedRole);
    }

    @Override
    public void deleteRole(Long id) {
        Optional<Role> role = roleRepository.findById(id);
        if (role.isEmpty()) {
            throw new RoleNotFoundException("Role not found.");
        }
        roleRepository.delete(role.get());
    }

    @Override
    public List<RoleResponseVM> getRoles() {
        List<Role> roles = roleRepository.findAll();
        return roleMapper.toDtoList(roles);
    }
}
