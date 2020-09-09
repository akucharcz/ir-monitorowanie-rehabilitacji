package com.ibm.ir;

import com.ibm.ir.model.LoginStructure;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("/login")
    Call<Object> postLogin(@Body LoginStructure body);
}