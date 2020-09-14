package com.ibm.server;

import com.ibm.server.model.LoginStructure;
import org.springframework.data.repository.CrudRepository;

public interface LoginRepository extends CrudRepository<LoginStructure, String> {
}
