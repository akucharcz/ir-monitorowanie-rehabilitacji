package com.ibm.server.repository;

import com.ibm.server.model.LoginStructure;
import org.springframework.data.repository.CrudRepository;

public interface LoginRepository extends CrudRepository<LoginStructure, String> {
}
