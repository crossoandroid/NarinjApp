package com.orange_team.narinjapp.model;

/**
 * Created by User on 11.03.2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DishOrder {

    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("dish")
    @Expose
    private Dish dish;

    public DishOrder(Integer id,int count)
    {

    }
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

}
