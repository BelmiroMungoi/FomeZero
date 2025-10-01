package com.bbm.fomezero.service.impl;

import com.bbm.fomezero.dto.request.UserRequest;
import com.bbm.fomezero.dto.response.AppResponse;
import com.bbm.fomezero.dto.response.UserResponseDTO;
import com.bbm.fomezero.exception.ConflictException;
import com.bbm.fomezero.exception.ResourceNotFoundException;
import com.bbm.fomezero.mapper.EntityConverter;
import com.bbm.fomezero.model.User;
import com.bbm.fomezero.model.enums.Role;
import com.bbm.fomezero.repository.UserRepository;
import com.bbm.fomezero.security.JWTService;
import com.bbm.fomezero.service.CartService;
import com.bbm.fomezero.service.ProfileService;
import com.bbm.fomezero.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final JWTService jwtService;
    private final CartService cartService;
    private final ProfileService profileService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final EntityConverter<User, UserRequest> userConverter;
    private final EntityConverter<User, UserResponseDTO> userResponseConverter;

    @Override
    @Transactional
    public AppResponse createUser(UserRequest userRequest) {
        var user = createUserAndReturnEntity(userRequest);
        var token = jwtService.generateToken(user);

        if (user.getRole() == Role.RESTAURANT_OWNER) {
            user.setStatus(true);
            userRepository.save(user);
        }

        return AppResponse.builder()
                .responseCode(CREATED.value())
                .responseStatus(CREATED)
                .responseMessage("User account created successfully.")
                .description("Welcome, " + user.getFullName() + "!")
                .createdAt(LocalDateTime.now())
                .token(token)
                .build();
    }

    @Override
    @Transactional
    public User createUserAndReturnEntity(UserRequest userRequest) {
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new ConflictException("An account with this email already exists.");
        }

        var user = userConverter.mapDtoToEntity(userRequest, User.class);
        user.setStatus(false);
        user.setRole(Role.valueOf(userRequest.getRole().toUpperCase()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        var savedUser = userRepository.save(user);

        cartService.createCart(savedUser);
        profileService.createProfile(savedUser, userRequest.getPhoneNumber(), userRequest.getAvatarUrl());

        return savedUser;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllUsers() {
        return userResponseConverter
                .mapEntityToDtoList(userRepository.findAll(), UserResponseDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("User not found."));
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getUserById(Long id) {
        var user = userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("User not found."));

        return userResponseConverter.mapEntityToDto(user, UserResponseDTO.class);
    }

    @Override
    @Transactional
    public AppResponse updateUser(Long id, UserRequest userRequest) {
        var user = getUser(id);
        user.setFullName(userRequest.getFullName());
        var updatedUser = userRepository.save(user);
        profileService.updateProfile(updatedUser, userRequest.getPhoneNumber(), userRequest.getAvatarUrl());

        return AppResponse.builder()
                .responseCode(OK.value())
                .responseStatus(OK)
                .responseMessage("User account updated successfully.")
                .description("Welcome back, " + user.getFullName() + "!")
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Override
    @Transactional
    public AppResponse activateUser(Long id) {
        var user = getUser(id);
        if (Boolean.TRUE.equals(user.isStatus())) {
            throw new ConflictException("This account is already active.");
        }
        user.setStatus(true);
        userRepository.save(user);

        return AppResponse.builder()
                .responseCode(OK.value())
                .responseStatus(OK)
                .responseMessage("User account activated successfully.")
                .description("Hello, " + user.getFullName() + ", your account has been activated.")
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Override
    @Transactional
    public AppResponse deactivateUser(Long id) {
        var user = getUser(id);
        if (Boolean.FALSE.equals(user.isStatus())) {
            throw new ConflictException("This account has already been deactivated.");
        }
        user.setStatus(false);
        userRepository.save(user);

        return AppResponse.builder()
                .responseCode(OK.value())
                .responseStatus(OK)
                .responseMessage("User account deactivated successfully.")
                .description("Hello, " + user.getFullName() + ", your account has been deactivated.")
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Override
    @Transactional
    public AppResponse deleteUser(Long id) {
        var user = getUser(id);
        userRepository.delete(user);

        return AppResponse.builder()
                .responseCode(OK.value())
                .responseStatus(OK)
                .responseMessage("User account deleted successfully.")
                .description("The account, " + user.getFullName() + ", has been deleted.")
                .createdAt(LocalDateTime.now())
                .build();
    }
}
