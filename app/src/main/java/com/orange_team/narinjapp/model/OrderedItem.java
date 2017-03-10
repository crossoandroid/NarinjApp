package com.orange_team.narinjapp.model; //H

/**
 * Created by Hayk on 02-Mar-17.
 */

public class OrderedItem {

    private long dishId;
    private int count;

    public OrderedItem(long dishId, int count) {
        this.dishId = dishId;
        this.count = count;
    }

    public long getDishId() {
        return dishId;
    }

    public void setDishId(long dishId) {
        this.dishId = dishId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
