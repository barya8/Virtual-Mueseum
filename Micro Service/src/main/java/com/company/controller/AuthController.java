package com.company.controller;

import com.company.exceptions.ServiceResultException;
import com.company.model.ServiceResult;
import com.company.dto.UserDto;
import com.company.service.AuthService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ServiceResult> authenticate(@Valid @RequestBody UserDto userDto) {
        return new ResponseEntity<>(authService.validateUser(userDto), HttpStatus.OK);
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<ServiceResult> resetPassword(@Valid @RequestBody UserDto userDto) {
        try {
            ServiceResult result = authService.resetPassword(userDto);
            return ResponseEntity.ok(result);
        } catch (ServiceResultException e) {
            ServiceResult errorResult = e.getServiceResult();
            log.error("Error resetting password {}", errorResult);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResult);
        }
    }

    @PostMapping("/lockUser/{username}")
    public ResponseEntity<ServiceResult> lockUser(@PathVariable String username) {
            try {
            ServiceResult result = authService.lockUser(username);
            return ResponseEntity.ok(result);
        } catch (ServiceResultException e) {
            ServiceResult errorResult = e.getServiceResult();
            log.error("Error resetting password {}", errorResult);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResult);
        }
    }
}