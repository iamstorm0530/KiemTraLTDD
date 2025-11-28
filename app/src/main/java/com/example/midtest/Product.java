package com.example.midtest;

import com.google.gson.annotations.SerializedName;

public class Product {
    private String id;
    private String name;
    private int price;
    private String image;

    @SerializedName("categoryId")
    private String categoryID;

    public Product(String id, String name, int price, String image, String categoryID) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.categoryID = categoryID;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
