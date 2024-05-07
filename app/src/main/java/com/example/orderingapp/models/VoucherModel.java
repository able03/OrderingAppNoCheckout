package com.example.orderingapp.models;

public class VoucherModel
{
    private int imgRID;
    private int id;
    private double percent;
    private String code;
    private int account_id;

    public VoucherModel(int imgRID, int id, double percent, String code, int account_id) {
        this.imgRID = imgRID;
        this.id = id;
        this.percent = percent;
        this.code = code;
        this.account_id = account_id;
    }

    public int getImgRID() {
        return imgRID;
    }

    public int getId() {
        return id;
    }

    public double getPercent() {
        return percent;
    }

    public String getCode() {
        return code;
    }

    public int getAccount_id() {
        return account_id;
    }
}
