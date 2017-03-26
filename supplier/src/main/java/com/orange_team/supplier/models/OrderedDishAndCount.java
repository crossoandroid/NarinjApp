package com.orange_team.supplier.models;


import io.realm.RealmObject;

public class OrderedDishAndCount extends RealmObject {


    private int count;

    private Dish dish;

    public OrderedDishAndCount(int count, Dish dish) {
        this.count = count;
        this.dish = dish;
    }

    public OrderedDishAndCount() {
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }



}
