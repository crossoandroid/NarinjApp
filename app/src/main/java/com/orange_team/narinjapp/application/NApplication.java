package com.orange_team.narinjapp.application;

import android.app.Application;

import com.orange_team.narinjapp.interfaces.RetrofitInterface;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class NApplication extends Application{

    RetrofitInterface mRetrofitInterface;

    @Override
    public void onCreate() {
        super.onCreate();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://narinj.am/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mRetrofitInterface = retrofit.create(RetrofitInterface.class);
    }

    public RetrofitInterface getRetrofitInterface(){return mRetrofitInterface;}

}
