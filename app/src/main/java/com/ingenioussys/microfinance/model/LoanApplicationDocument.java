package com.ingenioussys.microfinance.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "manage_loan_application_documents")
public class LoanApplicationDocument {

    @PrimaryKey(autoGenerate = true)
    int loan_application_document_id;
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

    public int getLoan_application_document_id() {
        return loan_application_document_id;
    }

    public void setLoan_application_document_id(int loan_application_document_id) {
        this.loan_application_document_id = loan_application_document_id;
    }

    public int getLoan_application_id() {
        return loan_application_id;
    }

    public void setLoan_application_id(int loan_application_id) {
        this.loan_application_id = loan_application_id;
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

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    @ColumnInfo(name = "loan_application_id")
    int  loan_application_id;


    @ColumnInfo(name = "loan_cycle")
    String loan_cycle;

    @ColumnInfo(name = "marital_status")
    String marital_status;

    @ColumnInfo(name = "religion")
    String 	religion;

    @ColumnInfo(name = "cast")
    String 	cast;

    @ColumnInfo(name = "co_name")
    String 	co_name;

    @ColumnInfo(name = "co_dob")
    String 	co_dob;

    @ColumnInfo(name = "family_name1")
    String 	family_name1;

    @ColumnInfo(name = "family_relation1")
    String 	family_relation1;

    @ColumnInfo(name = "family_name2")
    String 	family_name2;

    @ColumnInfo(name = "family_relation2")
    String 	family_relation2;

    @ColumnInfo(name = "family_name3")
    String 	family_name3;

    @ColumnInfo(name = "family_relation3")
    String 	family_relation3;

    @ColumnInfo(name = "family_name4")
    String 	family_name4;

    @ColumnInfo(name = "family_relation4")
    String 	family_relation4;

    @ColumnInfo(name = "email_id")
    String 	email_id;

    @ColumnInfo(name = "ration_card_no")
    String 	ration_card_no;

    @ColumnInfo(name = "pan_card_no")
    String 	pan_card_no;

    @ColumnInfo(name = "nominee_name")
    String 	nominee_name;

    @ColumnInfo(name = "nominee_age")
    String 	nominee_age;

    @ColumnInfo(name = "nominee_relation")
    String 	nominee_relation;

    public String getNominee_relation() {
        return nominee_relation;
    }

    public void setNominee_relation(String nominee_relation) {
        this.nominee_relation = nominee_relation;
    }

    @ColumnInfo(name = "member_photo_pr")
    String 	member_photo_pr;

    @ColumnInfo(name = "member_pan_card")
    String 	member_pan_card;

    @ColumnInfo(name = "member_adhar_card")
    String 	member_adhar_card;

    @ColumnInfo(name = "member_other_proof")
    String 	member_other_proof;

    @ColumnInfo(name = "member_new_business_activity")
    String 	member_new_business_activity;

    @ColumnInfo(name = "member_photo_signature")
    String 	member_photo_signature;

    @ColumnInfo(name = "loan_purpose")
    String 	loan_purpose;

    @ColumnInfo(name = "loan_amount")
    String 	loan_amount;

    public String getLoan_cycle() {
        return loan_cycle;
    }

    @ColumnInfo(name = "branch_id")
    int branch_id;

    @ColumnInfo(name = "created_by")
    int created_by;

    @ColumnInfo(name = "created_date")
    String 	created_date;
    public void setLoan_cycle(String loan_cycle) {
        this.loan_cycle = loan_cycle;
    }

    public String getMarital_status() {
        return marital_status;
    }

    public void setMarital_status(String marital_status) {
        this.marital_status = marital_status;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public String getCo_name() {
        return co_name;
    }

    public void setCo_name(String co_name) {
        this.co_name = co_name;
    }

    public String getCo_dob() {
        return co_dob;
    }

    public void setCo_dob(String co_dob) {
        this.co_dob = co_dob;
    }

    public String getFamily_name1() {
        return family_name1;
    }

    public void setFamily_name1(String family_name1) {
        this.family_name1 = family_name1;
    }

    public String getFamily_relation1() {
        return family_relation1;
    }

    public void setFamily_relation1(String family_relation1) {
        this.family_relation1 = family_relation1;
    }

    public String getLoan_amount() {
        return loan_amount;
    }

    public void setLoan_amount(String loan_amount) {
        this.loan_amount = loan_amount;
    }

    public String getFamily_name2() {
        return family_name2;
    }

    public void setFamily_name2(String family_name2) {
        this.family_name2 = family_name2;
    }

    public String getFamily_relation2() {
        return family_relation2;
    }

    public void setFamily_relation2(String family_relation2) {
        this.family_relation2 = family_relation2;
    }

    public String getFamily_name3() {
        return family_name3;
    }

    public void setFamily_name3(String family_name3) {
        this.family_name3 = family_name3;
    }

    public String getFamily_relation3() {
        return family_relation3;
    }

    public void setFamily_relation3(String family_relation3) {
        this.family_relation3 = family_relation3;
    }

    public String getFamily_name4() {
        return family_name4;
    }

    public void setFamily_name4(String family_name4) {
        this.family_name4 = family_name4;
    }

    public String getFamily_relation4() {
        return family_relation4;
    }

    public void setFamily_relation4(String family_relation4) {
        this.family_relation4 = family_relation4;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getRation_card_no() {
        return ration_card_no;
    }

    public void setRation_card_no(String ration_card_no) {
        this.ration_card_no = ration_card_no;
    }

    public String getPan_card_no() {
        return pan_card_no;
    }

    public void setPan_card_no(String pan_card_no) {
        this.pan_card_no = pan_card_no;
    }

    public String getNominee_name() {
        return nominee_name;
    }

    public void setNominee_name(String nominee_name) {
        this.nominee_name = nominee_name;
    }

    public String getNominee_age() {
        return nominee_age;
    }

    public void setNominee_age(String nominee_age) {
        this.nominee_age = nominee_age;
    }

    public String getMember_photo_pr() {
        return member_photo_pr;
    }

    public void setMember_photo_pr(String member_photo_pr) {
        this.member_photo_pr = member_photo_pr;
    }

    public String getMember_pan_card() {
        return member_pan_card;
    }

    public void setMember_pan_card(String member_pan_card) {
        this.member_pan_card = member_pan_card;
    }

    public String getMember_adhar_card() {
        return member_adhar_card;
    }

    public void setMember_adhar_card(String member_adhar_card) {
        this.member_adhar_card = member_adhar_card;
    }

    public String getMember_other_proof() {
        return member_other_proof;
    }

    public void setMember_other_proof(String member_other_proof) {
        this.member_other_proof = member_other_proof;
    }

    public String getMember_new_business_activity() {
        return member_new_business_activity;
    }

    public void setMember_new_business_activity(String member_new_business_activity) {
        this.member_new_business_activity = member_new_business_activity;
    }

    public String getMember_photo_signature() {
        return member_photo_signature;
    }

    public void setMember_photo_signature(String member_photo_signature) {
        this.member_photo_signature = member_photo_signature;
    }

    public String getLoan_purpose() {
        return loan_purpose;
    }

    public void setLoan_purpose(String loan_purpose) {
        this.loan_purpose = loan_purpose;
    }
}
