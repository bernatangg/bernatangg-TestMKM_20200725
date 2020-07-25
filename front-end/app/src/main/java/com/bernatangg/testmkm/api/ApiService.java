package com.bernatangg.testmkm.api;

import com.bernatangg.testmkm.model.Login;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

    @POST("ApiLogin.php")
    @FormUrlEncoded
    Call<Login> postLogin(@Field("username") String username,
                          @Field("password") String password);

    @POST("ApiRegister.php")
    @FormUrlEncoded
    Call<Login> postRegister(@Field("username") String username,
                             @Field("password") String password);


}
