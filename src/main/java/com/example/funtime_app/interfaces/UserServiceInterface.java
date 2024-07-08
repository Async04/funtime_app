package com.example.funtime_app.interfaces;

import com.example.funtime_app.dto.UserDTO;
import org.springframework.http.HttpEntity;

public interface UserServiceInterface {

    HttpEntity<?> saveUser(UserDTO userDTO);
}
