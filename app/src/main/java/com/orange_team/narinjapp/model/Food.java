package com.orange_team.narinjapp.model;


import java.net.URL;

public class Food {
    public String name;
    public String price; //
    public String desc;
    public String cookName;
    URL url;

    public String getCookName() {
        return cookName;
    }

    public void setCookName(String cookName) {
        this.cookName = cookName;
    }

    public Food() {
        name = "Anun";
        price = "1500 dr";
        desc = "sadjsa sadhgsah  sagaskcja sacsabjfksag asasf";
        cookName = "sajdhsha";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }
}
