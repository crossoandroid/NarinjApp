package com.orange_team.supplier.models;

import java.io.Serializable;

public class Dish implements Serializable
{
    private String Name;

    private long DishId;

    public String getName ()
    {
        return Name;
    }

    public void setName (String name)
    {
        this.Name = name;
    }

    public long getDishId ()
    {
        return DishId;
    }

    public void setDishId (long dishId)
    {
        this.DishId = dishId;
    }

}