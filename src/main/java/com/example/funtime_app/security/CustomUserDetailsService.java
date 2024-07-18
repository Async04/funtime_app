package com.example.funtime_app.security;

import com.example.funtime_app.entity.Role;
import com.example.funtime_app.entity.User;
import com.example.funtime_app.repository.RoleRepository;
import com.example.funtime_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User byUsername = userRepository.findByUsername(username);
        System.out.println("User-"+byUsername.getAuthorities());
        return byUsername;
    }
}
