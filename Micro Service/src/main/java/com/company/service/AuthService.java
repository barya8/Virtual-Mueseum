package com.company.service;

import com.company.exceptions.ServiceResultException;
import com.company.model.ServiceResult;
import com.company.dto.UserDto;
import com.company.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService {

    @Autowired
    private UserRepo userRepo;

    public ServiceResult validateUser(UserDto userDto) {
        String username = userDto.getUsername();
        String password = userDto.getPassword();
        log.info("Received request to validate user={}", username);
        ServiceResult.ServiceResultBuilder serviceResult=ServiceResult.builder();
        boolean isAuthenticated = authenticateUser(username, password);

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

    private boolean authenticateUser(String username, String password) {
        int count = userRepo.findByUsernameAndPassword(username, password);
        return count > 0;
    }

    public ServiceResult resetPassword(UserDto userDto) {
        ServiceResult.ServiceResultBuilder serviceResult=ServiceResult.builder();
        String username = userDto.getUsername();
        String password = userDto.getPassword();
        try {
            log.info("Received request to reset password for user={}", username);
            // Try to update the user's password
            int updatedRows = userRepo.updatePasswordByUsername(username, password);
            if (updatedRows != 0) {
                userRepo.updatePasswordByUsername(username, password);
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
}
