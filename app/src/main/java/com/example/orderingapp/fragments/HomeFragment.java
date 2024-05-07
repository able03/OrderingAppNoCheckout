package com.example.orderingapp.fragments;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orderingapp.CustomToast;
import com.example.orderingapp.DBHelper;
import com.example.orderingapp.FixedSpeedScroller;
import com.example.orderingapp.LoadItems;
import com.example.orderingapp.NotificationHelper;
import com.example.orderingapp.ProductClickedActivity;
import com.example.orderingapp.R;
import com.example.orderingapp.SelectListener;
import com.example.orderingapp.VoucherActivity;
import com.example.orderingapp.adapters.ProductAdapter;
import com.example.orderingapp.adapters.ViewPagerAdapter;
import com.example.orderingapp.models.AccountStaticModel;
import com.example.orderingapp.models.ProductModel;
import com.example.orderingapp.models.ProductStaticModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;


public class HomeFragment extends Fragment {

    ImageButton ib_profile;

    RecyclerView rv;
    ProductAdapter adapter;
    LoadItems itemList;



    ImageView iv_product;
    TextView tv_details, tv_items_detail;

    CardView cv_electronics, cv_fashion, cv_food;
    SearchView sv;


    List<ProductModel> mainSearchList;
    List<ProductModel> subSearchList = new ArrayList<>();
    List<ProductModel> rvList;

    MaterialButton btn_buy;
    FrameLayout fl;




    TextView tv_use_pin_code, tv_register_here, tv_login_here, tv_marquee;
    MaterialButton btn_register, btn_login;


    androidx.appcompat.app.AlertDialog.Builder adb_login, adb_register, adb_pin;


    androidx.appcompat.app.AlertDialog ad_login, ad_register, ad_pin;

    TextInputLayout lo_name, lo_address, lo_email, lo_bdate, lo_contact, lo_username, lo_password,
            lo_username_login, lo_password_login;

    TextInputEditText et_name, et_address, et_email, et_bdate, et_contact, et_username, et_password,
            et_username_login, et_password_login;

    EditText et1, et2, et3, et4;

    CheckBox cb_user;
    CustomToast toast;
    DBHelper dbHelper;
    NotificationHelper notificationHelper;

    static final String PREFS_NAME = "VoucherPrefs";
    static final String DIALOG_SHOWN_KEY = "shown";


    ListView lv;
    ImageView iv, iv_pop;
    AlertDialog.Builder adb_voucher;
    AlertDialog ad_voucher;
    ViewPager pager;
    ViewPagerAdapter vpAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        initValues();


        populateRV();


        setListener();


//        showVoucherDialog();
       /* if(AccountStaticModel.getId() > 0)
        {
            showVoucherLimited();
        }*/

        if(AccountStaticModel.getId() > 0)
        {
            ib_profile.setEnabled(false);
        }
        else
        {
            ib_profile.setEnabled(true);
        }


        int count = dbHelper.getVoucherCount(AccountStaticModel.getId());
        Log.d("Debugging", "Voucher count: " + count);


        if(AccountStaticModel.getId() > 0 && (count == 0 || count == 1 || count == 2))
        {
            showVoucherPopup();
        }
        else
        {
            Log.d("Debugging", "no vouchers available");
        }

        dbHelper.close();
        setViewPager();
    }

