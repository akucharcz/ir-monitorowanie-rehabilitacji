package com.ibm.server.service;

import com.ibm.server.repository.LoginRepository;
import com.ibm.server.model.LoginStructure;
import org.springframework.stereotype.Service;

import java.util.*;


import com.ibm.server.domain.Role;
import com.ibm.server.domain.User;
import com.ibm.server.repository.RoleRepository;
import com.ibm.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private final LoginRepository loginRepository;

    public User findUserByEmail(String email)
    {
        return userRepository.findByEmail(email);
    }

    public UserService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public List<LoginStructure> findAll() {

        var it = loginRepository.findAll();

        var users = new ArrayList<LoginStructure>();
        it.forEach(users::add);

        return users;
    }


    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        Role userRole = roleRepository.findByRole("ADMIN");
        user.setRoles(new HashSet<>(Arrays.asList(userRole)));
        userRepository.save(user);
    }

}
