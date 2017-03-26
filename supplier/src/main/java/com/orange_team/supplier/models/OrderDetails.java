package com.orange_team.supplier.models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;

public class OrderDetails extends RealmObject {

    private String phone;

    private String userName;

    private String address;

    private String dateAndTime;

    private int realmIdentifier;    //0 if current order and 1 if order in history

    RealmList<OrderedDishAndCount> mOrderedDishAndCountList;

    @Ignore
    public static final String REALM_IDENTIFIER = "realmIdentifier";
    @Ignore
    public static final String DATE_AND_TIME = "dateAndTime";
    @Ignore
    public static final int CURRENT_OBJECT = 0;
    @Ignore
    public static final int HISTORY_OBJECT = 1;

    public static int getCurrentObject() {
        return CURRENT_OBJECT;
    }

    public static int getHistoryObject() {
        return HISTORY_OBJECT;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public RealmList<OrderedDishAndCount> getOrderedDishAndCountList() {
        return mOrderedDishAndCountList;
    }

    public void setOrderedDishAndCountList(RealmList<OrderedDishAndCount> orderedDishAndCountList) {
        mOrderedDishAndCountList = orderedDishAndCountList;
    }

    public int getRealmIdentifier() {
        return realmIdentifier;
    }

    public void setRealmIdentifier(int realmIdentifier) {
        this.realmIdentifier = realmIdentifier;
    }

    public OrderDetails() {
    }
}
