package com.ibm.ir;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
class TrainingDataStructure {
   private List<Integer> emgData = new ArrayList<>();
   private Date start;
   private Date end;
   private String login;
}
