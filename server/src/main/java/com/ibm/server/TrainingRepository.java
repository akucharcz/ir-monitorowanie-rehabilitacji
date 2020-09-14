package com.ibm.server;

import com.ibm.server.model.TrainingDataStructure;
import org.springframework.data.repository.CrudRepository;

public interface TrainingRepository extends CrudRepository<TrainingDataStructure, String> {
}
