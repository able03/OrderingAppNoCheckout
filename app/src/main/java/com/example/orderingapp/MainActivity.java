package com.example.orderingapp;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.orderingapp.fragments.CartFragment;
import com.example.orderingapp.fragments.FavoritesFragment;
import com.example.orderingapp.fragments.HomeFragment;
import com.example.orderingapp.fragments.OrdersFragment;
import com.example.orderingapp.fragments.ProfileFragment;
import com.example.orderingapp.models.AccountStaticModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
//        new AccountStaticModel(0, null, null, null, null, null, null, null, null);

        initValues();
        setListener();
        setFragment(new HomeFragment());
    }

    private void initValues()
    {
        bottomNavigationView = findViewById(R.id.bottomNav);
    }
    private void setListener()
    {
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                if(id == R.id.menu_home)
                {
                    setFragment(new HomeFragment());
                }
                else if(id == R.id.menu_orders)
                {
                    setFragment(new OrdersFragment());
                }
              /*  else if(id == R.id.menu_favorites)
                {
                    setFragment(new FavoritesFragment());
                }*/
                else if(id == R.id.menu_profile)
                {
                    setFragment(new ProfileFragment());
                }
                else if (id == R.id.menu_cart)
                {
                    setFragment(new CartFragment());
                }

                return true;
            }
        });
    }

    private void setFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }


}