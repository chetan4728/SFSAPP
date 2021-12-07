package com.ingenioussys.microfinance.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


public class Center {


    int center_id;

    int bank_id;


    int center_no;

    public int getCenter_no() {
        return center_no;
    }

    public void setCenter_no(int center_no) {
        this.center_no = center_no;
    }

    public int getBank_id() {
        return bank_id;
    }

    public void setBank_id(int bank_id) {
        this.bank_id = bank_id;
    }


    String center_name;


    String area_name;

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }


    int area_id;


    String center_desc;


    Double latitude;


    Double longitude;


    int center_status;


    String fs_name;

    public String getFs_name() {
        return fs_name;
    }

    public void setFs_name(String fs_name) {
        this.fs_name = fs_name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public int getCenter_id() {
        return center_id;
    }

    public void setCenter_id(int center_id) {
        this.center_id = center_id;
    }

    public String getCenter_name() {
        return center_name;
    }

    public void setCenter_name(String center_name) {
        this.center_name = center_name;
    }

    public int getArea_id() {
        return area_id;
    }

    public void setArea_id(int area_id) {
        this.area_id = area_id;
    }

    public int getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(int branch_id) {
        this.branch_id = branch_id;
    }

    public int getCreated_by() {
        return created_by;
    }

    public void setCreated_by(int created_by) {
        this.created_by = created_by;
    }

    public int getCenter_status() {
        return center_status;
    }

    public void setCenter_status(int center_status) {
        this.center_status = center_status;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    @ColumnInfo(name = "branch_id")
    int branch_id;

    @ColumnInfo(name = "created_by")
    int created_by;

    @ColumnInfo(name = "created_date")
    String created_date;

    public String getCenter_desc() {
        return center_desc;
    }

    public void setCenter_desc(String center_desc) {
        this.center_desc = center_desc;
    }
}
