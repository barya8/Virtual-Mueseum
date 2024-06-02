package com.company.service;

import com.company.dto.ReturnCodeAndMessage;
import com.company.dto.ServiceResult;
import com.company.dto.UserDto;
import com.company.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepo userRepo;

    public ServiceResult validateUser(UserDto userDto) {

        String username = userDto.getUsername();
        String password = userDto.getPassword();

        boolean isAuthenticated = authenticateUser(username, password);

        ReturnCodeAndMessage returnCodeAndMessage  = new ReturnCodeAndMessage();
        // Check if the user exists
        if (userRepo.existsByUsername(userDto.getUsername())) {
            if (isAuthenticated) {
                returnCodeAndMessage
                        .setReturnCode("0")
                        .setReturnMessage("Authentication successful!");
            } else {
                returnCodeAndMessage
                        .setReturnCode("99")
                        .setReturnMessage("Wrong Password.");
            }
        }
        else {
            returnCodeAndMessage
                    .setReturnCode("98")
                    .setReturnMessage("User not found.");
        }
        ServiceResult response = new ServiceResult();
        response.setServiceResult(returnCodeAndMessage);

        return response;
    }

    private boolean authenticateUser(String username, String password) {
        int count = userRepo.findByUsernameAndPassword(username, password);
        return count > 0;
    }

    public ServiceResult resetPassword(UserDto userDto) {
        ReturnCodeAndMessage returnCodeAndMessage  = new ReturnCodeAndMessage();
        try {
            // Check if the user exists
            if (userRepo.existsByUsername(userDto.getUsername())) {
                // Update the user's password
                userRepo.updatePasswordByUsername(userDto.getUsername(), userDto.getPassword());
                returnCodeAndMessage.setReturnCode("0");
                returnCodeAndMessage.setReturnMessage("Password reset successfully.");
            } else {
                returnCodeAndMessage.setReturnCode("98");
                returnCodeAndMessage.setReturnMessage("User not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnCodeAndMessage.setReturnCode("99");
            returnCodeAndMessage.setReturnMessage("Error resetting password.");
        }

        ServiceResult response = new ServiceResult();
        response.setServiceResult(returnCodeAndMessage);
        return response;
    }
}
