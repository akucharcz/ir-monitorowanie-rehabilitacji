package com.ibm.server.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Document
public class TrainingDataStructure {
    @Id
    private String id = null; // mongo sam doda
    private List<Integer> emgData = new ArrayList<>();
    private LocalDateTime start;
    private LocalDateTime end;
    private String login;
}
