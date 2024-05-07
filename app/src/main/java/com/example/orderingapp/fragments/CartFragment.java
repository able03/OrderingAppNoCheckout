package com.example.orderingapp.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orderingapp.CartClickedListener;
import com.example.orderingapp.DBHelper;
import com.example.orderingapp.OnCheckedChangeListener;
import com.example.orderingapp.R;
import com.example.orderingapp.VoucherCloseListener;
import com.example.orderingapp.VoucherListener;
import com.example.orderingapp.adapters.CartAdapter;
import com.example.orderingapp.adapters.ClaimedVoucherAdapter;
import com.example.orderingapp.models.AccountStaticModel;
import com.example.orderingapp.models.CartModel;
import com.example.orderingapp.models.VoucherModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Stack;


public class CartFragment extends Fragment implements ClaimedVoucherAdapter.VoucherListener
{



    CartAdapter adapter;
    RecyclerView rv;
    List<CartModel> cartModelList;
    DBHelper dbHelper;
    ImageView iv_empty, iv_close;
    TextView tv_empty, tv_total_price, tv_total_items, tv_checkout_price;
    MaterialButton btn_checkout, btn_checkout_alert;
    List<CartModel> cartCheckoutList;
    private DatePickerDialog dialog;

    Stack<CartModel> cartModelStack;

    AlertDialog.Builder builder_checkout;
    AlertDialog alert_checkout;

    TextInputLayout lo_delivery_date;
    TextInputEditText et_delivery_date;
    EditText et_use_voucher;
    ArrayList<VoucherModel> voucherModelList;
    AlertDialog.Builder builder_voucher;
    AlertDialog alert_voucher;
    ListView lv_voucher;
    ClaimedVoucherAdapter voucherAdapter;
    Bitmap bitmap;


    double totalPrice = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragments_cart_temp, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initValues();

//        Toast.makeText(getContext(), "User ID: " + AccountStaticModel.getId(), Toast.LENGTH_SHORT).show();


        Cursor cursorV = dbHelper.getVoucher(AccountStaticModel.getId());
        while (cursorV.moveToNext())
        {
            int id = cursorV.getInt(0);
            String code = cursorV.getString(1);
            int imgRID = cursorV.getInt(2);
            double percent = cursorV.getDouble(3);
            int acc_id = cursorV.getInt(4);

            voucherModelList.add(new VoucherModel(imgRID, id, percent, code, acc_id));
        }

        int id = AccountStaticModel.getId();
        Cursor cursor = dbHelper.getCart(id);

        while (cursor.moveToNext()) {
            int cart_id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            int customer_id = cursor.getInt(cursor.getColumnIndexOrThrow("account_id"));
            int quantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"));
            int product_id = cursor.getInt(cursor.getColumnIndexOrThrow("product_id"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
            int imgRID = cursor.getInt(cursor.getColumnIndexOrThrow("imgRID"));

           /* Toast.makeText(getContext(), "Cart ID: " + cart_id +
                    " Customer ID: " + customer_id +
                    "Product ID: " + product_id + "Name: " + name + " Price: " + price + " ImageRID: " + imgRID, Toast.LENGTH_SHORT).show();*/

            cartModelList.add(new CartModel(cart_id, customer_id, product_id, name, price, quantity, imgRID));
        }


        adapter.setCartList(cartModelList);

        rv.setAdapter(adapter);

        if (cartModelList.isEmpty())
        {
            iv_empty.setVisibility(View.VISIBLE);
            tv_empty.setVisibility(View.VISIBLE);
        }
        else
        {
            iv_empty.setVisibility(View.INVISIBLE);
            tv_empty.setVisibility(View.INVISIBLE);
        }

        adapter.setListener(new CartClickedListener() {
            @Override
            public void itemClicked(CartModel cartModel) {
               if(cartModelList.isEmpty())
               {
                   iv_empty.setVisibility(View.VISIBLE);
                   tv_empty.setVisibility(View.VISIBLE);
               }
               if(!cartModelList.isEmpty())
               {
                   iv_empty.setVisibility(View.INVISIBLE);
                   tv_empty.setVisibility(View.INVISIBLE);
               }
            }
        });

        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        if(AccountStaticModel.getId() > 0)
        {
            btn_checkout.setEnabled(true);
        }
        else
        {
            btn_checkout.setEnabled(false);
        }
        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCheckoutDialog();
            }
        });



//        cartModelStack = new Stack<>();
       /* adapter.setChangeListener(new OnCheckedChangeListener()
        {
            @Override
            public void checkChanged(CartModel cartModel)
            {


                if(cartModel.isChecked())
                {
                    cartCheckoutList.add(cartModel);
                    for(CartModel model : cartCheckoutList)
                    {
                        Log.d("Debugging", model.getCart_id() + ":" + model.getProduct_name());


                        double tempPrice = model.getPrice() * model.getQty();
                        totalPrice += tempPrice;

                        tv_total_price.setText(String.valueOf(totalPrice));
                    }
                }
                else if(cartModel.isChecked() == false)
                {
                    totalPrice -= cartModel.getPrice();
                    if(totalPrice< 0)
                    {
                        tv_total_price.setText(String.valueOf(0));
                    }
                    cartCheckoutList.remove(cartModel);
                }

                tv_total_items.setText(String.valueOf(cartCheckoutList.size()));






            }
        });*/



        adapter.setChangeListener(new OnCheckedChangeListener() {
            @Override
            public void checkChanged(CartModel cartModel) {
                if (cartModel.isChecked()) {
                    cartCheckoutList.add(cartModel);
                    double tempPrice = cartModel.getPrice() * cartModel.getQty();
                    totalPrice += tempPrice;
                } else {
                    totalPrice -= cartModel.getPrice() * cartModel.getQty();
                    if (totalPrice < 0) {
                        totalPrice = 0;
                    }
                    cartCheckoutList.remove(cartModel);
                }

                tv_total_price.setText(String.valueOf(totalPrice));

                tv_total_items.setText(String.valueOf(cartCheckoutList.size()));
            }
        });






