package com.ibm.server;

import com.ibm.server.model.TrainingDataStructure;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TrainingController {

    private final TrainingRepository trainingRepository;

    @PostMapping("/training")
    public TrainingDataStructure saveTraining(@RequestBody TrainingDataStructure trainingDataStructure) {
        return trainingRepository.save(trainingDataStructure);
    }

    public List<TrainingDataStructure> getAllTrainings() {
        return (List<TrainingDataStructure>) trainingRepository.findAll();
    }
}
