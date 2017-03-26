package com.orange_team.narinjapp.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Body
{
    private String phone;
    private String comment;
    private int price;
    private String location;
    private List<DishOrders> dishOrders;

    public String getPhone ()
    {
        return phone;
    }

    public void setPhone (String phone)
    {
        this.phone = phone;
    }
    public String getComment ()
    {
        return comment;
    }

    public void setComment (String comment)
    {
        this.comment = comment;
    }
    public int getPrice ()
    {
        return price;
    }

    public void setPrice (int price)
    {
        this.price = price;
    }

    public String getLocation ()
    {
        return location;
    }

    public void setLocation (String location)
    {
        this.location = location;
    }



    public List<DishOrders> getDishOrders() {
        return dishOrders;
    }

    public void setDishOrders(List<DishOrders> dishOrders) {
        this.dishOrders = dishOrders;
    }
}