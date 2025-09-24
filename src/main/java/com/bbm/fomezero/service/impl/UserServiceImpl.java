package com.bbm.fomezero.service.impl;

import com.bbm.fomezero.dto.request.UserRequest;
import com.bbm.fomezero.dto.response.AppResponse;
import com.bbm.fomezero.exception.ConflictException;
import com.bbm.fomezero.mapper.EntityConverter;
import com.bbm.fomezero.model.User;
import com.bbm.fomezero.model.enums.Role;
import com.bbm.fomezero.repository.UserRepository;
import com.bbm.fomezero.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.CREATED;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final EntityConverter<User, UserRequest> userConverter;

    @Override
    @Transactional
    public AppResponse createUser(UserRequest userRequest) {
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new ConflictException("Email already exists");
        }
        var user = userConverter.mapDtoToEntity(userRequest, User.class);
        user.setRole(Role.ADMIN);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return AppResponse.builder()
                .responseCode(CREATED.value())
                .responseStatus(CREATED)
                .responseMessage("User created successfully!")
                .description(user.getFullName())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
