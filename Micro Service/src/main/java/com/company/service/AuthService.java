package com.company.service;

import com.company.exceptions.ServiceResultException;
import com.company.model.ServiceResult;
import com.company.dto.UserDto;
import com.company.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@Slf4j
public class AuthService {

    @Autowired
    private UserRepo userRepo;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public ServiceResult validateUser(UserDto userDto) {
        String username = userDto.getUsername();
        String password = userDto.getPassword();
        log.info("Received request to validate user={}", username);
        ServiceResult.ServiceResultBuilder serviceResult=ServiceResult.builder();

        // Retrieve the stored hashed password
        String storedPasswordHash = userRepo.findPasswordByUsername(username);
        boolean isAuthenticated = passwordEncoder.matches(password, storedPasswordHash);

        // Check if the user exists
        if (isAuthenticated) {
            serviceResult
                    .returnCode("0")
                    .returnMessage("Authentication successful!");
        } else {
            serviceResult
                    .returnCode("99")
                    .returnMessage("Authentication failed.");
        }
        ServiceResult result = serviceResult.build();
        log.info("Validation result for user {}: returnCode={}, returnMessage={}",
                username, result.getReturnCode(), result.getReturnMessage());
        return result;
    }

    public ServiceResult resetPassword(UserDto userDto) {
        ServiceResult.ServiceResultBuilder serviceResult=ServiceResult.builder();
        String username = userDto.getUsername();
        String password = userDto.getPassword();
        try {
            log.info("Received request to reset password for user={}", username);
            String hashedPassword = passwordEncoder.encode(password);
            // Try to update the user's password
            int updatedRows = userRepo.updatePasswordByUsername(username, hashedPassword, false);
            if (updatedRows != 0) {
                serviceResult
                        .returnCode("0")
                        .returnMessage("Password reset successfully.");
            }
            else{
                log.warn("User not found: {}", username);
                throw new ServiceResultException(ServiceResult.builder()
                        .returnCode("98")
                        .returnMessage("User not found")
                        .build());
            }
        } catch (DataAccessException e) {
            log.error("Error resetting password for user {}: {}", username, e.getMessage());
            throw new ServiceResultException(ServiceResult.builder()
                    .returnCode("99")
                    .returnMessage("Error resetting password.")
                    .build());
        }
        ServiceResult result = serviceResult.build();
        log.info("Reset password result for user {}: returnCode={}, returnMessage={}",
                username, result.getReturnCode(), result.getReturnMessage());
        return result;
    }

    public ServiceResult lockUser(String username) {
        ServiceResult.ServiceResultBuilder serviceResult = ServiceResult.builder();
        try {
            log.info("Received request to lock user={}", username);
            int updatedRows = userRepo.updateLockedStatusByUsername(username, true);
            if (updatedRows != 0) {
                serviceResult
                        .returnCode("0")
                        .returnMessage("User locked successfully.");
            } else {
                log.warn("User not found: {}", username);
                throw new ServiceResultException(ServiceResult.builder()
                        .returnCode("98")
                        .returnMessage("User not found")
                        .build());
            }
        } catch (DataAccessException e) {
            log.error("Error locking user {}: {}", username, e.getMessage());
            throw new ServiceResultException(ServiceResult.builder()
                    .returnCode("99")
                    .returnMessage("Error locking user.")
                    .build());
        }

        ServiceResult result = serviceResult.build();
        log.info("Lock user result for user {}: returnCode={}, returnMessage={}",
                username, result.getReturnCode(), result.getReturnMessage());
        return result;
    }
}
