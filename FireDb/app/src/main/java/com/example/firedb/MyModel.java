package com.example.firedb;

public class MyModel {
    String ilink,name,email,roll,phone;

    public String getIlink() {
        return ilink;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRoll() {
        return roll;
    }

    public String getPhone() {
        return phone;
    }

    public MyModel(String ilink, String name, String email, String roll, String phone) {
        this.ilink = ilink;
        this.name = name;
        this.email = email;
        this.roll = roll;
        this.phone = phone;
    }

    public MyModel() {
    }

}
