package com.orange_team.narinjapp.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Body
{
    @SerializedName("phone")
    private String phone;
    @SerializedName("comment")
    private String comment;
    @SerializedName("price")
    private int price;
    @SerializedName("location")
    private String location;
    @SerializedName("dishOrders[]")
    private List<DishOrders> dishOrders;

    private String Status;
    private String SupplierID;
    private String Key;
    private String Name;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getSupplierID() {
        return SupplierID;
    }

    public void setSupplierID(String supplierID) {
        SupplierID = supplierID;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

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