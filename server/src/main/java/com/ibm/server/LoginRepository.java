package com.ibm.server;

import org.springframework.data.repository.CrudRepository;

public interface LoginRepository extends CrudRepository<LoginStructure, String> {
}