//        if (isChecked) {
//            totalPrice += cartModel.getPrice() * cartModel.getQty();
//            cartCheckoutList.add(cartModel);
//        } else {
//            totalPrice -= cartModel.getPrice() * cartModel.getQty();
//            cartCheckoutList.remove(cartModel);
//        }
//
//        tv_total_price.setText(String.valueOf(totalPrice));
//        tv_total_items.setText(String.valueOf(cartCheckoutList.size()));


        /*// bitmap to bytes array
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bytes = stream.toByteArray();*/

       /* // blob to bytes array to bitmap
        byte[] bb = cursor.getBlob(2);
        Bitmap bitmap1 = BitmapFactory.decodeByteArray(bb, 0, bb.length);*/




        dbHelper.close();
    }

    private void initValues()
    {
        cartModelList = new ArrayList<>();
        cartCheckoutList = new ArrayList<>();
        voucherModelList = new ArrayList<>();
        dbHelper = new DBHelper(getContext());
        adapter = new CartAdapter(getContext());
        rv = getView().findViewById(R.id.rvCart);

        iv_empty = getView().findViewById(R.id.ivCartEmpty);
        tv_empty = getView().findViewById(R.id.tvCartEmpty);

        tv_total_items = getView().findViewById(R.id.tvTotalItems);
        tv_total_price = getView().findViewById(R.id.tvTotalPrice);

        btn_checkout = getView().findViewById(R.id.btnCheckout);
    }

    private void showDatePickerDialog()
    {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                et_delivery_date.setText(String.format("%04d", year) + "/" + String.format("%02d", month+1) + "/" + String.format("%02d", dayOfMonth));
            }
        }, year, month, day);

        dialog.getDatePicker().setMinDate(calendar.getTimeInMillis()+2000);
        Log.d("Debugging", "Time in millis: " + calendar.getTimeInMillis());
        dialog.show();
    }

    private void showCheckoutDialog()
    {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.checkout_dialog_layout, null);


        lo_delivery_date = view.findViewById(R.id.loDeliveryDate);
        et_delivery_date = view.findViewById(R.id.etDeliveryDate);
        tv_checkout_price = view.findViewById(R.id.tvCheckoutPrice);
        et_use_voucher = view.findViewById(R.id.etVoucher);
        btn_checkout_alert = view.findViewById(R.id.btnDialogCheckout);


        btn_checkout_alert.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(et_delivery_date.length() > 0)
                {
                  /*  // bitmap to bytes array
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] bytes = stream.toByteArray();
                    dbHelper.addOrder()*/
                }
                else
                {
                    lo_delivery_date.setError("Delivery date can't be empty");

                    et_delivery_date.addTextChangedListener(new TextWatcher()
                    {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after)
                        {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count)
                        {
                            lo_delivery_date.setErrorEnabled(false);
                        }

                        @Override
                        public void afterTextChanged(Editable s)
                        {

                        }
                    });
                }
            }
        });

        et_delivery_date.setFocusable(false);
        et_delivery_date.setFocusableInTouchMode(true);

        et_use_voucher.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                showVoucherDialog();
            }
        });

        tv_checkout_price.setText(String.valueOf(totalPrice));
        lo_delivery_date.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
        builder_checkout = new AlertDialog.Builder(getContext());
        builder_checkout.setView(view);
        alert_checkout = builder_checkout.create();

        alert_checkout.show();
    }

    private void showVoucherDialog()
    {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.voucher_dialog_layout, null);


        lv_voucher = view.findViewById(R.id.lvVoucher);
        iv_close = view.findViewById(R.id.ivVoucherClose);


        voucherAdapter = new ClaimedVoucherAdapter(getContext());

       iv_close.setOnClickListener(close -> {
           alert_voucher.dismiss();
       });

        voucherAdapter.setVoucherList(voucherModelList);
        voucherAdapter.setListener(this);
        lv_voucher.setAdapter(voucherAdapter);



        builder_voucher = new AlertDialog.Builder(getContext());
        builder_voucher.setView(view);

        alert_voucher = builder_voucher.create();
        alert_voucher.show();




    }


    @Override
    public void onVoucherClicked(VoucherModel voucher)
    {
        alert_voucher.dismiss();
        double voucherPercent = voucher.getPercent();
        et_use_voucher.setText(String.valueOf(voucherPercent));
        voucherPercent *= totalPrice;
        double discountedPrice = totalPrice-voucherPercent;
        tv_checkout_price.setText(String.valueOf(discountedPrice));
    }


    public Bitmap encodeAsBitmap(String str)
    {
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix bitMatrix = null;
        try
        {
            bitMatrix = writer.encode(str, BarcodeFormat.QR_CODE, 400, 400);
        } catch (WriterException e)
        {
            throw new RuntimeException(e);
        }

        int w = bitMatrix.getWidth();
        int h = bitMatrix.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                pixels[y * w + x] = bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, w, 0, 0, w, h);
        return bitmap;
    }


}