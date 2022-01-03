package com.ingenioussys.microfinance.model;

public class LoanEmi {
    String emi_no;
    String laon_application_no;

    public String getLaon_application_no() {
        return laon_application_no;
    }

    public void setLaon_application_no(String laon_application_no) {
        this.laon_application_no = laon_application_no;
    }

    int loan_distribution_emi_id;

    public int getLoan_distribution_emi_id() {
        return loan_distribution_emi_id;
    }

    public void setLoan_distribution_emi_id(int loan_distribution_emi_id) {
        this.loan_distribution_emi_id = loan_distribution_emi_id;
    }

    String inc_date;
    String scheduled_payment;
    int status;

    public String getEmi_no() {
        return emi_no;
    }

    public void setEmi_no(String emi_no) {
        this.emi_no = emi_no;
    }

    public String getInc_date() {
        return inc_date;
    }

    public void setInc_date(String inc_date) {
        this.inc_date = inc_date;
    }

    public String getScheduled_payment() {
        return scheduled_payment;
    }

    public void setScheduled_payment(String scheduled_payment) {
        this.scheduled_payment = scheduled_payment;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
