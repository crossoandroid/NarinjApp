package com.orange_team.narinjapp.interfaces; //H

import com.orange_team.narinjapp.model.Result;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Hayk on 07-Mar-17.
 */

public interface RetrofitInterface {

    @GET("get-chefs")
    Call<Result.NChefList> getAllChefs();

    @GET("get-dishes")
    Call<Result.NFoodList> getFoodByCategory(@QueryMap Map<String, String> paramsMap);


    @GET("get-dishes?page=0&count=10")
    Call<Result.NFoodList> getFoodByCategory1(@Query("category") String category);

    @GET("get-dishes-by-chef")
    Call<Result.NFoodList> getChefFoodList(@Query("chefId") Long category);



}
