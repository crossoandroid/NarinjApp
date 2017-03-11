package com.orange_team.narinjapp.model; //H

import java.util.List;

/**
 * Created by Hayk on 07-Mar-17.
 */

public class Result {

    public static class NFood {

        public long dishId;
        public int price;
        public String name;
        public String description;
    //    public String picture;                                //must be amended
    //   public String chefName;

    }


    public static class NChef {

        public long partnerId;
        public String name;
        public String surName;
        public String phone;
   //     public String avatar;  //must be amended
    }
}
