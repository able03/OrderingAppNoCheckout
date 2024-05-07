package com.example.orderingapp;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Gainmap;
import android.util.Log;

import com.example.orderingapp.models.ProductModel;

import java.util.ArrayList;
import java.util.List;

public class LoadItems
{

    private List<ProductModel> electronics;
    private List<ProductModel> fashion;
    private List<ProductModel> food;
    private List<ProductModel> all;
    private DBHelper dbHelper;
    private final String ELECTRONICS = "electronics",
            FASHION = "fashion", FOOD = "food";
    private Context context;

    public LoadItems(Context context)
    {
        this.context = context;
    }

    /*public List<ProductModel> getAllList()
    {
        all = new ArrayList<>();



        //ELECTRONICS
        all.add(new ProductModel(1, "SmartPhone (Samsung Galaxy S21)",
                "The Samsung Galaxy S21 features a stunning 6.2-inch AMOLED display, a powerful Exynos 2100 processor, triple rear cameras, and 5G capability.",
                45000,0, R.drawable.elec_samsung, ELECTRONICS));

        all.add(new ProductModel(2, "Wireless Headphones (Sony WH-1000XM4)",
                "Sony WH-1000XM4 offers industry-leading noise cancellation, exceptional sound quality, long battery life, touch controls, and comfortable design.",
                16000, 0, R.drawable.elec_sony, ELECTRONICS));

        all.add(new ProductModel(3, "Smartwatch (Apple Watch Series 6)",
                "Apple Watch Series 6 boasts advanced health tracking features, a bright Always-On Retina display, ECG app, GPS, and cellular connectivity.",
                25000, 0, R.drawable.elec_smarthwatch, ELECTRONICS));


        all.add(new ProductModel(4, " Laptop (Dell XPS 13)",
                "Dell XPS 13 is a sleek and powerful ultrabook with a stunning InfinityEdge display, fast performance, long battery life, and premium build quality.",
                60000, 0, R.drawable.elec_laptop, ELECTRONICS));

        all.add(new ProductModel(5, "Wireless Speaker (JBL Charge 4)",
                "JBL Charge 4 is a portable Bluetooth speaker with powerful sound, IPX7 waterproof rating, built-in power bank, and 20 hours of battery life.",
                7500, 0, R.drawable.elec_speaker, ELECTRONICS));


        //FASHION
        all.add(new ProductModel(6, "Men's Watch (Seiko 5 Sports Automatic)",
                "Seiko 5 Sports Automatic watch features a stainless steel case, automatic movement, day-date display, and a classic stainless steel bracelet.",
                10000,0, R.drawable.fash_watch, FASHION));

        all.add(new ProductModel(7, "Women's Handbag (Louis Vuitton Speedy 30)",
                "Louis Vuitton Speedy 30 is an iconic handbag crafted from Monogram canvas, featuring rolled leather handles, spacious interior, and signature LV monogram.",
                80000,0, R.drawable.fash_bag, FASHION));

        all.add(new ProductModel(8, "Sneakers (Nike Air Force 1)",
                "Nike Air Force 1 is a classic basketball-inspired sneaker with a timeless design, durable leather upper, cushioned midsole, and iconic Air-Sole unit.",
                5500,0, R.drawable.fash_nike, FASHION));

        all.add(new ProductModel(9, "Designer Sunglasses (Ray-Ban Wayfarer Classic)",
                " Ray-Ban Wayfarer Classic sunglasses feature a timeless design, durable acetate frame, polarized lenses, and iconic Ray-Ban logo on the temples.",
                8000,0, R.drawable.fash_sunglass, FASHION));

        all.add(new ProductModel(10, "Women's Dress (Zara Wrap Dress)",
                "Zara Wrap Dress is a stylish and versatile piece featuring a flattering wrap silhouette, V-neckline, tie waist, and flowy fabric perfect for any occasion.",
                2500,0, R.drawable.fash_dress, FASHION));



        //FOOD
        all.add(new ProductModel(11, "Pizza (Pepperoni Pizza from Shakey's)",
                "Shakey's Pepperoni Pizza is a classic favorite with a crispy crust, savory tomato sauce, generous pepperoni slices, and melted mozzarella cheese.",
                400,0, R.drawable.food_pizza, FOOD));

        all.add(new ProductModel(12, "Sushi (Assorted Sushi Platter from Sushi Nori)",
                "Sushi Nori's Assorted Sushi Platter includes a variety of fresh nigiri and maki sushi, featuring tuna, salmon, California rolls, and more.",
                900,0, R.drawable.food_sushi, FOOD));

        all.add(new ProductModel(13, "Burger (Double Cheeseburger from McDonald's)",
                "McDonald's Double Cheeseburger is a delicious combination of two juicy beef patties, melted cheese slices, pickles, onions, ketchup, and mustard, all sandwiched in a soft bun.",
                150,0, R.drawable.food_burger, FOOD));

        all.add(new ProductModel(14, "Coffee (Starbucks Caramel Macchiato)",
                "Starbucks Caramel Macchiato is a delicious espresso-based beverage with velvety steamed milk, rich espresso, and a sweet caramel drizzle.",
                180,0, R.drawable.food_coffee_tall, FOOD));






        return all;
    }

    public List<ProductModel> getElectronicsList()
    {
        electronics = new ArrayList<>();

        electronics.add(new ProductModel(1, "SmartPhone (Samsung Galaxy S21)",
                "The Samsung Galaxy S21 features a stunning 6.2-inch AMOLED display, a powerful Exynos 2100 processor, triple rear cameras, and 5G capability.",
                45000,0, R.drawable.elec_samsung, ELECTRONICS));

        electronics.add(new ProductModel(2, "Wireless Headphones (Sony WH-1000XM4)",
                "Sony WH-1000XM4 offers industry-leading noise cancellation, exceptional sound quality, long battery life, touch controls, and comfortable design.",
                16000, 0, R.drawable.elec_sony, ELECTRONICS));

        electronics.add(new ProductModel(3, "Smartwatch (Apple Watch Series 6)",
                "Apple Watch Series 6 boasts advanced health tracking features, a bright Always-On Retina display, ECG app, GPS, and cellular connectivity.",
                25000, 0, R.drawable.elec_smarthwatch, ELECTRONICS));


        electronics.add(new ProductModel(4, " Laptop (Dell XPS 13)",
                "Dell XPS 13 is a sleek and powerful ultrabook with a stunning InfinityEdge display, fast performance, long battery life, and premium build quality.",
                60000, 0, R.drawable.elec_laptop, ELECTRONICS));

        electronics.add(new ProductModel(5, "Wireless Speaker (JBL Charge 4)",
                "JBL Charge 4 is a portable Bluetooth speaker with powerful sound, IPX7 waterproof rating, built-in power bank, and 20 hours of battery life.",
                7500, 0, R.drawable.elec_speaker, ELECTRONICS));

        return electronics;
    }

    public List<ProductModel> getFashionList()
    {
        fashion = new ArrayList<>();

        fashion.add(new ProductModel(6, "Men's Watch (Seiko 5 Sports Automatic)",
                "Seiko 5 Sports Automatic watch features a stainless steel case, automatic movement, day-date display, and a classic stainless steel bracelet.",
                10000,0, R.drawable.fash_watch, FASHION));

        fashion.add(new ProductModel(7, "Women's Handbag (Louis Vuitton Speedy 30)",
                "Louis Vuitton Speedy 30 is an iconic handbag crafted from Monogram canvas, featuring rolled leather handles, spacious interior, and signature LV monogram.",
                80000,0, R.drawable.fash_bag, FASHION));

        fashion.add(new ProductModel(8, "Sneakers (Nike Air Force 1)",
                "Nike Air Force 1 is a classic basketball-inspired sneaker with a timeless design, durable leather upper, cushioned midsole, and iconic Air-Sole unit.",
                5500,0, R.drawable.fash_nike, FASHION));

        fashion.add(new ProductModel(9, "Designer Sunglasses (Ray-Ban Wayfarer Classic)",
                " Ray-Ban Wayfarer Classic sunglasses feature a timeless design, durable acetate frame, polarized lenses, and iconic Ray-Ban logo on the temples.",
                8000,0, R.drawable.fash_sunglass, FASHION));

        fashion.add(new ProductModel(10, "Women's Dress (Zara Wrap Dress)",
                "Zara Wrap Dress is a stylish and versatile piece featuring a flattering wrap silhouette, V-neckline, tie waist, and flowy fabric perfect for any occasion.",
                2500,0, R.drawable.fash_dress, FASHION));


        return fashion;
    }

    public List<ProductModel> getFoodList()
    {
        food = new ArrayList<>();

        food.add(new ProductModel(11, "Pizza (Pepperoni Pizza from Shakey's)",
                "Shakey's Pepperoni Pizza is a classic favorite with a crispy crust, savory tomato sauce, generous pepperoni slices, and melted mozzarella cheese.",
                400,0, R.drawable.food_pizza, FOOD));

        food.add(new ProductModel(12, "Sushi (Assorted Sushi Platter from Sushi Nori)",
                "Sushi Nori's Assorted Sushi Platter includes a variety of fresh nigiri and maki sushi, featuring tuna, salmon, California rolls, and more.",
                900,0, R.drawable.food_sushi, FOOD));

        food.add(new ProductModel(13, "Burger (Double Cheeseburger from McDonald's)",
                "McDonald's Double Cheeseburger is a delicious combination of two juicy beef patties, melted cheese slices, pickles, onions, ketchup, and mustard, all sandwiched in a soft bun.",
                150,0, R.drawable.food_burger, FOOD));

        food.add(new ProductModel(14, "Coffee (Starbucks Caramel Macchiato)",
                "Starbucks Caramel Macchiato is a delicious espresso-based beverage with velvety steamed milk, rich espresso, and a sweet caramel drizzle.",
                180,0, R.drawable.food_coffee_tall, FOOD));



        return food;
    }*/

