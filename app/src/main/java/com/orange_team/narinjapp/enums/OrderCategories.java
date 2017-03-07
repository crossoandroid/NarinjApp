package com.orange_team.narinjapp.enums;


public enum OrderCategories {
         SOUP("Ապուր"),
         SALAD("Աղցան"),
        RECEPTION("Ֆուրշետ"), //must be deleted
         LUNCH("Լանչ"),
         CAKE("Թխվածք"),
         HOT_DISHES("Ուտեստ"),
         GARNISH("Խավարտ"),
         CHEF("Խոհարար"),
         ALL("Բոլորը");
        String catName;

        OrderCategories(String catName) {
                this.catName=catName;
        }
        @Override
        public String toString() {
                return catName;
        }
}
