package com.example.fundmatch.domain.vm;

import com.example.fundmatch.domain.entities.Role;
import lombok.*;

import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseVM {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Set<Role> roles;
    private Boolean isActive;

}
