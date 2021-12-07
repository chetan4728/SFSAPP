package com.ingenioussys.microfinance.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "manage_loan_application_cash_flow_two")
public class LoanApplicationCashTwoFlow {

    @PrimaryKey(autoGenerate = true)
    int loan_application_cash_flow_two_id;

    @ColumnInfo(name = "loan_application_number")
    String loan_application_number;

    public String getLoan_application_number() {
        return loan_application_number;
    }

    public void setLoan_application_number(String loan_application_number) {
        this.loan_application_number = loan_application_number;
    }

    public int getLoan_application_cash_flow_two_id() {
        return loan_application_cash_flow_two_id;
    }

    public void setLoan_application_cash_flow_two_id(int loan_application_cash_flow_two_id) {
        this.loan_application_cash_flow_two_id = loan_application_cash_flow_two_id;
    }

    @ColumnInfo(name = "bank_id")
    int bank_id;

    public int getBank_id() {
        return bank_id;
    }

    public void setBank_id(int bank_id) {
        this.bank_id = bank_id;
    }

    @ColumnInfo(name = "loan_application_id")
    int loan_application_id;

    public int getLoan_application_id() {
        return loan_application_id;
    }

    public void setLoan_application_id(int loan_application_id) {
        this.loan_application_id = loan_application_id;
    }

    @ColumnInfo(name = "no_of_dep")
    String no_of_dep;

    @ColumnInfo(name = "house_hold_income")
    String house_hold_income;

    @ColumnInfo(name = "current_profession")
    String current_profession;

    @ColumnInfo(name = "lottery")
    String lottery;

    @ColumnInfo(name = "spouse_name")
    String spouse_name;

    @ColumnInfo(name = "branch_id")
    int branch_id;

    @ColumnInfo(name = "created_by")
    int created_by;

    @ColumnInfo(name = "created_date")
    String 	created_date;

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

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    @ColumnInfo(name = "income_of_this_month")
    String income_of_this_month;

    public String getCurrent_profession() {
        return current_profession;
    }

    public void setCurrent_profession(String current_profession) {
        this.current_profession = current_profession;
    }
    public void setHouse_hold_income(String house_hold_income) {
        this.house_hold_income = house_hold_income;
    }

    public String getIncome_of_this_month() {
        return income_of_this_month;
    }

    public void setIncome_of_this_month(String income_of_this_month) {
        this.income_of_this_month = income_of_this_month;
    }

    public String getNo_of_dep() {
        return no_of_dep;
    }

    public void setNo_of_dep(String no_of_dep) {
        this.no_of_dep = no_of_dep;
    }

    public String getHouse_hold_income() {
        return house_hold_income;
    }
    public String getLottery() {
        return lottery;
    }

    public void setLottery(String lottery) {
        this.lottery = lottery;
    }

    public String getSpouse_name() {
        return spouse_name;
    }

    public void setSpouse_name(String spouse_name) {
        this.spouse_name = spouse_name;
    }

}
