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
       // public Map<String, String> files;
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