    public List<ProductModel> getAllList()
    {
        dbHelper = new DBHelper(context);
        all = new ArrayList<>();

        Cursor cursor = dbHelper.getProduct();
        while (cursor.moveToNext())
        {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String desc = cursor.getString(2);
            double price = cursor.getDouble(3);
            int ratingsID = cursor.getInt(4);
            int imgRID = cursor.getInt(5);
            String category = cursor.getString(6);

            all.add(new ProductModel(id, name, desc, price, ratingsID, imgRID, category));

            Log.d("Debugging", name + " : " + imgRID);
        }


        dbHelper.close();

        return all;
    }


    public List<ProductModel> getElectronicsList()
    {
        dbHelper = new DBHelper(context);
        electronics = new ArrayList<>();

        Cursor cursor = dbHelper.getProduct(ELECTRONICS);
        while (cursor.moveToNext())
        {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String desc = cursor.getString(2);
            double price = cursor.getDouble(3);
            int ratingsID = cursor.getInt(4);
            int imgRID = cursor.getInt(5);
            String category = cursor.getString(6);

            electronics.add(new ProductModel(id, name, desc, price, ratingsID, imgRID, category));
        }

        cursor.close();
        dbHelper.close();
        return electronics;
    }

    public List<ProductModel> getFashionList()
    {
        dbHelper = new DBHelper(context);
        fashion = new ArrayList<>();

        Cursor cursor = dbHelper.getProduct(FASHION);
        while (cursor.moveToNext())
        {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String desc = cursor.getString(2);
            double price = cursor.getDouble(3);
            int ratingsID = cursor.getInt(4);
            int imgRID = cursor.getInt(5);
            String category = cursor.getString(6);

            fashion.add(new ProductModel(id, name, desc, price, ratingsID, imgRID, category));
        }

        cursor.close();
        dbHelper.close();
        return fashion;
    }

    public List<ProductModel> getFoodList()
    {
        dbHelper = new DBHelper(context);
        food = new ArrayList<>();

        Cursor cursor = dbHelper.getProduct(FOOD);
        while (cursor.moveToNext())
        {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String desc = cursor.getString(2);
            double price = cursor.getDouble(3);
            int ratingsID = cursor.getInt(4);
            int imgRID = cursor.getInt(5);
            String category = cursor.getString(6);

            food.add(new ProductModel(id, name, desc, price, ratingsID, imgRID, category));
        }

        cursor.close();
        dbHelper.close();

        return food;
    }


}
