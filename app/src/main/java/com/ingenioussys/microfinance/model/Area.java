package com.ingenioussys.microfinance.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Objects;

@Entity(tableName = "manage_assign_area")
public class Area  {
    @ColumnInfo(name = "area_name")
    String area_name;
    @ColumnInfo(name = "bank_id")
    int bank_id;
    @ColumnInfo(name = "survey_uniqe_id")
    String survey_uniqe_id;

    public String getSurvey_uniqe_id() {
        return survey_uniqe_id;
    }

    public void setSurvey_uniqe_id(String survey_uniqe_id) {
        this.survey_uniqe_id = survey_uniqe_id;
    }

    public int getBank_id() {
        return bank_id;
    }

    public void setBank_id(int bank_id) {
        this.bank_id = bank_id;
    }

    @PrimaryKey(autoGenerate = true)
    int assign_area_id;

    @ColumnInfo(name = "latitude")
    double latitude;

    @ColumnInfo(name = "longitude")
    double 	longitude;

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public int getAssign_area_id() {
        return assign_area_id;
    }

    public void setAssign_area_id(int assign_area_id) {
        this.assign_area_id = assign_area_id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
