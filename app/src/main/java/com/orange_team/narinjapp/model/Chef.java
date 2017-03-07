package com.orange_team.narinjapp.model; //H

import java.util.List;

/**
 * Created by Hayk on 07-Mar-17.
 */

public class Chef {

    private long id;
    private String mName;
    private String mSurname;
    private String mPhone;
    private String mAvatar;
    private List<Food> mFoodList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getSurname() {
        return mSurname;
    }

    public void setSurname(String surname) {
        mSurname = surname;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public List<Food> getFoodList() {
        return mFoodList;
    }

    public void setFoodList(List<Food> foodList) {
        mFoodList = foodList;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public void setAvatar(String avatar) {
        mAvatar = avatar;
    }
}
