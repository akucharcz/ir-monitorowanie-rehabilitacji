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

   public TrainingDataStructure returnData(){
       return this.trainingDataStructure;
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
