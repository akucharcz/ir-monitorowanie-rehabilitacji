package com.ibm.server.service;

import com.ibm.server.repository.LoginRepository;
import com.ibm.server.model.LoginStructure;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private final LoginRepository loginRepository;

    public UserService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public List<LoginStructure> findAll() {

        var it = loginRepository.findAll();

        var users = new ArrayList<LoginStructure>();
        it.forEach(users::add);

        return users;
    }
}
