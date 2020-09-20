package com.ibm.ir;

import com.ibm.ir.model.ChartStructure;
import com.ibm.ir.model.LoginStructure;
import com.ibm.ir.model.ResultStructure;

import java.time.LocalDateTime;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("/registerUser")
    Call<Object> postLogin(@Body LoginStructure body);

    @POST("/chart")
    Call<List<Integer>> postLogin(@Body ChartStructure body);

    @POST("/lastResult")
    Call<Object> postLogin(@Body String body);
}