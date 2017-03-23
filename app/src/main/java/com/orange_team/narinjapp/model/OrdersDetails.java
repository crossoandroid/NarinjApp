package com.orange_team.narinjapp.model;


public class OrdersDetails{

    private long id;
    private String date;

    public OrdersDetails() {

    }

    public OrdersDetails(long id, String date) {
        this.id = id;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
