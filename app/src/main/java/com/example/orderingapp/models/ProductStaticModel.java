package com.example.orderingapp.models;

public class ProductStaticModel
{
    private static int product_id;
    private static String name;
    private static String description;
    private static double price;
    private static int ratings_id;
    private static int imgRID;
    private static String category;

    public ProductStaticModel(int product_id, String name, String description, double price, int ratings_id, int imgRID, String category) {
        this.product_id = product_id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.ratings_id = ratings_id;
        this.imgRID = imgRID;
        this.category = category;
    }

    public static int getProduct_id() {
        return product_id;
    }

    public static String getName() {
        return name;
    }

    public static String getDescription() {
        return description;
    }

    public static double getPrice() {
        return price;
    }

    public static int getRatings_id() {
        return ratings_id;
    }

    public static int getImgRID() {
        return imgRID;
    }

    public static String getCategory()
    {
        return category;
    }
}
