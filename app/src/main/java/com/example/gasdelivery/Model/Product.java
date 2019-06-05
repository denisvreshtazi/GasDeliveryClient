package com.example.gasdelivery.Model;

public class Product {
    private String Name, Image, CategoryId, Price;

    public Product() {
    }

    public Product(String name, String image, String categoryId, String price) {
        Name = name;
        Image = image;
        CategoryId = categoryId;
        Price = price;

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
}
