package com.orange_team.narinjapp.enums;


public enum OrderCategories {
        SOUP("Ապուր"),                 //apur
        SALAD("Աղցան"),                //axcanner
        RECEPTION("Ֆուրշետ"),            //furshetner
        LUNCH("Լանչ"),                //lunch
        CAKE("Թխվածք"),                 //txvacqner
        MEAL("Ուտեստ"),                 //utestner
        GARNISH("Խավարտ"),              //xavartner
        COOKS("Խոհարար"),                //xohararner,
        ALL("Բոլորը");                  //boloryxz
        String catName;
        OrderCategories(String catName) {
                this.catName=catName;
        }
        @Override
        public String toString() {
                return catName;
        }
}
