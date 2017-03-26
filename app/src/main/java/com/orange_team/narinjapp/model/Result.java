package com.orange_team.narinjapp.model;

import java.util.List;


public class Result {

    private List<Body> bodies;

    public List<Body> sendOrder()
    {
        return bodies;
    }

    public static class NFood {

        public long dishId;
        public int price;
        public String name;
        public String description;
        public List<NFoodFile> files;
        public class NFoodFile {
            public String path;
        }
    }


    public static class NChef {

        public long partnerId;
        public String name;
        public String surName;
        public String phone;
        public List<NChefFiles> files;
        public class NChefFiles {
            public String path;
        }
    }


}
