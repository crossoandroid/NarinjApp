package com.orange_team.narinjapp.model; //H

/**
 * Created by Hayk on 02-Mar-17.
 */

public class OrderedItem {

    private long dishId;
    private int quantity;

    public OrderedItem(long dishId, int quantity) {
        this.dishId = dishId;
        this.quantity = quantity;
    }

    public long getDishId() {
        return dishId;
    }

    public void setDishId(long dishId) {
        this.dishId = dishId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
