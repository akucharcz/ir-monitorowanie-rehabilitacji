package com.ibm.server.repository;

import com.ibm.server.model.TrainingDataStructure;
import org.springframework.data.repository.CrudRepository;

public interface TrainingRepository extends CrudRepository<TrainingDataStructure, String> {
}
