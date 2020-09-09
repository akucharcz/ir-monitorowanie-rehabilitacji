package com.ibm.ir;

import com.ibm.ir.model.TrainingDataStructure;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.Subscriber;

public class DataSubscriber extends Subscriber<Integer> {

   private TrainingDataStructure trainingDataStructure = new TrainingDataStructure();

   public void setEndDate(){
       trainingDataStructure.setEnd(new Date());
   }

   public List<Integer> returnData(){
       // TODO
       //will return in next part steps when there will be connection to server
  /*     TrainingDataStructure.builder()
               .emgData(new ArrayList<>(trainingDataStructure.emgData))
               .start(trainingDataStructure.start) // check if is immutable and wont reset
               .end(trainingDataStructure.end)
               .build();*/
       return new ArrayList<>(trainingDataStructure.getEmgData());
   }

   public void resetModel(){
       trainingDataStructure = new TrainingDataStructure();
   }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
    }

    @Override
    public void onNext(Integer integer) {
        if (trainingDataStructure.getEmgData().isEmpty()) {
            trainingDataStructure.setStart(new Date());
        }
        trainingDataStructure.getEmgData().add(integer);
    }
}
