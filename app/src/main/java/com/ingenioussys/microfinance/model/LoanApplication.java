package com.ingenioussys.microfinance.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "manage_loan_application")
public class LoanApplication {

    String member_photo_pr;
    String voter_id_no;

    public String getVoter_id_no() {
        return voter_id_no;
    }

    public void setVoter_id_no(String voter_id_no) {
        this.voter_id_no = voter_id_no;
    }

    public String getMember_photo_pr() {
        return member_photo_pr;
    }

    public void setMember_photo_pr(String member_photo_pr) {
        this.member_photo_pr = member_photo_pr;
    }

    @ColumnInfo(name = "active")
    private boolean active;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @PrimaryKey(autoGenerate = true)
    int loan_application_id;

    @ColumnInfo(name = "loan_application_number")
    String loan_application_number;

    public String getLoan_application_number() {
        return loan_application_number;
    }

    public void setLoan_application_number(String loan_application_number) {
        this.loan_application_number = loan_application_number;
    }

    @ColumnInfo(name = "area_id")
    int area_id;

    public int getArea_id() {
        return area_id;
    }

    public void setArea_id(int area_id) {
        this.area_id = area_id;
    }

    @ColumnInfo(name = "bank_id")
    int bank_id;

    public int getBank_id() {
        return bank_id;
    }

    public void setBank_id(int bank_id) {
        this.bank_id = bank_id;
    }


    @ColumnInfo(name = "is_cgt_verfied")
    int is_cgt_verfied;

    @ColumnInfo(name = "is_blc_verfied")
    int is_blc_verfied;

    @ColumnInfo(name = "branch_id")
    int branch_id;

    public int getIs_cgt_verfied() {
        return is_cgt_verfied;
    }

    public void setIs_cgt_verfied(int is_cgt_verfied) {
        this.is_cgt_verfied = is_cgt_verfied;
    }

    public int getIs_blc_verfied() {
        return is_blc_verfied;
    }

    public void setIs_blc_verfied(int is_blc_verfied) {
        this.is_blc_verfied = is_blc_verfied;
    }

    @ColumnInfo(name = "applicant_name")
    String applicant_name;

    @ColumnInfo(name = "applicant_father_name")
    String applicant_father_name;

    @ColumnInfo(name = "dob")
    String 	dob;

    @ColumnInfo(name = "age")
    String 	age;

    @ColumnInfo(name = "gender")
    String 	gender;

    @ColumnInfo(name = "applicant_mob_no")
    String 	applicant_mob_no;

    @ColumnInfo(name = "address")
    String 	address;

    @ColumnInfo(name = "tq")
    String 	tq;

    @ColumnInfo(name = "dist")
    String 	dist;

    @ColumnInfo(name = "state")
    String 	state;

    @ColumnInfo(name = "pincode")
    String 	pincode;

    @ColumnInfo(name = "uid_no")
    String 	uid_no;








    @ColumnInfo(name = "center_id")
    int center_id;

    @ColumnInfo(name = "group_id")
    int group_id;

    @ColumnInfo(name = "created_by")
    int created_by;

    @ColumnInfo(name = "created_date")
    String 	created_date;

    @ColumnInfo(name = "approved_status")
    int approved_status;

    @ColumnInfo(name = "approved_by")
    int approved_by;

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

    public int getApproved_status() {
        return approved_status;
    }

    public void setApproved_status(int approved_status) {
        this.approved_status = approved_status;
    }

    public int getApproved_by() {
        return approved_by;
    }

    public void setApproved_by(int approved_by) {
        this.approved_by = approved_by;
    }

    public int getCenter_id() {
        return center_id;
    }

    public void setCenter_id(int center_id) {
        this.center_id = center_id;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getApplicant_name() {
        return applicant_name;
    }

    public void setApplicant_name(String applicant_name) {
        this.applicant_name = applicant_name;
    }

    public String getApplicant_father_name() {
        return applicant_father_name;
    }

    public void setApplicant_father_name(String applicant_father_name) {
        this.applicant_father_name = applicant_father_name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getApplicant_mob_no() {
        return applicant_mob_no;
    }

    public void setApplicant_mob_no(String applicant_mob_no) {
        this.applicant_mob_no = applicant_mob_no;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTq() {
        return tq;
    }

    public void setTq(String tq) {
        this.tq = tq;
    }

    public String getDist() {
        return dist;
    }

    public void setDist(String dist) {
        this.dist = dist;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getUid_no() {
        return uid_no;
    }

    public void setUid_no(String uid_no) {
        this.uid_no = uid_no;
    }


}
