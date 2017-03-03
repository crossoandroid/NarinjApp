package com.orange_team.narinjapp.model;

/**
 * Created by Hayk on 02-Mar-17.
 */

public class OrderedItem {

    private String itemLink;
    private int quantity; //

    public OrderedItem(String itemLink, int quantity) {
        this.itemLink = itemLink;
        this.quantity = quantity;
    }

    public String getItemLink() {
        return itemLink;
    }

    public void setItemLink(String itemLink) {
        this.itemLink = itemLink;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