//    private void showVoucherLimited()
//    {
//        SharedPreferences preferences = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
//        boolean dialogShown = preferences.getBoolean(DIALOG_SHOWN_KEY, false);
//
//        if(!dialogShown)
//        {
//           showVoucherDialog();
//
//            preferences.edit().putBoolean(DIALOG_SHOWN_KEY, true).apply();
//        }
//    }


    /*private void setViewPager()
    {
//        int[] images = {R.drawable.banner1, R.drawable.banner2, R.drawable.banner3};
//        int[] images = {R.drawable.bd_voucher, R.drawable.fs_voucher, R.drawable.bd_voucher, R.drawable.fs_voucher};
         int[] images = {R.drawable.banner_1, R.drawable.banner_2, R.drawable.banner_3};
        pager = getView().findViewById(R.id.vpImageSlider);
        vpAdapter = new ViewPagerAdapter(getContext(), images);
        pager.setAdapter(vpAdapter);



        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                pager.post(new Runnable(){

                    @Override
                    public void run() {
                        pager.setCurrentItem((pager.getCurrentItem()+1)%images.length);
                    }
                });
            }
        };
        Timer timer;
        timer = new Timer();
        timer.schedule(timerTask, 3000, 5000);

        try {
            AccelerateInterpolator sInterpolator = new AccelerateInterpolator();
            Field mScroller;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(pager.getContext(), (android.view.animation.Interpolator) sInterpolator);
            mScroller.set(pager, scroller);
        } catch (NoSuchFieldException e) {
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        }
    }*/



    private void setViewPager() {
        int[] imageResources = {R.drawable.banner_1, R.drawable.banner_2, R.drawable.banner_3};

        pager = getView().findViewById(R.id.vpImageSlider);
        vpAdapter = new ViewPagerAdapter(getContext(), imageResources);
        pager.setAdapter(vpAdapter);

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                pager.post(new Runnable(){
                    @Override
                    public void run() {
                        pager.setCurrentItem((pager.getCurrentItem()+1)%imageResources.length);
                    }
                });
            }
        };

        Timer timer = new Timer();
        timer.schedule(timerTask, 3000, 5000);

        try {
            AccelerateInterpolator sInterpolator = new AccelerateInterpolator();
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(pager.getContext(), sInterpolator);
            mScroller.set(pager, scroller);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }




    private void initValues()
    {
        ib_profile = getView().findViewById(R.id.ibProfile);

        rv = getView().findViewById(R.id.rvItems);
        adapter = new ProductAdapter(getContext());
        itemList = new LoadItems(getContext());

        tv_details = getView().findViewById(R.id.tvProductDetails);
        iv_product = getView().findViewById(R.id.ivDetailsIcon);
        tv_marquee = getView().findViewById(R.id.tvMarqueeDelivery);
        tv_marquee.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        tv_marquee.setScrollX(5000);
        tv_marquee.setSelected(true);



        cv_electronics = getView().findViewById(R.id.cvElectronics);
        cv_fashion = getView().findViewById(R.id.cvFashion);
        cv_food = getView().findViewById(R.id.cvFood);

        sv = getView().findViewById(R.id.searchView);
        sv.clearFocus();

        mainSearchList = itemList.getAllList();
        tv_items_detail = getView().findViewById(R.id.tvItemsLabel);

        btn_buy = getView().findViewById(R.id.btnBuy);


        fl = getView().findViewById(R.id.flDetails);

        toast = new CustomToast();

        dbHelper = new DBHelper(getContext());

        notificationHelper = new NotificationHelper();

    }

    private void populateRV()
    {
        rvList = itemList.getAllList();
        adapter.setProductList(rvList);
        rv.setAdapter(adapter);
        tv_items_detail.setText("Items");
        rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    }


    private void setListener()
    {

        ib_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoginDialog();

            }
        });

        adapter.setOnItemClickListener(new SelectListener() {
            @Override
            public void itemClicked(ProductModel productModel) {
                int id = productModel.getProduct_id();
                int imgRID = productModel.getImgRID();
                String name = productModel.getName();
                String desc = productModel.getDescription();
                double price = productModel.getPrice();
                int ratings = productModel.getRatings_id();
                String category = productModel.getCategory();

                iv_product.setVisibility(View.VISIBLE);
                tv_details.setVisibility(View.VISIBLE);
                btn_buy.setVisibility(View.VISIBLE);
                fl.setVisibility(View.VISIBLE);

                iv_product.setImageResource(Integer.valueOf(imgRID));
                tv_details.setText(desc);

                int i = R.drawable.elec_sony;
                Log.d("Debugging", String.valueOf(i));

                new ProductStaticModel(id, name, desc, price, ratings, Integer.valueOf(imgRID), category);


            }
        });


        cv_electronics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filteredList("electronics");
                tv_items_detail.setText("Electronics");
            }
        });


        cv_fashion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filteredList("fashion");
                tv_items_detail.setText("Fashion");
            }
        });


        cv_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filteredList("food");
                tv_items_detail.setText("Food");
            }
        });



        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchFiltered(s);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(s.isEmpty())
                {
                    clearSubSearchList();
                }
                return true;
            }
        });

        btn_buy.setOnClickListener(click -> {
            Intent intent = new Intent(getContext(), ProductClickedActivity.class);
            startActivity(intent);
        });


        ib_profile.setOnClickListener(click -> {
            showLoginDialog();
        });

    }

    private void searchFiltered(String s)
    {
        for(ProductModel productModel : mainSearchList)
        {
            if(productModel.getName().toLowerCase().contains(s.toLowerCase()))
            {
                subSearchList.add(productModel);
            }
        }

       if(!subSearchList.isEmpty())
       {
           adapter.filteredList(subSearchList);
       }
    }

    private void clearSubSearchList()
    {
        subSearchList.clear();
        adapter.setProductList(subSearchList);
        populateRV();
    }



    private void filteredList(String category)
    {
        switch (category)
        {
            case "electronics": adapter.filteredList(itemList.getElectronicsList()); break;
            case "fashion": adapter.filteredList(itemList.getFashionList()); break;
            case "food": adapter.filteredList(itemList.getFoodList()); break;
            default: break;
        }
    }

    private void showRegisterDialog()
    {
        View registerView = LayoutInflater.from(getContext()).inflate(R.layout.register_dialog_layout, null);
        initRegister(registerView);

        adb_register = new androidx.appcompat.app.AlertDialog.Builder(getContext());
        adb_register.setView(registerView);

        ad_register = adb_register.create();
        ad_register.show();

        tv_login_here.setOnClickListener(login ->
        {
            ad_register.dismiss();
            showLoginDialog();
        });

        cb_user.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (isChecked)
                {
                    btn_register.setEnabled(true);
                } else
                {
                    btn_register.setEnabled(false);
                }
            }
        });

        lo_bdate.setEndIconOnClickListener(click -> {
            displayDatePicker();
        });

        btn_register.setOnClickListener(register ->
        {

            if(validateRegister())
            {
                registerProcess();
            }

        });

    }


    private void initRegister(View registerView)
    {
        tv_login_here = registerView.findViewById(R.id.tvJumpToLogin);

        lo_name = registerView.findViewById(R.id.loName);
        lo_address = registerView.findViewById(R.id.loAddress);
        lo_email = registerView.findViewById(R.id.loEmail);
        lo_bdate = registerView.findViewById(R.id.loBirthdate);
        lo_contact = registerView.findViewById(R.id.loContact);
        lo_username = registerView.findViewById(R.id.loUsernameRegis);
        lo_password = registerView.findViewById(R.id.loPasswordRegis);

        et_name = registerView.findViewById(R.id.etName);
        et_address = registerView.findViewById(R.id.etAddress);
        et_email = registerView.findViewById(R.id.etEmail);
        et_bdate = registerView.findViewById(R.id.etBirthdate);
        et_contact = registerView.findViewById(R.id.etContact);
        et_username = registerView.findViewById(R.id.etUsernameRegis);
        et_password = registerView.findViewById(R.id.etPasswordRegis);

        btn_register = registerView.findViewById(R.id.btnRegister);

        cb_user = registerView.findViewById(R.id.cbUserAgreement);
    }


    private void showLoginDialog()
    {
        View loginView = LayoutInflater.from(getContext()).inflate(R.layout.login_dialog_layout, null);
        initLogin(loginView);


        adb_login = new androidx.appcompat.app.AlertDialog.Builder(getContext());
        adb_login.setView(loginView);

        ad_login = adb_login.create();
        ad_login.show();


        tv_register_here.setOnClickListener(register ->
        {
            ad_login.dismiss();
            showRegisterDialog();
        });

        tv_use_pin_code.setOnClickListener(usePin ->
        {
            ad_login.dismiss();
            showPinDialog();
        });

        btn_login.setOnClickListener(login ->
        {
            if (loginIsNotEmpty())
            {
                String username = et_username_login.getText().toString().trim();
                String password = et_password_login.getText().toString().trim();
                loginProcess(username, password);
            }
        });
    }

    private void initLogin(View loginView)
    {
        btn_login = loginView.findViewById(R.id.btnLogin);

        tv_register_here = loginView.findViewById(R.id.tvJumpToRegister);
        tv_use_pin_code = loginView.findViewById(R.id.tvUsePinCode);

        lo_username_login = loginView.findViewById(R.id.loUsernameLogin);
        lo_password_login = loginView.findViewById(R.id.loPasswordLogin);

        et_username_login = loginView.findViewById(R.id.etUsernameLogin);
        et_password_login = loginView.findViewById(R.id.etPasswordLogin);
    }


    private void showPinDialog()
    {
        View pinView = LayoutInflater.from(getContext()).inflate(R.layout.pin_dialog_layout, null);
        initPin(pinView);

        adb_pin = new AlertDialog.Builder(getContext());
        adb_pin.setView(pinView)
                .setTitle("Enter your pin code")
                .setPositiveButton("submit", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        String str = et1.getText().toString() + et2.getText().toString() + et3.getText().toString() + et4.getText().toString();
                        pinProcess(str);
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        ad_pin.dismiss();
                        showLoginDialog();
                    }
                });

        ad_pin = adb_pin.create();
        ad_pin.show();


    }

    private void initPin(View pinView)
    {
        et1 = pinView.findViewById(R.id.et1);
        et2 = pinView.findViewById(R.id.et2);
        et3 = pinView.findViewById(R.id.et3);
        et4 = pinView.findViewById(R.id.et4);

        et1.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (s.length() == 1)
                {
                    et2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });


        et2.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (s.length() == 1)
                {
                    et3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });

        et3.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (s.length() == 1)
                {
                    et4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
    }

    private void showVoucherPopup()
    {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.voucher_popup_layout, null);

        iv = view.findViewById(R.id.ivVoucherClose);
        iv_pop = view.findViewById(R.id.ivVoucherPopup);


        iv_pop.setOnClickListener(pop -> {
            Intent intent = new Intent(getContext(), VoucherActivity.class);
            startActivity(intent);
        });

        iv.setOnClickListener(close -> {
            ad_voucher.dismiss();
        });

        adb_voucher = new AlertDialog.Builder(getContext());
        adb_voucher.setView(view);

        ad_voucher = adb_voucher.create();
        ad_voucher.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ad_voucher.setCancelable(false);
        ad_voucher.show();
    }


