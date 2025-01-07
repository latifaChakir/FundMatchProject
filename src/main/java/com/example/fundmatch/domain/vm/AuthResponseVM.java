package com.example.fundmatch.domain.vm;
import com.example.fundmatch.domain.entities.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseVM {
    private Long id;
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Set<Role> roles;
    private String token;
    private String refreshToken;
}
