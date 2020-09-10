package com.ibm.server;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@RequiredArgsConstructor
public class TrainingController {

    private final TrainingRepository trainingRepository;

    @PostMapping("/training")
    public TrainingDataStructure saveTraining(@RequestBody TrainingDataStructure trainingDataStructure) {
        return trainingRepository.save(trainingDataStructure);
    }

}
