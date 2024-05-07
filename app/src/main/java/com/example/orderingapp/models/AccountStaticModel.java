package com.example.orderingapp.models;

public class AccountStaticModel
{
    public static int id;
    public static String name;
    public static String address;
    public static String email;
    public static String birthdate;
    public static String phone;
    public static String username;
    public static String password;
    public static String pin;

    public AccountStaticModel(int id, String name, String address, String email, String birthdate, String phone, String username, String password, String pin)
    {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.birthdate = birthdate;
        this.phone = phone;
        this.username = username;
        this.password = password;
        this.pin = pin;
    }


    public static int getId()
    {
        return id;
    }

    public static String getName()
    {
        return name;
    }

    public static String getAddress()
    {
        return address;
    }

    public static String getEmail()
    {
        return email;
    }

    public static String getBirthdate()
    {
        return birthdate;
    }

    public static String getPhone()
    {
        return phone;
    }

    public static String getUsername()
    {
        return username;
    }

    public static String getPassword()
    {
        return password;
    }

    public static String getPin()
    {
        return pin;
    }
}
