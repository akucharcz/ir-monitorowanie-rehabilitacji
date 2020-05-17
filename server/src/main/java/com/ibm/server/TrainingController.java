package com.ibm.server;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrainingController {

    private final TrainingRepository trainingRepository;

    public TrainingDataStructure saveTraining(TrainingDataStructure trainingDataStructure)
    {
       return trainingRepository.save(trainingDataStructure);
    }

}
