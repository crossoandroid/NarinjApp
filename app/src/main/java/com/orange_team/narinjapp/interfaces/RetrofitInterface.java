package com.orange_team.narinjapp.interfaces; //H

import com.orange_team.narinjapp.model.Result;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Hayk on 07-Mar-17.
 */

public interface RetrofitInterface {

    @GET("get-chefs?page=0&count=10")
    Call<List<Result.NChef>> getAllChefs();


    @GET("get-dishes")
    Call<List<Result.NFood>> getFoodByCategory(@Query("category") String category, @Query(value = "page")int page, @Query(value = "count")int count);

    @GET("get-dishes-by-chef")
    Call<List<Result.NFood>> getChefFoodList(@Query("chefId") Long chefId,  @Query(value = "page")int page, @Query(value = "count")int count);

   /* @FormUrlEncoded
    @POST("make-order")
    public void sendToServer(@Field("phone") String phone, @Field("comment") String comment, @Field("price") int price, @Field("location") String location, @Field("dishOrders[]")List<SendItemInfo> dishOrders);
*/
}
