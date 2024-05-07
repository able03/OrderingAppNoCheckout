package com.example.orderingapp.models;

public class ProductModel
{
    private int product_id;
    private String name;
    private String description;
    private double price;
    private int ratings_id;
    private int imgRID;
    private String category;

    public ProductModel(int product_id, String name, String description, double price, int ratings_id, int imgRID, String category)
    {
        this.product_id = product_id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.ratings_id = ratings_id;
        this.imgRID = imgRID;
        this.category = category;
    }

    public int getProduct_id()
    {
        return product_id;
    }

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

    public double getPrice()
    {
        return price;
    }

    public int getRatings_id()
    {
        return ratings_id;
    }

    public int getImgRID()
    {
        return imgRID;
    }

    public String getCategory()
    {
        return category;
    }
}
