package com.ibm.server.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.ibm.server.model.User;
import com.ibm.server.web.dto.UserRegistrationDto;

public interface UserService extends UserDetailsService{
	User save(UserRegistrationDto registrationDto);
}
