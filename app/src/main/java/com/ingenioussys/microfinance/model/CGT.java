package com.ingenioussys.microfinance.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "manage_cgt")
public class CGT {
    @PrimaryKey(autoGenerate = true)
    int cgt_id;

    @ColumnInfo(name = "bank_id")
    int bank_id;


    @ColumnInfo(name = "group_id")
    int group_id;

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public int getBank_id() {
        return bank_id;
    }

    public void setBank_id(int bank_id) {
        this.bank_id = bank_id;
    }

    @ColumnInfo(name = "area_id")
    int area_id;
    @ColumnInfo(name = "center_id")
    int center_id;
    @ColumnInfo(name = "process_id")
    int process_id;


    @ColumnInfo(name = "number_of_customers")
    int number_of_customers;

    @ColumnInfo(name = "cgt_added_by")
    int cgt_added_by;

    @ColumnInfo(name = "branch_id")
    int branch_id;

    @ColumnInfo(name = "cgt_added_at")
    String cgt_added_at;

    @ColumnInfo(name = "picture_path")
    String picture_path;

    @ColumnInfo(name = "area_name")
    String area_name;

    @ColumnInfo(name = "center_name")
    String center_name;

    @ColumnInfo(name = "group_name")
    String group_name;

    @ColumnInfo(name = "CGT_ids")
    String CGT_ids;

    public String getCGT_ids() {
        return CGT_ids;
    }

    public void setCGT_ids(String CGT_ids) {
        this.CGT_ids = CGT_ids;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public String getCenter_name() {
        return center_name;
    }

    public void setCenter_name(String center_name) {
        this.center_name = center_name;
    }


    public int getCgt_id() {
        return cgt_id;
    }

    public void setCgt_id(int cgt_id) {
        this.cgt_id = cgt_id;
    }

    public int getArea_id() {
        return area_id;
    }

    public void setArea_id(int area_id) {
        this.area_id = area_id;
    }

    public int getCenter_id() {
        return center_id;
    }

    public void setCenter_id(int center_id) {
        this.center_id = center_id;
    }

    public int getProcess_id() {
        return process_id;
    }

    public void setProcess_id(int process_id) {
        this.process_id = process_id;
    }


    public int getNumber_of_customers() {
        return number_of_customers;
    }

    public void setNumber_of_customers(int number_of_customers) {
        this.number_of_customers = number_of_customers;
    }

    public String getPicture_path() {
        return picture_path;
    }

    public void setPicture_path(String picture_path) {
        this.picture_path = picture_path;
    }

    public int getCgt_added_by() {
        return cgt_added_by;
    }

    public void setCgt_added_by(int cgt_added_by) {
        this.cgt_added_by = cgt_added_by;
    }

    public int getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(int branch_id) {
        this.branch_id = branch_id;
    }

    public String getCgt_added_at() {
        return cgt_added_at;
    }

    public void setCgt_added_at(String cgt_added_at) {
        this.cgt_added_at = cgt_added_at;
    }
}
