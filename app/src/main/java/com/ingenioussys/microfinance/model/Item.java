package com.ingenioussys.microfinance.model;

import com.multilevelview.models.RecyclerViewItem;

public class Item extends RecyclerViewItem {

    String text="";

    String secondText = "";
    String thirdText = "";
    String loan_application_no = "";

    public String getLoan_application_no() {
        return loan_application_no;
    }

    public void setLoan_application_no(String loan_application_no) {
        this.loan_application_no = loan_application_no;
    }

    public String getThirdText() {
        return thirdText;
    }

    public void setThirdText(String thirdText) {
        this.thirdText = thirdText;
    }



    public String getSecondText() {
        return secondText;
    }

    public void setSecondText(String secondText) {
        this.secondText = secondText;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public Item(int level) {
        super(level);
    }
}
