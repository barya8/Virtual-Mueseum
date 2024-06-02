package com.company.controller;

import com.company.dto.ServiceResult;
import com.company.dto.UserDto;
import com.company.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ServiceResult> authenticate(@RequestBody UserDto userDto) {
        ServiceResult serviceResult = authService.validateUser(userDto);
        return new ResponseEntity<>(serviceResult, HttpStatus.OK);
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<ServiceResult> resetPassword(@RequestBody UserDto userDto) {
        ServiceResult serviceResult = authService.resetPassword(userDto);
        return new ResponseEntity<>(serviceResult, HttpStatus.OK);
    }
}
