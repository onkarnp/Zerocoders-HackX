package com.example.zerocoders.models;

public class Users {
    private String name, state, city, bloodGroup, phoneNo, email, password, dob, pincode;

    public Users(String name, String number, String pin, String dob, String email, String password, String bloodgroup){}

    public Users(String name, String phoneNo, String pincode, String state, String city, String dob, String email, String password, String bloodGroup) {
        this.name = name;
        this.phoneNo = phoneNo;
        this.pincode = pincode;
        this.state = state;
        this.city = city;
        this.dob = dob;
        this.email = email;
        this.password = password;
        this.bloodGroup = bloodGroup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPincode() { return pincode; }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getState() { return state; }

    public void setState(String state) {
        this.state = state;
    }
}