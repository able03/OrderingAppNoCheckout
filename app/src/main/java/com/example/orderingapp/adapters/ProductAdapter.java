package com.example.orderingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderingapp.R;
import com.example.orderingapp.SelectListener;
import com.example.orderingapp.models.ProductModel;

import java.util.ConcurrentModificationException;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder>
{

    private Context context;
    private List<ProductModel> productList;
    private SelectListener listener;


    public ProductAdapter(Context context) {
        this.context = context;
    }

    public void setProductList(List<ProductModel> productList) {
        this.productList = productList;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(SelectListener listener)
    {
        this.listener = listener;
    }


    public void filteredList(List<ProductModel> productList) {
        this.productList = productList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.rv_product_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        int pos = position;
       holder.iv.setImageResource(productList.get(pos).getImgRID());
       holder.cv.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               listener.itemClicked(productList.get(pos));
           }
       });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {

        ImageView iv;
        CardView cv;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.ivProductRV);
            cv = itemView.findViewById(R.id.cvProductItemRV);
        }
    }

}
