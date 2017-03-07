package com.orange_team.narinjapp.model; //H

import java.util.List;

/**
 * Created by Hayk on 08-Mar-17.
 */

public class User {                               //The class will be used exceptionally for POST requests

    private String phone;
    private String comment;                       //name
    private String location;
    private int price;                            //total price
    private List<OrderedItem> dishOrders;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<OrderedItem> getDishOrders() {
        return dishOrders;
    }

    public void setDishOrders(List<OrderedItem> dishOrders) {
        this.dishOrders = dishOrders;
    }
}
