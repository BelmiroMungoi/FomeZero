package com.bbm.fomezero.controller;

import com.bbm.fomezero.dto.request.UserRequest;
import com.bbm.fomezero.dto.response.AppResponse;
import com.bbm.fomezero.dto.response.UserResponseDTO;
import com.bbm.fomezero.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Tag(name = "2. User")
public class UserController {

    private final UserService userService;

    @PostMapping("/")
    public ResponseEntity<AppResponse> createUser(@RequestBody UserRequest userRequest) {
        return new ResponseEntity<>(userService.createUser(userRequest), CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
