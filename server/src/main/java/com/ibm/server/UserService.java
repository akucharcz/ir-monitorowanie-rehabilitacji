package com.ibm.server;

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
        it.forEach(e -> users.add(e));

        return users;
    }
}
