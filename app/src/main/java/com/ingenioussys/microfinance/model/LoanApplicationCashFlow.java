package com.ingenioussys.microfinance.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "manage_loan_application_cash_flow")
public class LoanApplicationCashFlow {

    @PrimaryKey(autoGenerate = true)
    int loan_application_cash_flow_id;

    @ColumnInfo(name = "branch_id")
    int branch_id;

    @ColumnInfo(name = "bank_id")
    int bank_id;

    @ColumnInfo(name = "loan_application_number")
    String loan_application_number;

    public String getLoan_application_number() {
        return loan_application_number;
    }

    public void setLoan_application_number(String loan_application_number) {
        this.loan_application_number = loan_application_number;
    }

    public int getBank_id() {
        return bank_id;
    }

    public void setBank_id(int bank_id) {
        this.bank_id = bank_id;
    }

    @ColumnInfo(name = "opening_bal")
    String 	opening_bal;

    @ColumnInfo(name = "loan_application_id")
    int  loan_application_id;

    @ColumnInfo(name = "sale_amount")
    String 	sale_amount;

    @ColumnInfo(name = "asset_sale")
    String 	asset_sale;

    @ColumnInfo(name = "deb_receipts")
    String deb_receipts;

    @ColumnInfo(name = "family_income")
    String 	family_income;

    @ColumnInfo(name = "other_income")
    String 	other_income;

    @ColumnInfo(name = "current_bal")
    String 	current_bal;

    @ColumnInfo(name = "outgoing_amount")
    String 	outgoing_amount;

    @ColumnInfo(name = "wages")
    String 	wages;

    @ColumnInfo(name = "income_tax")
    String 	income_tax;

    @ColumnInfo(name = "licensing")
    String 	licensing;

    @ColumnInfo(name = "stationary_printing")
    String 	stationary_printing;

    @ColumnInfo(name = "repair_maintenance")
    String 	repair_maintenance;

    @ColumnInfo(name = "rents_rates")
    String rents_rates;

    @ColumnInfo(name = "Loan")
    String Loan;

    @ColumnInfo(name = "utilities")
    String utilities;

    @ColumnInfo(name = "credit_card_fees")
    String credit_card_fees;

    @ColumnInfo(name = "interest_paid")
    String interest_paid;

    @ColumnInfo(name = "bank_charges")
    String bank_charges;


    @ColumnInfo(name = "created_by")
    int created_by;

    @ColumnInfo(name = "created_date")
    String 	created_date;

    @ColumnInfo(name = "advertisement_and_marketing")
    String advertisement_and_marketing;

    @ColumnInfo(name = "solicitor_fees")
    String solicitor_fees;

    @ColumnInfo(name = "motor_vehicle_ex")
    String motor_vehicle_ex;

    public String getMotor_vehicle_ex() {
        return motor_vehicle_ex;
    }

    public void setMotor_vehicle_ex(String motor_vehicle_ex) {
        this.motor_vehicle_ex = motor_vehicle_ex;
    }

    public String getAdvertisement_and_marketing() {
        return advertisement_and_marketing;
    }

    public void setAdvertisement_and_marketing(String advertisement_and_marketing) {
        this.advertisement_and_marketing = advertisement_and_marketing;
    }

    public String getSolicitor_fees() {
        return solicitor_fees;
    }

    public void setSolicitor_fees(String solicitor_fees) {
        this.solicitor_fees = solicitor_fees;
    }

    public String getTotal_income() {
        return total_income;
    }

    public void setTotal_income(String total_income) {
        this.total_income = total_income;
    }

    public String getPurchase() {
        return purchase;
    }

    public void setPurchase(String purchase) {
        this.purchase = purchase;
    }

    public String getAccountant_fees() {
        return accountant_fees;
    }

    public void setAccountant_fees(String accountant_fees) {
        this.accountant_fees = accountant_fees;
    }

    @ColumnInfo(name = "total_income")
    String total_income;

    @ColumnInfo(name = "purchase")
    String purchase;

    @ColumnInfo(name = "accountant_fees")
    String accountant_fees;

    public int getLoan_application_cash_flow_id() {
        return loan_application_cash_flow_id;
    }

    public void setLoan_application_cash_flow_id(int loan_application_cash_flow_id) {
        this.loan_application_cash_flow_id = loan_application_cash_flow_id;
    }

    public int getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(int branch_id) {
        this.branch_id = branch_id;
    }

    public int getLoan_application_id() {
        return loan_application_id;
    }

    public void setLoan_application_id(int loan_application_id) {
        this.loan_application_id = loan_application_id;
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

    public String getOpening_bal() {
        return opening_bal;
    }

    public void setOpening_bal(String opening_bal) {
        this.opening_bal = opening_bal;
    }

    public String getSale_amount() {
        return sale_amount;
    }

    public void setSale_amount(String sale_amount) {
        this.sale_amount = sale_amount;
    }

    public String getAsset_sale() {
        return asset_sale;
    }

    public void setAsset_sale(String asset_sale) {
        this.asset_sale = asset_sale;
    }

    public String getDeb_receipts() {
        return deb_receipts;
    }

    public void setDeb_receipts(String deb_receipts) {
        this.deb_receipts = deb_receipts;
    }

    public String getFamily_income() {
        return family_income;
    }

    public void setFamily_income(String family_income) {
        this.family_income = family_income;
    }

    public String getOther_income() {
        return other_income;
    }

    public void setOther_income(String other_income) {
        this.other_income = other_income;
    }

    public String getCurrent_bal() {
        return current_bal;
    }

    public void setCurrent_bal(String current_bal) {
        this.current_bal = current_bal;
    }

    public String getOutgoing_amount() {
        return outgoing_amount;
    }

    public void setOutgoing_amount(String outgoing_amount) {
        this.outgoing_amount = outgoing_amount;
    }

    public String getWages() {
        return wages;
    }

    public void setWages(String wages) {
        this.wages = wages;
    }

    public String getIncome_tax() {
        return income_tax;
    }

    public void setIncome_tax(String income_tax) {
        this.income_tax = income_tax;
    }

    public String getLicensing() {
        return licensing;
    }

    public void setLicensing(String licensing) {
        this.licensing = licensing;
    }

    public String getStationary_printing() {
        return stationary_printing;
    }

    public void setStationary_printing(String stationary_printing) {
        this.stationary_printing = stationary_printing;
    }

    public String getRepair_maintenance() {
        return repair_maintenance;
    }

    public void setRepair_maintenance(String repair_maintenance) {
        this.repair_maintenance = repair_maintenance;
    }

    public String getRents_rates() {
        return rents_rates;
    }

    public void setRents_rates(String rents_rates) {
        this.rents_rates = rents_rates;
    }

    public String getLoan() {
        return Loan;
    }

    public void setLoan(String loan) {
        Loan = loan;
    }

    public String getUtilities() {
        return utilities;
    }

    public void setUtilities(String utilities) {
        this.utilities = utilities;
    }

    public String getCredit_card_fees() {
        return credit_card_fees;
    }

    public void setCredit_card_fees(String credit_card_fees) {
        this.credit_card_fees = credit_card_fees;
    }

    public String getInterest_paid() {
        return interest_paid;
    }

    public void setInterest_paid(String interest_paid) {
        this.interest_paid = interest_paid;
    }

    public String getBank_charges() {
        return bank_charges;
    }

    public void setBank_charges(String bank_charges) {
        this.bank_charges = bank_charges;
    }
}
