package com.orange_team.narinjapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dish
{
    private String name;

    private long dishId;

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public long getDishId ()
    {
        return dishId;
    }

    public void setDishId (long dishId)
    {
        this.dishId = dishId;
    }

}