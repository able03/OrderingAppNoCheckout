package com.example.orderingapp.adapters;

import android.app.DatePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderingapp.CartClickedListener;
import com.example.orderingapp.DBHelper;
import com.example.orderingapp.OnCheckedChangeListener;
import com.example.orderingapp.QuantityChangedListener;
import com.example.orderingapp.R;
import com.example.orderingapp.models.CartModel;
import com.google.android.material.button.MaterialButton;

import java.util.Calendar;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder>
{
    int qty;
    private Context context;
    private CheckedChangeListener onCheckedChangeListener;



    private List<CartModel> cartList;
    private CartClickedListener listener;
    private OnCheckedChangeListener changeListener;
    private DatePickerDialog dialog;
    private QuantityChangedListener quantityChangedListener;

    public CartAdapter(Context context)
    {
        this.context = context;
    }

    public void setCartList(List<CartModel> cartList)
    {
        this.cartList = cartList;
    }


    public void setListener(CartClickedListener listener) {
        this.listener = listener;
    }

    public void setChangeListener(OnCheckedChangeListener changeListener)
    {
        this.changeListener = changeListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_cart_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        DBHelper dbHelper = new DBHelper(context);
        int pos = position;
        int cart_id = cartList.get(pos).getCart_id();
        int product_id = cartList.get(pos).getProduct_id();
        String product_name = cartList.get(pos).getProduct_name();
        double price = cartList.get(pos).getPrice();

        int imgRID = cartList.get(pos).getImgRID();

        holder.tv_id.setText(String.valueOf(cart_id));
        holder.tv_pid.setText(String.valueOf(product_id));
        holder.tv_name.setText(product_name);
        holder.tv_price.setText(String.valueOf(price));
        holder.iv_img.setImageResource(imgRID);

        qty = cartList.get(pos).getQty();
        holder.tv_qty.setText(String.valueOf(qty));



            holder.ib_add.setOnClickListener(add -> {

                if(qty > 0)
                {
                    qty++;

                    cartList.get(pos).setQty(qty);
                }
                holder.tv_qty.setText(String.valueOf(qty));

            });

            holder.ib_minus.setOnClickListener(minus -> {

                if(qty > 1)
                {
                     qty--;
                    cartList.get(pos).setQty(qty);
                }
                holder.tv_qty.setText(String.valueOf(qty));

            });



            holder.iv_delete.setOnClickListener(delete -> {
                listener.itemClicked(cartList.get(pos));
                cartList.remove(cartList.get(pos));
                notifyDataSetChanged();


                dbHelper.deleteCart(cart_id);





            });



        /*holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cartList.get(pos).setChecked(isChecked);
                onCheckedChangeListener.onItemCheckedChanged(cartList.get(pos), isChecked);
            }
        });*/

          /* holder.btn_checkout.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Log.d("Debugging", "Checkout button clicked");
               }
           });*/


        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                cartList.get(pos).setChecked(isChecked);
                changeListener.checkChanged(cartList.get(pos));
            }
        });


        dbHelper.close();
    }

    @Override
    public int getItemCount()
    {
        return cartList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tv_id, tv_pid, tv_name, tv_price, tv_qty;
        private ImageView iv_img, iv_delete;
        private ImageButton ib_add, ib_minus;
        private MaterialButton btn_checkout;
        private CheckBox checkBox;


        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            tv_id = itemView.findViewById(R.id.tvCartId);
            tv_pid = itemView.findViewById(R.id.tvCartProductId);
            tv_name = itemView.findViewById(R.id.tvCartName);
            tv_price = itemView.findViewById(R.id.tvCartPrice);
            tv_qty = itemView.findViewById(R.id.tvQty);
            checkBox = itemView.findViewById(R.id.cb_CheckBox);


            iv_img = itemView.findViewById(R.id.ivCartImg);
            iv_delete = itemView.findViewById(R.id.ivCartDelete);



            ib_add = itemView.findViewById(R.id.ibAdd);
            ib_minus = itemView.findViewById(R.id.ibMinus);

            checkBox = itemView.findViewById(R.id.cb_CheckBox);
        }
    }


    private void showDatePickerDialog()
    {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {

            }
        }, year, month, day);

        dialog.getDatePicker().setMinDate(calendar.getTimeInMillis()+2000);
        Log.d("Debugging", "Time in millis: " + calendar.getTimeInMillis());
        dialog.show();
    }

    public void setOnCheckedChangeListener(CheckedChangeListener onCheckedChangeListener)
    {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    public interface CheckedChangeListener {
        void onItemCheckedChanged(CartModel cartModel, boolean isChecked);
    }






}
