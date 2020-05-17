package com.ibm.server;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document
class TrainingDataStructure {
    @Id
    private String id = null; // mongo sam doda
    private List<Integer> emgData = new ArrayList<>();
    private Date start;
    private Date end;
}
