package com.example.fundmatch.domain.vm;

import lombok.*;

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
}
