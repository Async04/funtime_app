package com.example.funtime_app.services;

import com.example.funtime_app.entity.User;
import com.example.funtime_app.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private UserRepository userRepository;

    @BeforeEach
    void before(){
        this.userRepository=Mockito.mock(UserRepository.class);
    }

    @Test
    void saveUser() {
        UUID uuid = UUID.randomUUID();

        User user = User.builder()
                .build();
        Mockito.when(userRepository.save(user)).thenReturn(
                User.builder().id(uuid).build()
        );
        User saved = userRepository.save(user);
        assertEquals(saved.getId(), uuid);

    }
}