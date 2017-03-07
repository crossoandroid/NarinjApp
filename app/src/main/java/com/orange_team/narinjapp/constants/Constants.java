package com.orange_team.narinjapp.constants;



public class Constants {

    public static final int SINGLE_ORDER_QUANTITY = 1;
    public static int CART_COUNT = 0;
    public static final String LOG_TAG = "MyLogs";
    public static final String MAKE_ORDER = "Order";
    public static final String DO_NOT_ORDER = "Cancel";
    public static final String CATEGORY_KEY = "Category";

    public static int increaseCartCount() {
        return Constants.CART_COUNT = Constants.CART_COUNT + Constants.SINGLE_ORDER_QUANTITY;
    }

    public static int decreaseCartCount() {
        return Constants.CART_COUNT = Constants.CART_COUNT - Constants.SINGLE_ORDER_QUANTITY;
    }
}
