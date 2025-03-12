package com.example.fundmatch.domain.mappers;


import com.example.fundmatch.domain.dtos.request.user.CreateUserRequestDto;
import com.example.fundmatch.domain.entities.Role;
import com.example.fundmatch.domain.entities.User;
import com.example.fundmatch.domain.vm.AuthResponseVM;
import com.example.fundmatch.domain.vm.UserResponseVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(CreateUserRequestDto dto);
    @Mappings({
            @Mapping(target = "roles", source = "user.roles")
    })
    AuthResponseVM toDto(User user);

    default Set<Role> map(Set<Long> roleIds) {
        if (roleIds == null) {
            return null;
        }
        return roleIds.stream()
                .map(this::mapRoleIdToRole)
                .collect(Collectors.toSet());
    }

    default Role mapRoleIdToRole(Long roleId) {
        Role role = new Role();
        role.setId((long) Math.toIntExact(roleId));
        return role;
    }

    List<UserResponseVM> toDtoList(List<User> users);
    List<UserResponseVM> toDtoList(Set<User> users);
}

