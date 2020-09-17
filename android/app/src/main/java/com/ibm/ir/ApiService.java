package com.ibm.ir;

import com.ibm.ir.model.ChartStructure;
import com.ibm.ir.model.LoginStructure;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("/registerUser")
    Call<Object> postLogin(@Body LoginStructure body);

    @POST("/chart")
    Call<List<Integer>> postLogin(@Body ChartStructure body);
}