package com.example.orderingapp.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orderingapp.DBHelper;
import com.example.orderingapp.MainActivity;
import com.example.orderingapp.R;
import com.example.orderingapp.models.AccountStaticModel;


public class ProfileFragment extends Fragment {


    private TextView tv_name, tv_address, tv_contact, tv_orders, tv_vouchers;
    private ImageView iv_logout;

    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initValues();


        DBHelper dbHelper = new DBHelper(getContext());

       if(AccountStaticModel.getId() > 0)
       {
           tv_name.setText(AccountStaticModel.getName());
           tv_address.setText(AccountStaticModel.getAddress());
           tv_contact.setText(AccountStaticModel.getPhone());
           iv_logout.setVisibility(View.VISIBLE);

           iv_logout.setOnClickListener(new View.OnClickListener()
           {
               @Override
               public void onClick(View v)
               {
                   new AccountStaticModel(0, null, null, null, null, null, null, null, null);
                   Intent intent = new Intent(getContext(), MainActivity.class);
                   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                   getContext().startActivity(intent);
               }
           });


           int vouchers = dbHelper.getVCount(AccountStaticModel.getId());
           int orders = dbHelper.getOrdersCount(AccountStaticModel.getId());

           tv_vouchers.setText(String.valueOf(vouchers));
           tv_orders.setText(String.valueOf(orders));

       }
       else
       {
           iv_logout.setVisibility(View.GONE);
           tv_name.setText("");
           tv_address.setText("");
           tv_contact.setText("");
       }


    }

    private void initValues()
    {
        view = getView();
        tv_name = view.findViewById(R.id.tvProfileName);
        tv_address = view.findViewById(R.id.tvProfileAddress);
        tv_contact = view.findViewById(R.id.tvProfileContactNo);
        tv_orders = view.findViewById(R.id.tvProfileOrders);
        tv_vouchers = view.findViewById(R.id.tvProfileVouchers);
        iv_logout = view.findViewById(R.id.ivProfileLogout);
    }
}