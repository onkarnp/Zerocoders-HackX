package com.example.zerocoders.models;

public class Request {
    String name, phoneNo, bloodGroup, pincode, reason;

    public  Request(){}
    public Request(String name, String phoneNo, String bloodGroup, String pincode, String reason) {
        this.name = name;
        this.phoneNo = phoneNo;
        this.bloodGroup = bloodGroup;
        this.pincode = pincode;
        this.reason = reason;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public String toString() {
        return
                "Name = " + name +
                " Phone No = " + phoneNo +
                " Blood Group = " + bloodGroup  +
                " Pincode = " + pincode  +
                " Reason = " + reason ;

    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
