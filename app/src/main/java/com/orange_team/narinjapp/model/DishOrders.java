package com.orange_team.narinjapp.model;

/**
 * Created by User on 11.03.2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DishOrders
{
    @SerializedName("count")
    private int count;
    @SerializedName("dish")
    private Dish dish;

    public int getCount ()
    {
        return count;
    }

    public void setCount (int count)
    {
        this.count = count;
    }

    public Dish getDish ()
    {
        return dish;
    }

    public void setDish (Dish dish)
    {
        this.dish = dish;
    }



}