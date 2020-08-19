package com.ibm.server;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private LoginRepository loginRepository;
    public List<LoginStructure> findAll() {

        var it = loginRepository.findAll();

        var users = new ArrayList<LoginStructure>();
        it.forEach(e -> users.add(e));

        return users;
    }
}
