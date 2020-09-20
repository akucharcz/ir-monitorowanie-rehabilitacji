package com.ibm.ir;

import com.ibm.ir.model.ChartStructure;
import com.ibm.ir.model.LoginStructure;
import com.ibm.ir.model.ResultStructure;
import com.ibm.ir.model.TrainingDataStructure;

import java.time.LocalDateTime;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("/registerUser")
    Call<Object> getLastResult(@Body LoginStructure body);

    @POST("/chart")
    Call<List<Integer>> getLastResult(@Body ChartStructure body);

    @POST("/lastResult")
    Call<Object> getLastResult(@Body String body);

    @POST("/training")
    Call<Object> postTrainingResults(@Body TrainingDataStructure trainingDataStructure);
}