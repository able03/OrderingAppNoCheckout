package com.example.orderingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;

import com.example.orderingapp.R;
import com.example.orderingapp.VoucherCloseListener;
import com.example.orderingapp.VoucherListener;
import com.example.orderingapp.models.AccountStaticModel;
import com.example.orderingapp.models.VoucherModel;

import java.util.ArrayList;

public class ClaimedVoucherAdapter extends BaseAdapter
{
    ArrayList<VoucherModel> voucherList;
    Context context;
//    VoucherListener listener;
    private VoucherListener listener;

    public void setListener(VoucherListener listener) {
        this.listener = listener;
    }

    public ClaimedVoucherAdapter(Context context) {
        this.context = context;

    }

    public void setVoucherList(ArrayList<VoucherModel> voucherList) {
        this.voucherList = voucherList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount()
    {
        return voucherList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return voucherList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

//    public void setListener(VoucherListener listener) {
//        this.listener = listener;
//    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if(convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.voucher_layout, parent, false);
        }



        ImageView iv = convertView.findViewById(R.id.ivVoucherImage);

        iv.setImageResource(voucherList.get(position).getImgRID());

        Button btn_claim = convertView.findViewById(R.id.btnClaimVoucher);
        btn_claim.setText("Use voucher");

        btn_claim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onVoucherClicked(voucherList.get(position));
                }
            }
        });

        if(AccountStaticModel.getId() > 0)
        {
            btn_claim.setEnabled(true);
        }
        else
        {
            btn_claim.setEnabled(false);
        }


        return convertView;

    }


    public interface VoucherListener {
        void onVoucherClicked(VoucherModel voucher);
    }


}
