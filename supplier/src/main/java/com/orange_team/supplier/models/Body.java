package com.orange_team.supplier.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Body implements Serializable{
    private String Phone;
    private String Comment;
    private int Price;
    private String Location;
    private ArrayList<DishOrders> DishOrders;
    private String SupplierId;
    private String Status;
    private String Key;
    private String Name;

    public String getPhone ()
    {
        return Phone;
    }

    public void setPhone (String phone)
    {
        this.Phone = phone;
    }
    public String getComment ()
    {
        return Comment;
    }

    public void setComment (String comment)
    {
        this.Comment = comment;
    }
    public int getPrice ()
    {
        return Price;
    }

    public void setPrice (int price)
    {
        this.Price = price;
    }

    public String getLocation ()
    {
        return Location;
    }

    public void setLocation (String location)
    {
        this.Location = location;
    }



    public ArrayList<DishOrders> getDishOrders() {
        return DishOrders;
    }

    public void setDishOrders(ArrayList<DishOrders> dishOrders) {
        this.DishOrders = dishOrders;
    }

    public String getSupplierId() {
        return SupplierId;
    }

    public void setSupplierId(String supplierId) {
        SupplierId = supplierId;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
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
}