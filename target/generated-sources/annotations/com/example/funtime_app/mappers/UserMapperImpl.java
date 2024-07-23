package com.example.funtime_app.mappers;

import com.example.funtime_app.dto.UserDTO;
import com.example.funtime_app.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-20T12:40:01+0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.3 (Amazon.com Inc.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.firstName( userDTO.firstName() );
        user.lastName( userDTO.lastName() );
        user.username( userDTO.username() );
        user.email( userDTO.email() );
        user.password( userDTO.password() );

        return user.build();
    }

    @Override
    public UserDTO toDto(User user) {
        if ( user == null ) {
            return null;
        }

        String firstName = null;
        String lastName = null;
        String username = null;
        String email = null;
        String password = null;

        firstName = user.getFirstName();
        lastName = user.getLastName();
        username = user.getUsername();
        email = user.getEmail();
        password = user.getPassword();

        UserDTO userDTO = new UserDTO( firstName, lastName, username, email, password );

        return userDTO;
    }

    @Override
    public User partialUpdate(UserDTO userDTO, User user) {
        if ( userDTO == null ) {
            return user;
        }

        if ( userDTO.firstName() != null ) {
            user.setFirstName( userDTO.firstName() );
        }
        if ( userDTO.lastName() != null ) {
            user.setLastName( userDTO.lastName() );
        }
        if ( userDTO.username() != null ) {
            user.setUsername( userDTO.username() );
        }
        if ( userDTO.email() != null ) {
            user.setEmail( userDTO.email() );
        }
        if ( userDTO.password() != null ) {
            user.setPassword( userDTO.password() );
        }

        return user;
    }
}
