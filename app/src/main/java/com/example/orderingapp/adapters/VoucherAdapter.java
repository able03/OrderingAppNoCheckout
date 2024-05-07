package com.example.orderingapp.adapters;

import android.accounts.Account;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.orderingapp.DBHelper;
import com.example.orderingapp.R;
import com.example.orderingapp.SelectListener;
import com.example.orderingapp.VoucherListener;
import com.example.orderingapp.models.AccountStaticModel;
import com.example.orderingapp.models.VoucherModel;

import java.util.ArrayList;

public class VoucherAdapter extends BaseAdapter {
    ArrayList<VoucherModel> voucherList;
    Context context;
    VoucherListener listener;

    public VoucherAdapter(Context context) {
        this.context = context;

    }

    public void setVoucherList(ArrayList<VoucherModel> voucherList) {
        this.voucherList = voucherList;
        notifyDataSetChanged();
    }

    public void setListener(VoucherListener listener) {
        this.listener = listener;
    }


    @Override
    public int getCount() {
        return voucherList.size();
    }

    @Override
    public Object getItem(int i) {
        return voucherList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null)
        {
            view = LayoutInflater.from(context).inflate(R.layout.voucher_layout, viewGroup, false);
        }

        DBHelper dbHelper = new DBHelper(context);


        Cursor cursor = dbHelper.getVoucher(voucherList.get(i).getCode(), voucherList.get(i).getAccount_id());

        /*if(voucherList != null && cursor.moveToFirst())
        {
            voucherList.remove(i);
            notifyDataSetChanged();
        }*/

        ImageView iv = view.findViewById(R.id.ivVoucherImage);
        iv.setImageResource(voucherList.get(i).getImgRID());

        Button btn_claim = view.findViewById(R.id.btnClaimVoucher);

        if(AccountStaticModel.getId() > 0)
        {
            btn_claim.setEnabled(true);
        }
        else
        {
            btn_claim.setEnabled(false);
        }


       /* btn_claim.setOnClickListener(claim -> {
            Cursor c = dbHelper.getVoucher(voucherList.get(i).getCode(), voucherList.get(i).getAccount_id());
            if(!c.moveToFirst())
            {
                if(dbHelper.addVoucher(voucherList.get(i).getCode(), voucherList.get(i).getImgRID(), voucherList.get(i).getPercent(), voucherList.get(i).getAccount_id()))
                {
                    Cursor c1 = dbHelper.getVoucher(AccountStaticModel.getId());
                    if(c1.moveToFirst())
                    {
                        Log.d("Debugging", cursor.getString(1));
                    }
                    Toast.makeText(context, "Claimed successfully", Toast.LENGTH_SHORT).show();
                    voucherList.remove(i);
                    notifyDataSetChanged();

                }
                else
                {
                    Toast.makeText(context, "Claim failed", Toast.LENGTH_SHORT).show();
                }
            }

        });*/


        btn_claim.setOnClickListener(claim -> {
            if(dbHelper.addVoucher(voucherList.get(i).getCode(), voucherList.get(i).getImgRID(), voucherList.get(i).getPercent(), voucherList.get(i).getAccount_id()))
            {
                Cursor c1 = dbHelper.getVoucher(AccountStaticModel.getId());
                if(c1.moveToFirst())
                {
                    Log.d("Debugging", c1.getString(1));
                }
                Toast.makeText(context, "Claimed successfully", Toast.LENGTH_SHORT).show();
                dbHelper.deleteVouchers(voucherList.get(i).getCode());
                voucherList.remove(i);
                notifyDataSetChanged();

            }
            else
            {
                Toast.makeText(context, "Claim failed", Toast.LENGTH_SHORT).show();
            }
        });

        return view;


    }

   /* @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.voucher_layout, viewGroup, false);
        }

        DBHelper dbHelper = new DBHelper(context);

        // Check if the voucher exists in the database
        Cursor cursor = dbHelper.getVoucher(voucherList.get(i).getCode(), voucherList.get(i).getAccount_id());

        if (voucherList != null && cursor.moveToFirst()) {
            // If the voucher exists in the database, remove it from the list view
            voucherList.remove(i);
            notifyDataSetChanged();
        }

        // Display the voucher image
        ImageView iv = view.findViewById(R.id.ivVoucherImage);
        if (!voucherList.isEmpty()) {
            iv.setImageResource(voucherList.get(i).getImgRID());
        }

        // Enable/disable claim button based on account status
        Button btn_claim = view.findViewById(R.id.btnClaimVoucher);
        if (AccountStaticModel.getId() == voucherList.get(i).getAccount_id()) {
            btn_claim.setEnabled(true);
        } else {
            btn_claim.setEnabled(false);
        }

        // Handle claim button click
        btn_claim.setOnClickListener(claim -> {
            if (voucherList != null) {
                if (dbHelper.addVoucher(voucherList.get(i).getCode(), voucherList.get(i).getImgRID(), voucherList.get(i).getPercent(), voucherList.get(i).getAccount_id())) {
                    // If voucher is successfully claimed, remove it from the list view
                    Cursor c = dbHelper.getVoucher(AccountStaticModel.getId());
                    if (c.moveToFirst()) {
                        Log.d("Debugging", cursor.getString(1));
                    }
                    Toast.makeText(context, "Claimed successfully", Toast.LENGTH_SHORT).show();
                    voucherList.remove(i);
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(context, "Claim failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dbHelper.close();
        return view;
    }*/

}
