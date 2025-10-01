package com.bbm.fomezero.controller;

import com.bbm.fomezero.dto.request.AddressRequestDTO;
import com.bbm.fomezero.dto.response.AddressResponseDTO;
import com.bbm.fomezero.dto.response.AppResponse;
import com.bbm.fomezero.model.User;
import com.bbm.fomezero.service.AddressService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/addresses")
@Tag(name = "5. Address Management")
public class AddressController {

    private final AddressService addressService;

    @PostMapping("/")
    public ResponseEntity<AppResponse> createAddress(@AuthenticationPrincipal User user,
                                                     @RequestBody AddressRequestDTO addressRequest) {
        return new ResponseEntity<>(addressService.createAddress(addressRequest, user.getId()), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AddressResponseDTO>> getAllAddressesByUser(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(addressService.getAllAddressesByUser(user.getId()));
    }

    @PutMapping
    public ResponseEntity<AppResponse> updateAddress(@RequestParam("id") Long id,
                                                     @RequestBody AddressRequestDTO addressRequest) {
        return ResponseEntity.ok(addressService.updateAddress(id, addressRequest));
    }
}
