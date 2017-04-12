package com.orange_team.supplier.models;

import java.io.Serializable;

public class DishOrders implements Serializable
{
    private int Count;
    private Dish Dish;

    public int getCount ()
    {
        return Count;
    }

    public void setCount (int count)
    {
        this.Count = count;
    }

    public Dish getDish ()
    {
        return Dish;
    }

    public void setDish (Dish dish)
    {
        this.Dish = dish;
    }



}