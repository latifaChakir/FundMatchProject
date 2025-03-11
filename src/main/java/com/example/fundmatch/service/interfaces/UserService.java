package com.example.fundmatch.service.interfaces;


import com.example.fundmatch.domain.vm.UserResponseVM;

import java.util.List;

public interface UserService   {
    List<UserResponseVM> getUsers();
}
