package com.example.zerocoders.models;
/*Omkar*/
public class Users {
    private String name, city, bloodGroup, phoneNo, email, password, dob, state;

    public Users(){}

    public Users(String name, String city, String state, String bloodGroup, String phoneNo, String email, String password, String dob) {
        this.name = name;
        this.city = city;
        this.state = state;
        this.bloodGroup = bloodGroup;
        this.phoneNo = phoneNo;
        this.email = email;
        this.password = password;
        this.dob = dob;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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
}
