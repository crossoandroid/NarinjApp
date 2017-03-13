package com.orange_team.narinjapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dish {                               //The class will be used exceptionally for POST requests

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("dishId")
    @Expose
    private Integer dishId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDishId() {
        return dishId;
    }

    public void setDishId(Integer dishId) {
        this.dishId = dishId;
    }
}
