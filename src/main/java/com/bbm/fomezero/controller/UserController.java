package com.bbm.fomezero.controller;

import com.bbm.fomezero.dto.request.UserRequest;
import com.bbm.fomezero.dto.response.AppResponse;
import com.bbm.fomezero.dto.response.UserResponseDTO;
import com.bbm.fomezero.model.User;
import com.bbm.fomezero.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @GetMapping("/profile")
    public ResponseEntity<UserResponseDTO> getUserById(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(userService.getUserById(user.getId()));
    }

    @PutMapping("/profile")
    public ResponseEntity<AppResponse> updateUserProfile(@AuthenticationPrincipal User user,
                                                         @RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(userService.updateUser(user.getId(), userRequest));

    }

    @PutMapping("/activate")
    public ResponseEntity<AppResponse> activateUser(@RequestParam("userId") Long userId) {
        return ResponseEntity.ok(userService.activateUser(userId));
    }

    @PatchMapping("/deactivate")
    public ResponseEntity<AppResponse> deactivateUser(@RequestParam("userId") Long userId) {
        return ResponseEntity.ok(userService.deactivateUser(userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AppResponse> deleteUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }
}
