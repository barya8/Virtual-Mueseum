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
        if (isAuthenticated) {
            returnCodeAndMessage
                    .setReturnCode("0")
                    .setReturnMessage("Authentication successful!");
        } else {
            returnCodeAndMessage
                    .setReturnCode("99")
                    .setReturnMessage("Authentication failed.");
        }
        ServiceResult response = new ServiceResult();
        response.setServiceResult(returnCodeAndMessage);

        return response;
    }

    private boolean authenticateUser(String username, String password) {
        int count = userRepo.findByUsernameAndPassword(username, password);
        return count > 0;
    }
}
