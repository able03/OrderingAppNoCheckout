package com.example.orderingapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SplashScreenActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) ->
        {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        DBHelper dbHelper = new DBHelper(this);
        Cursor cursor = dbHelper.getProductCount();

        if(cursor.getCount() == 0)
        {
            dbHelper.embedProducts();
        }

        cursor.close();
        dbHelper.close();

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
               Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
               startActivity(intent);
            }
        }, 2000);

    }
}