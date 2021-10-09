package com.example.zerocoders.models;

public class Donor {
    String name, email, phoneNo, dob;

    public Donor() {}

    public Donor(String name, String email, String phoneNo, String dob) {
        this.name = name;
        this.email = email;
        this.phoneNo = phoneNo;
        this.dob = dob;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}
