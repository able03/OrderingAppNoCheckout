package com.example.orderingapp;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

public class CustomToast
{
    private ImageView iv_toast;
    private TextView tv_status, tv_message;
    private CardView cv_toast;
    public void myToast(Context context, int imgRID, String status, String message, int bgRID, int textRID)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_toast_layout, null);

        cv_toast = view.findViewById(R.id.cvToast);
        iv_toast = view.findViewById(R.id.ivToastIcon);
        tv_status = view.findViewById(R.id.tvToastStatus);
        tv_message = view.findViewById(R.id.tvToastMessage);

        cv_toast.setCardBackgroundColor(context.getResources().getColor(bgRID));
        iv_toast.setImageResource(imgRID);
        tv_status.setText(status);
        tv_status.setTextColor(context.getResources().getColor(textRID));
        tv_message.setText(message);
        tv_message.setTextColor(context.getResources().getColor(textRID));

        Toast toast = new Toast(context);
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

}
