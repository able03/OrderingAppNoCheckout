package com.example.orderingapp;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.orderingapp.adapters.VoucherAdapter;
import com.example.orderingapp.models.AccountStaticModel;
import com.example.orderingapp.models.VoucherModel;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class VoucherActivity extends AppCompatActivity {

    ListView lv_voucher;
    ArrayList<VoucherModel> voucherModelList;
    VoucherAdapter adapter;
    ImageView iv_back;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_voucher);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initValues();
        setData();
        setListener();

        dbHelper.close();
    }

    private void initValues()
    {
        lv_voucher = findViewById(R.id.lvVoucher);
        iv_back = findViewById(R.id.ivVoucherBack);
        dbHelper = new DBHelper(this);
    }

    private void setListener()
    {
        iv_back.setOnClickListener(back -> {
            finish();
        });
    }


    /*private void setData()
    {
        voucherModelList = new ArrayList<>();

        int id =  AccountStaticModel.getId();

        Cursor cursor = dbHelper.getVoucher("bday", id);
        Cursor cursor1 = dbHelper.getVoucher("fshipping", id);
        Cursor cursor2 = dbHelper.getVoucher("discount", id);



        adapter = new VoucherAdapter(this);

        *//*adapter.setListener(new VoucherListener() {
            @Override
            public void clickedItem(VoucherModel voucherModel) {
                if(dbHelper.addVoucher(voucherModel.getCode(), voucherModel.getImgRID(), voucherModel.getPercent(), voucherModel.getAccount_id()))
                {
                    Cursor cursor = dbHelper.getVoucher(id);
                    if(cursor.moveToFirst())
                    {
                        Log.d("Debugging", cursor.getString(1));
                    }
                    Toast.makeText(VoucherActivity.this, "Claimed successfully", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(VoucherActivity.this, "Claim failed", Toast.LENGTH_SHORT).show();
                }
            }
        });*//*

        Toast.makeText(this, ""+id, Toast.LENGTH_SHORT).show();

        if(!cursor.moveToFirst())
        {
            voucherModelList.add(new VoucherModel(R.drawable.bd_voucher, 1, 0.15, "bday", id));
            adapter.setVoucherList(voucherModelList);
        }

        if(!cursor1.moveToFirst())
        {
            voucherModelList.add(new VoucherModel(R.drawable.fs_voucher, 2, 0.2, "fshipping", id));
            adapter.setVoucherList(voucherModelList);
        }

        if(!cursor2.moveToFirst())
        {
            voucherModelList.add(new VoucherModel(R.drawable.voucher_2, 3, 0.10, "discount", id));
            adapter.setVoucherList(voucherModelList);
        }

        lv_voucher.setAdapter(adapter);

    }*/

    private void setData() {
        voucherModelList = new ArrayList<>();




        Cursor cursor = dbHelper.getEmbeddedVouchers(AccountStaticModel.getId());
        while (cursor.moveToNext())
        {

            int voucher_id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            int img = cursor.getInt(cursor.getColumnIndexOrThrow("imgRID"));
            double percent = cursor.getDouble(cursor.getColumnIndexOrThrow("percent"));
            String code = cursor.getString(cursor.getColumnIndexOrThrow("code"));
            int acc_id = cursor.getInt(cursor.getColumnIndexOrThrow("account_id"));

            Log.d("Debugging", "code: " + code);

            voucherModelList.add(new VoucherModel(img, voucher_id, percent, code, acc_id));
        }



     /*   if (!cursor.moveToFirst()) {
            voucherModelList.add(new VoucherModel(R.drawable.bd_voucher, 1, 0.15, "bday", id));
        }
        else
        {
            voucherModelList.removeIf(voucherModel -> voucherModel.getCode().equals("bday"));
        }

        if (!cursor1.moveToFirst()) {
            voucherModelList.add(new VoucherModel(R.drawable.fs_voucher, 2, 0.2, "fshipping", id));
        }
        else
        {
            voucherModelList.removeIf(voucherModel -> voucherModel.getCode().equals("fshipping"));
        }

        if (!cursor2.moveToFirst()) {
            voucherModelList.add(new VoucherModel(R.drawable.voucher_2, 3, 0.10, "discount", id));
        }
        else
        {
            voucherModelList.removeIf(voucherModel -> voucherModel.getCode().equals("discount"));
        }
*/



        adapter = new VoucherAdapter(this);
        adapter.setVoucherList(voucherModelList);
        lv_voucher.setAdapter(adapter);
    }

   /* private void setData() {
        voucherModelList = new ArrayList<>();

        int id = AccountStaticModel.getId();

        Cursor cursor = dbHelper.getVoucher("bday", id);
        Cursor cursor1 = dbHelper.getVoucher("fshipping", id);
        Cursor cursor2 = dbHelper.getVoucher("discount", id);

        // If vouchers exist, remove them from the list
        if (cursor.moveToFirst()) {
            voucherModelList.removeIf(voucherModel -> voucherModel.getCode().equals("bday"));
        }
        if (cursor1.moveToFirst()) {
            voucherModelList.removeIf(voucherModel -> voucherModel.getCode().equals("fshipping"));
        }
        if (cursor2.moveToFirst()) {
            voucherModelList.removeIf(voucherModel -> voucherModel.getCode().equals("discount"));
        }

        // Add vouchers to the list if they are not already claimed
        if (!cursor.moveToFirst()) {
            voucherModelList.add(new VoucherModel(R.drawable.bd_voucher, 1, 0.15, "bday", id));
        }
        if (!cursor1.moveToFirst()) {
            voucherModelList.add(new VoucherModel(R.drawable.fs_voucher, 2, 0.2, "fshipping", id));
        }
        if (!cursor2.moveToFirst()) {
            voucherModelList.add(new VoucherModel(R.drawable.voucher_2, 3, 0.10, "discount", id));
        }

        adapter = new VoucherAdapter(this);
        adapter.setVoucherList(voucherModelList);

        // Set the listener for the adapter
        adapter.setListener(new VoucherListener() {
            @Override
            public void clickedItem(VoucherModel voucherModel) {
                if(dbHelper.addVoucher(voucherModel.getCode(), voucherModel.getImgRID(), voucherModel.getPercent(), voucherModel.getAccount_id())) {
                    Cursor cursor = dbHelper.getVoucher(id);
                    if(cursor.moveToFirst()) {
                        Log.d("Debugging", cursor.getString(1));
                    }
                    Toast.makeText(VoucherActivity.this, "Claimed successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(VoucherActivity.this, "Claim failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        lv_voucher.setAdapter(adapter);
    }*/



}