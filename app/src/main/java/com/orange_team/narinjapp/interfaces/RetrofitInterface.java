package com.orange_team.narinjapp.interfaces; //H

import com.orange_team.narinjapp.model.ItemRequest;
import com.orange_team.narinjapp.model.Result;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitInterface {

    @GET("get-chefs?page=0&count=10")
    Call<List<Result.NChef>> getAllChefs();


    @GET("get-dishes")
    Call<List<Result.NFood>> getFoodByCategory(@Query("category") String category, @Query(value = "page")int page, @Query(value = "count")int count);

    @GET("get-dishes-by-chef")
    Call<List<Result.NFood>> getChefFoodList(@Query("chefId") Long chefId,  @Query(value = "page")int page, @Query(value = "count")int count);

    @FormUrlEncoded
    @POST("make-order")
    Call<ItemRequest> sendItems(@Body ItemRequest dishorders);

}
