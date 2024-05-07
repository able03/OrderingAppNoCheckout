package com.example.orderingapp;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.orderingapp.models.AccountStaticModel;
import com.example.orderingapp.models.ProductStaticModel;
import com.google.android.material.button.MaterialButton;

public class ProductClickedActivity extends AppCompatActivity {

    TextView tv_name, tv_price, tv_desc;
    ImageView iv_back, iv_head, iv_fav, iv_favs;
    MaterialButton btn_cart, btn_checkout;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_clicked);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initValues();

        if(AccountStaticModel.getId() > 0)
        {
            btn_checkout.setEnabled(true);
            btn_cart.setEnabled(true);
        }
        else
        {
            btn_checkout.setEnabled(false);
            btn_cart.setEnabled(false);
        }

        tv_name.setText(ProductStaticModel.getName());
        tv_price.setText(String.valueOf(ProductStaticModel.getPrice()));
        tv_desc.setText(ProductStaticModel.getDescription());
        iv_head.setImageResource(ProductStaticModel.getImgRID());
        setListener();

        dbHelper.close();

    }

    private void initValues()
    {
        tv_name = findViewById(R.id.tvProductName);
        tv_price = findViewById(R.id.tvProductPrice);
        tv_desc = findViewById(R.id.tvProductDescription);

        iv_back = findViewById(R.id.ivBack);
        iv_head = findViewById(R.id.vTemp);
        iv_fav = findViewById(R.id.ivFav);
        iv_favs = findViewById(R.id.ivFav1);

        btn_cart = findViewById(R.id.btnClickedAddToCart);
        btn_checkout = findViewById(R.id.btnClickedCheckout);

        dbHelper = new DBHelper(this);

    }

    private void setListener()
    {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        iv_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_fav.setVisibility(View.GONE);

                iv_favs.setVisibility(View.VISIBLE);

            }
        });


        iv_favs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_fav.setVisibility(View.VISIBLE);

                iv_favs.setVisibility(View.GONE);

            }
        });



       /* btn_cart.setOnClickListener(addToCart -> {
            if(dbHelper.addCart(AccountStaticModel.getId(), ProductStaticModel.getProduct_id(), ProductStaticModel.getName(), ProductStaticModel.getPrice(), ProductStaticModel.getImgRID()))
            {
                Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show();
                finish();
            }else{
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
*/
        btn_cart.setOnClickListener(addToCart -> {
            Cursor c = dbHelper.FindSameProduct(ProductStaticModel.getName());
            if(c.getCount() > 0){
                Toast.makeText(this, "There's a similar product inside the cart", Toast.LENGTH_SHORT).show();
                dbHelper.UpdateCart(ProductStaticModel.getName());
                finish();
            }else{
                if(dbHelper.addCart(AccountStaticModel.getId(), ProductStaticModel.getProduct_id(), 1, ProductStaticModel.getName(), ProductStaticModel.getPrice(), ProductStaticModel.getImgRID()));
                Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show();
                finish();
            }

           /* if(dbHelper.addCart()
            {
                Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show();
                finish();
            }else{
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
            }*/
        });





    }


    private void setFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
}