//    private void showVoucherDialog()
//    {
//        ArrayList<VoucherModel> voucherModelArrayList = new ArrayList<>();
//        voucherModelArrayList.add(new VoucherModel(R.drawable.bd_voucher));
//        voucherModelArrayList.add(new VoucherModel(R.drawable.fs_voucher));
//        View view = LayoutInflater.from(getContext()).inflate(R.layout.voucher_dialog_layout, null);
//
//        iv = view.findViewById(R.id.ivVoucherClose);
//
//        lv =  view.findViewById(R.id.lvVoucher);
//        VoucherAdapter adapter = new VoucherAdapter(getContext(), voucherModelArrayList);
//
//        lv.setAdapter(adapter);
//
//
//        iv.setOnClickListener(close -> {
//            ad_voucher.dismiss();
//        });
//        adb_voucher = new AlertDialog.Builder(getContext());
//        adb_voucher.setView(view);
//
//        ad_voucher = adb_voucher.create();
//        ad_voucher.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        ad_voucher.setCancelable(false);
//        ad_voucher.show();
//
//    }




    private void loginProcess(String username, String password)
    {
        Cursor cursor = dbHelper.findAccount(username, password);
        if(cursor.moveToFirst())
        {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String name = cursor.getString(1);
            String address = cursor.getString(2);
            String email = cursor.getString(3);
            String bdate = cursor.getString(4);
            String phone = cursor.getString(5);
            String user = cursor.getString(6);
            String pass = cursor.getString(7);
            String pin = cursor.getString(8);

            new AccountStaticModel(id, name, address, email, bdate, phone, user, pass, pin);
            toast.myToast(getContext(), R.drawable.ic_success, "Success",
                    "Login successfully", R.color.success_color_bg, R.color.success_color_text);
            ad_login.dismiss();
            ib_profile.setEnabled(false);

//            startActivity(new Intent(getContext(), MainActivity.class));
        }
        else
        {
            ib_profile.setEnabled(true);
            toast.myToast(getContext(), R.drawable.ic_failed, "Failed",
                    "Login failed", R.color.failed_color_bg, R.color.failed_color_text);
        }
    }

    private void registerProcess()
    {
        String name = et_name.getText().toString().trim();
        String address = et_address.getText().toString().trim();
        String email = et_email.getText().toString().trim();
        String bdate = et_bdate.getText().toString().trim();
        String phone = et_contact.getText().toString().trim();
        String user = et_username.getText().toString().trim();
        String pass = et_password.getText().toString().trim();

        Random random = new Random();
        String pinCode = String.format("%04d", random.nextInt(1001));
        if(dbHelper.addAccount(name, address, email, bdate, phone, user, pass, pinCode))
        {
            Cursor cursor = dbHelper.getAccount(name, user, pass);
            if(cursor.moveToFirst())
            {

                dbHelper.embedVouchers(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            }

            toast.myToast(getContext(), R.drawable.ic_success, "Success",
                    "Registered successfully", R.color.success_color_bg, R.color.success_color_text);
            notificationHelper.showNotification(getContext(), "Your pin code", pinCode);
            new Handler().postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    ad_register.dismiss();
                    showLoginDialog();
                }
            }, 2000);




        }
        else
        {
            toast.myToast(getContext(), R.drawable.ic_failed, "Failed",
                    "Registration failed", R.color.failed_color_bg, R.color.failed_color_text);
        }

    }

    private void pinProcess(String str)
    {
        Cursor cursor = dbHelper.findAccount(str);
        if(cursor.moveToFirst())
        {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String address = cursor.getString(2);
            String email = cursor.getString(3);
            String bdate = cursor.getString(4);
            String phone = cursor.getString(5);
            String user = cursor.getString(6);
            String pass = cursor.getString(7);
            String pin = cursor.getString(8);

            ib_profile.setEnabled(false);
            new AccountStaticModel(id, name, address, email, bdate, phone, user, pass, pin);
            toast.myToast(getContext(), R.drawable.ic_success, "Success",
                    "Login successfully", R.color.success_color_bg, R.color.success_color_text);

        }
        else
        {
            ib_profile.setEnabled(true);
            toast.myToast(getContext(), R.drawable.ic_failed, "Failed",
                    "Login failed", R.color.failed_color_bg, R.color.failed_color_text);
        }
    }








    private boolean loginIsNotEmpty()
    {
        boolean username = et_username_login.getText().toString().isEmpty();
        boolean password = et_password_login.getText().toString().isEmpty();

        if (username || password)
        {
            if (username)
            {
                lo_username_login.setErrorEnabled(true);
                lo_username_login.setError("Username can't be empty");

                et_username_login.addTextChangedListener(new TextWatcher()
                {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after)
                    {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count)
                    {
                        lo_username_login.setErrorEnabled(false);
                    }

                    @Override
                    public void afterTextChanged(Editable s)
                    {

                    }
                });
            }

            if (password)
            {
                lo_password_login.setErrorEnabled(true);
                lo_password_login.setError("Password can't be empty");

                et_password_login.addTextChangedListener(new TextWatcher()
                {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after)
                    {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count)
                    {
                        lo_password_login.setErrorEnabled(false);
                    }

                    @Override
                    public void afterTextChanged(Editable s)
                    {

                    }
                });
            }

            return false;
        }

        return true;
    }

    private boolean validateRegister()
    {
        boolean name = et_name.getText().toString().isEmpty();
        boolean address = et_address.getText().toString().isEmpty();
        boolean bdate = et_bdate.getText().toString().isEmpty();
        boolean contact = et_contact.getText().toString().isEmpty();
        boolean user = et_username.getText().toString().isEmpty();
        boolean pass = et_password.getText().toString().isEmpty();


        if(name || address || bdate || contact || user || pass || validateTwo() == false)
        {
            if(name)
            {
                lo_name.setErrorEnabled(true);
                lo_name.setError("Name can't be empty");

                et_name.addTextChangedListener(new TextWatcher()
                {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after)
                    {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count)
                    {
                        lo_name.setErrorEnabled(false);
                    }

                    @Override
                    public void afterTextChanged(Editable s)
                    {

                    }
                });
            }
            else
            {
                validateName();
            }


            if(address)
            {
                lo_address.setErrorEnabled(true);
                lo_address.setError("Address can't be empty");

                et_address.addTextChangedListener(new TextWatcher()
                {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after)
                    {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count)
                    {
                        lo_address.setErrorEnabled(false);
                    }

                    @Override
                    public void afterTextChanged(Editable s)
                    {

                    }
                });
            }
            else
            {
                validateAddress();
            }


            if(bdate)
            {
                lo_bdate.setErrorEnabled(true);
                lo_bdate.setError("Birthdate can't be empty");

                et_bdate.addTextChangedListener(new TextWatcher()
                {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after)
                    {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count)
                    {
                        lo_bdate.setErrorEnabled(false);

                    }

                    @Override
                    public void afterTextChanged(Editable s)
                    {

                    }
                });
            }


            if(contact)
            {
                lo_contact.setErrorEnabled(true);
                lo_contact.setError("Phone can't be empty");

                et_contact.addTextChangedListener(new TextWatcher()
                {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after)
                    {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count)
                    {
                        lo_contact.setErrorEnabled(false);
                    }

                    @Override
                    public void afterTextChanged(Editable s)
                    {

                    }
                });

            }
            else
            {
                validateContact();
            }


            if(user)
            {
                lo_username.setErrorEnabled(true);
                lo_username.setError("Username can't be empty");

                et_username.addTextChangedListener(new TextWatcher()
                {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after)
                    {

                    }

                    @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count)
                    {
                        lo_username.setErrorEnabled(false);
                    }

                    @Override
                    public void afterTextChanged(Editable s)
                    {

                    }
                });
            }
            else
            {
                validateUsername();
            }


            if(pass)
            {
                lo_password.setErrorEnabled(true);
                lo_password.setError("Password can't be empty");

                et_password.addTextChangedListener(new TextWatcher()
                {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after)
                    {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count)
                    {
                        lo_password.setErrorEnabled(false);
                    }

                    @Override
                    public void afterTextChanged(Editable s)
                    {

                    }
                });
            }
            else
            {
                validatePassword();
            }


            return false;
        }

        return true;
    }

    private boolean validateTwo()
    {
        if(validateName() && validateAddress() && validateContact() && validateUsername() && validatePassword())
        {
            return true;
        }
        return false;
    }

    private boolean validateName()
    {
        Set<Character> sChars = new HashSet<>();
        Collections.addAll(sChars, '@', '!', ',', '/', '#', '%', '<', '>', '-', '_', ';', '.',
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9');


        String name = et_name.getText().toString().trim();

        for(int i=0; i<name.length(); i++)
        {
            if(sChars.contains(name.charAt(i)))
            {
                lo_name.setErrorEnabled(true);
                lo_name.setError("Must not contain special character or number");

                et_name.addTextChangedListener(new TextWatcher()
                {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after)
                    {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count)
                    {
                        lo_name.setErrorEnabled(false);
                    }

                    @Override
                    public void afterTextChanged(Editable s)
                    {

                    }
                });
                return false;
            }
        }

        return true;
    }


    private boolean validateAddress()
    {
        String address = et_address.getText().toString().trim();

        if(address.length() <= 8)
        {
            lo_address.setErrorEnabled(true);
            lo_address.setError("Address must be more than 8 characters long");

            et_address.addTextChangedListener(new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after)
                {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {
                    lo_address.setErrorEnabled(false);
                }

                @Override
                public void afterTextChanged(Editable s)
                {

                }
            });

            return false;
        }

        return true;
    }

    private boolean validateContact()
    {
        String contact = et_contact.getText().toString().toString();

        if(contact.length() < 11)
        {
            lo_contact.setErrorEnabled(true);
            lo_contact.setError("Phone must be 11 digits long");

            et_contact.addTextChangedListener(new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after)
                {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {
                    lo_contact.setErrorEnabled(false);
                }

                @Override
                public void afterTextChanged(Editable s)
                {

                }
            });

            return false;
        }
        else if(!contact.substring(0,2).equals("09"))
        {
            lo_contact.setErrorEnabled(true);
            lo_contact.setError("Phone must start with 09");


            et_contact.addTextChangedListener(new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after)
                {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {
                    lo_contact.setErrorEnabled(false);
                }

                @Override
                public void afterTextChanged(Editable s)
                {

                }
            });
        }



        return true;
    }


    private boolean validateUsername()
    {
        String username = et_username.getText().toString().trim();

        Pattern upper = Pattern.compile("[A-Z]");
        if(username.length() != 5)
        {
            lo_username.setError("Username must be 5 characters long");

            et_username.addTextChangedListener(new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after)
                {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {
                    lo_username.setErrorEnabled(false);
                }

                @Override
                public void afterTextChanged(Editable s)
                {

                }
            });
            return false;
        }
        else if(!upper.matcher(username).find())
        {
            lo_username.setError("Username must contain at least 1 uppercase");

            et_username.addTextChangedListener(new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after)
                {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {
                    lo_username.setErrorEnabled(false);

                }

                @Override
                public void afterTextChanged(Editable s)
                {

                }
            });
            return false;
        }


        return true;
    }


    private boolean validatePassword()
    {
        String password = et_password.getText().toString().trim();

        Pattern upper = Pattern.compile("[A-Z]");
        Pattern digit = Pattern.compile("[0-9]");
        Pattern sChar = Pattern.compile("[@!,?]");

        if(password.length() < 5)
        {
            lo_password.setError("Password must be at least 5 or more characters");

            et_password.addTextChangedListener(new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after)
                {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {
                    lo_password.setErrorEnabled(false);
                }

                @Override
                public void afterTextChanged(Editable s)
                {

                }
            });
            return false;
        }
        else if(!upper.matcher(password).find())
        {
            lo_password.setError("Password must contain at least 1 uppercase");

            et_password.addTextChangedListener(new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after)
                {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {
                    lo_password.setErrorEnabled(false);
                }

                @Override
                public void afterTextChanged(Editable s)
                {

                }
            });
            return false;
        }
        else if(!digit.matcher(password).find())
        {
            lo_password.setError("Password must contain at least 1 digit");

            et_password.addTextChangedListener(new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after)
                {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {
                    lo_password.setErrorEnabled(false);
                }

                @Override
                public void afterTextChanged(Editable s)
                {

                }
            });
            return false;

        }
        else if(!sChar.matcher(password).find())
        {
            lo_password.setError("Password must contain at least 1 special character");

            et_password.addTextChangedListener(new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after)
                {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {
                    lo_password.setErrorEnabled(false);
                }

                @Override
                public void afterTextChanged(Editable s)
                {

                }
            });
            return false;
        }


        return true;
    }


    private void displayDatePicker()
    {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
            {
                et_bdate.setText(String.format("%04d", year) + "/" + String.format("%02d", (month+1)) + "/" + String.format("%02d", dayOfMonth));
            }
        }, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
        datePickerDialog.show();
    }





}