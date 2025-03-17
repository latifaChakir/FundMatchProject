package com.example.fundmatch;

import com.example.fundmatch.domain.entities.User;
import com.example.fundmatch.domain.mappers.UserMapper;
import com.example.fundmatch.domain.vm.UserResponseVM;
import com.example.fundmatch.repository.UserRepository;
import com.example.fundmatch.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private User user;

    @Mock
    private UserResponseVM userResponseVM;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUsers_ReturnsListOfUsers() {
        List<User> users = Arrays.asList(user);
        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.toDtoList(users)).thenReturn(Arrays.asList(userResponseVM));

        List<UserResponseVM> result = userService.getUsers();

        assertEquals(1, result.size());
        assertEquals(userResponseVM, result.get(0));
    }
    //Bloque user

    @Test
    void blockUser_WhenUserExists_BlocksUser() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toEntity(user)).thenReturn(userResponseVM);

        UserResponseVM result = userService.blockUser(userId);

        assertEquals(userResponseVM, result);
        assertFalse(user.getIsActive());
        verify(userRepository).save(user);
    }
    @Test
    void blockUser_WhenUserDoesNotExist_ThrowsUsernameNotFoundException() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.blockUser(userId));
    }

    @Test
    void unBlockUser_WhenUserExists_UnblocksUser() {
        Long userId = 1L;

        User realUser = new User();
        realUser.setIsActive(false);

        when(userRepository.findById(userId)).thenReturn(Optional.of(realUser));
        when(userRepository.save(realUser)).thenReturn(realUser);
        when(userMapper.toEntity(realUser)).thenReturn(userResponseVM);

        UserResponseVM result = userService.unBlockUser(userId);

        assertEquals(userResponseVM, result);
        assertTrue(realUser.getIsActive());
        verify(userRepository).save(realUser);
    }
    @Test
    void unBlockUser_WhenUserDoesNotExist_ThrowsUsernameNotFoundException() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.unBlockUser(userId));
    }
}
