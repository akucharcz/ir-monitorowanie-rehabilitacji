package com.ibm.server;

import com.ibm.server.model.LoginStructure;
import com.ibm.server.repository.LoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LoginController {

    private final LoginRepository loginRepository;

    public LoginStructure saveLogin(LoginStructure loginStructure)
    {
        return loginRepository.save(loginStructure);
    }

    public List<LoginStructure> getAllLogins()
    {
        return (List<LoginStructure>) loginRepository.findAll();
    }

}
