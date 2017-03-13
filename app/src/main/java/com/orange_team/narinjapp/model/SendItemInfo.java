package com.orange_team.narinjapp.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendItemInfo {
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("dishOrders")
    @Expose
    private List<DishOrder> dishOrders = null;

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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<DishOrder> getDishOrders() {
        return dishOrders;
    }

    public void setDishOrders(List<DishOrder> dishOrders) {
        this.dishOrders = dishOrders;
    }
}
