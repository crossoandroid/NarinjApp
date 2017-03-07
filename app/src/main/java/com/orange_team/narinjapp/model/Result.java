package com.orange_team.narinjapp.model;

import java.util.List;

/**
 * Created by Hayk on 07-Mar-17.
 */

public class Result {
    public static class JSONFoodList {
        public List<JSONFood> items;
    }

    public static class JSONFood {
       // public long dishId;
        public String name;
        public String description;
                                        //picture url
                                        //chef name
        public int price;
    }

    public static class NChefList {
        public List<NChef> chefList;
    }

    public static class NChef {       //must be improved
        public String name;

    }
}
