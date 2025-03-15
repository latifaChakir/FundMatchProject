package com.example.fundmatch.service.impl;
import com.example.fundmatch.domain.entities.User;
import com.example.fundmatch.domain.mappers.UserMapper;
import com.example.fundmatch.domain.vm.UserResponseVM;
import com.example.fundmatch.repository.UserRepository;
import com.example.fundmatch.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Override
    public List<UserResponseVM> getUsers() {
        List<User> users = userRepository.findAll() ;
        return userMapper.toDtoList(users);
    }
    @Override
    public UserResponseVM blockUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setIsActive(false);
        User updatedUser = userRepository.save(user);
        return userMapper.toEntity(updatedUser);
    }

}
