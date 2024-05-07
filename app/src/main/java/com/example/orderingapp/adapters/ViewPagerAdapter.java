package com.example.orderingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.orderingapp.R;

import java.util.Objects;

public class ViewPagerAdapter extends PagerAdapter
{
    int images[];
    Context context;
    LayoutInflater inflater;

    public ViewPagerAdapter(Context context, int[] images)
    {
        this.images = images;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount()
    {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object)
    {
        return view == ((FrameLayout) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.image_slider_layout, null);

        ImageView iv = view.findViewById(R.id.ivImageSlider);

        iv.setImageResource(images[position]);

        Objects.requireNonNull(container).addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object)
    {
        container.removeView((FrameLayout) object);
    }
}
