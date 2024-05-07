package com.example.orderingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper
{

    private Context context;
    private final String ELECTRONICS = "electronics", FASHION = "fashion", FOOD = "food";
    private static final String DB_NAME = "Order.db";
    private static final int DB_VER = 1;

    private static final String TABLE_ACCOUNTS = "accounts",
                                COLUMN_ID = "id",
                                COLUMN_NAME = "name",
                                COLUMN_ADDRESS = "address",
                                COLUMN_EMAIL = "email",
                                COLUMN_BIRTHDATE = "birthdate",
                                COLUMN_PHONE = "phone",
                                COLUMN_USERNAME = "username",
                                COLUMN_PASSWORD  = "password",
                                COLUMN_PIN = "pin",
                                COLUMN_USER_TYPE = "user_type";

    private static final String CREATE_TABLE_ACCOUNTS = "CREATE TABLE "+TABLE_ACCOUNTS+"(" +
            COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_NAME+" VARCHAR," +
            COLUMN_ADDRESS+" VARCHAR," +
            COLUMN_EMAIL+" VARCHAR," +
            COLUMN_BIRTHDATE+" VARCHAR," +
            COLUMN_PHONE+" VARCHAR," +
            COLUMN_USERNAME+" VARCHAR," +
            COLUMN_PASSWORD+" VARCHAR," +
            COLUMN_PIN+" VARCHAR UNIQUE)";

    private static final String DROP_TABLE_ACCOUNTS = "DROP TABLE IF EXISTS "+TABLE_ACCOUNTS;

    private static final String TABLE_PRODUCTS = "products", COLUMN_PRODUCT_ID = "id", COLUMN_PRODUCT_NAME = "name", COLUMN_PRODUCT_DESC = "description", COLUMN_PRODUCT_PRICE = "price", COLUMN_PRODUCT_RATINGS_ID = "ratings_id", COLUMN_PRODUCT_IMG_RID = "img_rid", COLUMN_PRODUCT_CATEGORY = "category";
    private static final String CREATE_PRODUCT_TABLE = "CREATE TABLE "+TABLE_PRODUCTS+"(" +
            COLUMN_PRODUCT_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_PRODUCT_NAME+" VARCHAR, " +
            COLUMN_PRODUCT_DESC+" VARCHAR, " +
            COLUMN_PRODUCT_PRICE+" DOUBLE, " +
            COLUMN_PRODUCT_RATINGS_ID+" INTEGER, " +
            COLUMN_PRODUCT_IMG_RID+" VARCHAR, " +
            COLUMN_PRODUCT_CATEGORY+" VARCHAR)";
    private static final String DROP_PRODUCTS_TABLE = "DROP TABLE IF EXISTS "+TABLE_PRODUCTS;


    private static final String TABLE_CART = "cart", COLUMN_CART_ID = "id", QUANTITY = "quantity", COLUMN_CART_ACCOUNT_ID = "account_id", COLUMN_CART_PRODUCT_ID = "product_id", COLUMN_CART_NAME = "name", COLUMN_CART_PRICE = "price", COLUMN_CART_IMG_RID = "imgRID";
    private static final String CREATE_TABLE_CART = "CREATE TABLE "+TABLE_CART+ "(" +
            COLUMN_CART_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_CART_PRODUCT_ID+" INTEGER, " +
            COLUMN_CART_ACCOUNT_ID+" INTEGER," +
            QUANTITY+" INTEGER," +
            COLUMN_CART_NAME+" VARCHAR, " +
            COLUMN_CART_PRICE+" VARCHAR, " +
            COLUMN_CART_IMG_RID+" VARCHAR)";
    private static final String DROP_TABLE_CART = "DROP TABLE IF EXISTS "+TABLE_CART;


    private static final String TABLE_ORDERS = "orders", COLUMN_ORDER_ID = "id", COLUMN_ORDEr_DELIVERY_DATE = "date", COLUMN_ORDER_ACCOUNT_ID = "account_id", COLUMN_ORDER_PRODUCT_ID = "product_id", COLUMN_ORDER_RATINGS = "ratings", COLUMN_ORDER_QR_CODE = "qrcode", COLUMN_ORDER_PRICE = "price";
    private static final String CREATE_TABLE_ORDER = "CREATE TABLE "+TABLE_ORDERS+ "(" +
            COLUMN_ORDER_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_ORDER_ACCOUNT_ID+" INTEGER," +
            COLUMN_ORDER_PRODUCT_ID+" INTEGER," +
            COLUMN_ORDER_RATINGS+" DOUBLE, " +
            COLUMN_ORDER_QR_CODE+" BLOB, " +
            COLUMN_ORDER_PRICE+" DOUBLE," +
            COLUMN_ORDEr_DELIVERY_DATE+" VARCHAR)";
    private static final String DROP_TABLE_ORDER = "DROP TABLE IF EXISTS " + TABLE_ORDERS;


    public boolean addOrder(int acc_id, int pro_id, double ratings, byte[] qr_code, double price, String delivery_date)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ORDER_ACCOUNT_ID, acc_id);
        values.put(COLUMN_ORDER_PRODUCT_ID, pro_id);
        values.put(COLUMN_ORDER_RATINGS, ratings);
        values.put(COLUMN_ORDER_ACCOUNT_ID, qr_code);
        values.put(COLUMN_ORDER_ACCOUNT_ID, price);
        values.put(COLUMN_ORDER_ACCOUNT_ID, delivery_date);

        long rowsAffected = db.insert(TABLE_ORDERS, null, values);

        if(rowsAffected != -1)
        {
            return true;
        }
        return false;

    }

    private static final String TABLE_VOUCHER = "vouchers", COLUMN_VOUCHER_ID = "id", COLUMN_VOUCHER_CODE = "code",
            COLUMN_VOUCHER_IMG_RID = "imgRID", COLUMN_VOUCHER_PERCENT = "percent", COLUMN_VOUCHER_ACCOUNT_ID = "account_id";

    private static final String CREATE_TABLE_VOUCHER = "CREATE TABLE "+TABLE_VOUCHER+ " (" +
            COLUMN_VOUCHER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_VOUCHER_CODE+" VARCHAR, " +
            COLUMN_VOUCHER_IMG_RID+" INTEGER, " +
            COLUMN_VOUCHER_PERCENT+" DOUBLE," +
            COLUMN_VOUCHER_ACCOUNT_ID+" INTEGER)";

    private static final String DROP_TABLE_VOUCHER = "DROP TABLE IF EXISTS " + TABLE_VOUCHER;



    public DBHelper(@Nullable Context context)
    {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_TABLE_ACCOUNTS);
        db.execSQL(CREATE_PRODUCT_TABLE);
        db.execSQL(CREATE_TABLE_CART);
        db.execSQL(CREATE_TABLE_ORDER);
        db.execSQL(CREATE_TABLE_VOUCHER);
        db.execSQL("CREATE TABLE embeddedVouchers(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "imgRID INTEGER, " +
                "percent DOUBLE, " +
                "code VARCHAR, " +
                "account_id INTEGER)");
    }

    public void addVouchers(int imgRID,  double percent, String code, int account_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("code", code);
        values.put("imgRID", imgRID);
        values.put("account_id", account_id);
        values.put("percent", percent);

        db.insert("embeddedVouchers", null, values);
        Log.d("Debugging", "voucher embed success");
    }

    public void deleteVouchers(String code)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("embeddedVouchers", "code = ?", new String[]{code});
    }

    public void embedVouchers(int account_id)
    {
        addVouchers( R.drawable.bd_voucher,  0.15, "bday", account_id);
        addVouchers( R.drawable.fs_voucher, 0.2, "fshipping",account_id);
        addVouchers( R.drawable.voucher_2, 0.10,"discount",account_id);
    }

    public Cursor getEmbeddedVouchers(int account_id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM embeddedVouchers WHERE account_id = '"+account_id+"'", null);
    }





    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(DROP_TABLE_ACCOUNTS);
        db.execSQL(DROP_PRODUCTS_TABLE);
        db.execSQL(DROP_TABLE_CART);
        db.execSQL(DROP_TABLE_ORDER);
        db.execSQL(DROP_TABLE_VOUCHER);
        db.execSQL("DROP TABLE IF EXISTS vouchers");
        onCreate(db);
    }


    public boolean addAccount(String name, String address, String email, String birthdate,
                              String phone, String username, String password, String pin)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_ADDRESS, address);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_BIRTHDATE, birthdate);
        values.put(COLUMN_PHONE, phone);
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_PIN, pin);;


        long rowsAffected = db.insert(TABLE_ACCOUNTS, null, values);

        if(rowsAffected != -1)
        {
            return true;
        }
        return false;
    }

    public Cursor findAccount(String username, String password)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM "+TABLE_ACCOUNTS+" WHERE "+COLUMN_USERNAME+ " = '"+username+"' AND "+COLUMN_PASSWORD+ " = '"+password+"'", null);
    }

    public Cursor findAccount(String pin)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM "+TABLE_ACCOUNTS+ " WHERE "+COLUMN_PIN+ " = '"+pin+"'", null);
    }

    public boolean addProduct(String name, String desc, double price, int ratings_id, int imgRID, String category)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
         values.put(COLUMN_PRODUCT_NAME, name);
        values.put(COLUMN_PRODUCT_DESC, desc);
        values.put(COLUMN_PRODUCT_PRICE, price);
        values.put(COLUMN_PRODUCT_RATINGS_ID, ratings_id);
        values.put(COLUMN_PRODUCT_IMG_RID, imgRID);
        values.put(COLUMN_PRODUCT_CATEGORY, category);


        long rowsAffected = db.insert(TABLE_PRODUCTS, null, values);
        Log.d("Debugging", ""+rowsAffected);
        if(rowsAffected != -1)
        {
            return true;
        }
        return false;
    }

    public Cursor getProduct(String category)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM "+TABLE_PRODUCTS+" WHERE "
                +COLUMN_PRODUCT_CATEGORY+ " = '"+category+"'", null);
    }

    public Cursor getProduct()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM "+TABLE_PRODUCTS, null);
    }

    public Cursor getProductCount()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM "+TABLE_PRODUCTS, null);
    }

    public Cursor FindSameProduct(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM cart WHERE name = '"+name+"'" , null);
        return c;
    }

    public boolean addCart(int account_id, int product_id, int quantity, String name, double price, int imgRID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CART_PRODUCT_ID, product_id);
        values.put(COLUMN_CART_ACCOUNT_ID, account_id);
        values.put(QUANTITY, quantity);
        values.put(COLUMN_CART_NAME, name);
        values.put(COLUMN_CART_PRICE, price);
        values.put(COLUMN_CART_IMG_RID, imgRID);

        long rowsAffected = db.insert(TABLE_CART, null, values);

        if(rowsAffected != -1)
        {
            return true;
        }
        return false;
    }

    public void UpdateCart(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM cart WHERE name = '"+name+"'", null);
        c.moveToFirst();
        if(c.getCount() > 0){
            int newQuantity = (c.getInt(3) + 1);
            try{
                String sql = "UPDATE cart SET quantity = '"+newQuantity+"' WHERE name = '"+name+"'";
                db.execSQL(sql);

            }catch (SQLException e){
                e.printStackTrace();

            }


        }
    }

    public boolean addCart(int account_id, int product_id, String name, double price, int imgRID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CART_PRODUCT_ID, product_id);
        values.put(COLUMN_CART_ACCOUNT_ID, account_id);
        values.put(COLUMN_CART_NAME, name);
        values.put(COLUMN_CART_PRICE, price);
        values.put(COLUMN_CART_IMG_RID, imgRID);

        long rowsAffected = db.insert(TABLE_CART, null, values);

        if(rowsAffected != -1)
        {
            return true;
        }
        return false;
    }

   /* public Cursor getCart(int account_id, int product_id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT cart.id, products.name, products.price, products.img_rid FROM cart INNER JOIN products ON cart.account_id = '" + account_id + "' AND products.id = '"+product_id+"'", null);

    }*/

    public Cursor getCart(int account_id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM "+TABLE_CART + " WHERE account_id = '"+account_id+"'", null);
    }



    public boolean deleteCart(int cart_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        long rowsAffected = db.delete(TABLE_CART, COLUMN_CART_ID + " = ?", new String[]{String.valueOf(cart_id)});

        if(rowsAffected > 0)
        {
            return true;
        }
        return false;
    }


    public boolean addVoucher(String code, int imgRID, double percent, int account_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_VOUCHER_CODE, code);
        values.put(COLUMN_VOUCHER_IMG_RID, imgRID);
        values.put(COLUMN_VOUCHER_PERCENT, percent);
        values.put(COLUMN_VOUCHER_ACCOUNT_ID, account_id);

        long rowsAffected = db.insert(TABLE_VOUCHER, null, values);

        if(rowsAffected != -1)
        {
            return true;
        }
        return false;
    }

    public Cursor getVoucher(String code, int account_id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM "+TABLE_VOUCHER+ " WHERE "+COLUMN_VOUCHER_CODE+" = '"+code+"' AND "+COLUMN_VOUCHER_ACCOUNT_ID+" = '"+account_id+"'", null);
    }

    public Cursor getVoucher(int account_id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_VOUCHER + " WHERE " +COLUMN_VOUCHER_ACCOUNT_ID+ " = '"+account_id+"'", null);
    }

    public int getVoucherCount(int account_id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " +TABLE_VOUCHER+ " WHERE " +COLUMN_VOUCHER_ACCOUNT_ID+ " = '"+account_id+"'", null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }


    public Cursor getAccount(String name, String username, String password)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM "+TABLE_ACCOUNTS+ " WHERE " + COLUMN_NAME + " = '"+name+"' AND " +COLUMN_USERNAME+ " = '"+username+"' AND " +COLUMN_PASSWORD+" = '"+password+"'", null);
    }

    public int getOrdersCount(int acc_id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM "+TABLE_ORDERS+ " WHERE " + COLUMN_ORDER_ACCOUNT_ID + " = '"+acc_id+"'", null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    public int getVCount(int acc_id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM "+TABLE_VOUCHER+ " WHERE " + COLUMN_VOUCHER_ACCOUNT_ID + " = '"+acc_id+"'", null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }










    public void embedProducts()
    {
        // Electronics
        addProduct( "SmartPhone (Samsung Galaxy S21)",
                "The Samsung Galaxy S21 features a stunning 6.2-inch AMOLED display, a powerful Exynos 2100 processor, triple rear cameras, and 5G capability.",
                45000,0, R.drawable.elec_samsung, ELECTRONICS);

        addProduct("Wireless Headphones (Sony WH-1000XM4)",
                "Sony WH-1000XM4 offers industry-leading noise cancellation, exceptional sound quality, long battery life, touch controls, and comfortable design.",
                16000, 0, R.drawable.elec_sony, ELECTRONICS);

        addProduct("Smartwatch (Apple Watch Series 6)",
                "Apple Watch Series 6 boasts advanced health tracking features, a bright Always-On Retina display, ECG app, GPS, and cellular connectivity.",
                25000, 0, R.drawable.elec_smarthwatch, ELECTRONICS);

        addProduct(" Laptop (Dell XPS 13)",
                "Dell XPS 13 is a sleek and powerful ultrabook with a stunning InfinityEdge display, fast performance, long battery life, and premium build quality.",
                60000, 0, R.drawable.elec_laptop, ELECTRONICS);

        addProduct("Wireless Speaker (JBL Charge 4)",
                "JBL Charge 4 is a portable Bluetooth speaker with powerful sound, IPX7 waterproof rating, built-in power bank, and 20 hours of battery life.",
                7500, 0, R.drawable.elec_speaker, ELECTRONICS);


        // Fashion
        addProduct("Men''s Watch (Seiko 5 Sports Automatic)",
                "Seiko 5 Sports Automatic watch features a stainless steel case, automatic movement, day-date display, and a classic stainless steel bracelet.",
                10000,0, R.drawable.fash_watch, FASHION);

        addProduct("Women''s Handbag (Louis Vuitton Speedy 30)",
                "Louis Vuitton Speedy 30 is an iconic handbag crafted from Monogram canvas, featuring rolled leather handles, spacious interior, and signature LV monogram.",
                80000,0, R.drawable.fash_bag, FASHION);

        addProduct("Sneakers (Nike Air Force 1)",
                "Nike Air Force 1 is a classic basketball-inspired sneaker with a timeless design, durable leather upper, cushioned midsole, and iconic Air-Sole unit.",
                5500,0, R.drawable.fash_nike, FASHION);

        addProduct("Designer Sunglasses (Ray-Ban Wayfarer Classic)",
                " Ray-Ban Wayfarer Classic sunglasses feature a timeless design, durable acetate frame, polarized lenses, and iconic Ray-Ban logo on the temples.",
                8000,0, R.drawable.fash_sunglass, FASHION);

        addProduct( "Women''s Dress (Zara Wrap Dress)",
                "Zara Wrap Dress is a stylish and versatile piece featuring a flattering wrap silhouette, V-neckline, tie waist, and flowy fabric perfect for any occasion.",
                2500,0, R.drawable.fash_dress, FASHION);

        // Food
        addProduct("Pizza (Pepperoni Pizza from Shakey''s)",
                "Shakey''s Pepperoni Pizza is a classic favorite with a crispy crust, savory tomato sauce, generous pepperoni slices, and melted mozzarella cheese.",
                400,0, R.drawable.food_pizza, FOOD);

        addProduct("Sushi (Assorted Sushi Platter from Sushi Nori)",
                "Sushi Nori''s Assorted Sushi Platter includes a variety of fresh nigiri and maki sushi, featuring tuna, salmon, California rolls, and more.",
                900,0, R.drawable.food_sushi, FOOD);

        addProduct("Burger (Double Cheeseburger from McDonald''s)",
                "McDonald''s Double Cheeseburger is a delicious combination of two juicy beef patties, melted cheese slices, pickles, onions, ketchup, and mustard, all sandwiched in a soft bun.",
                150,0, R.drawable.food_burger, FOOD);

        addProduct("Coffee (Starbucks Caramel Macchiato)",
                "Starbucks Caramel Macchiato is a delicious espresso-based beverage with velvety steamed milk, rich espresso, and a sweet caramel drizzle.",
                180,0, R.drawable.food_coffee_tall, FOOD);
    }









}
