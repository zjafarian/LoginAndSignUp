package com.example.loginandsignup.model;

import java.io.Serializable;

public class BankUser implements Serializable {
    private String userName = "";
    private String password = "";

    public BankUser(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public BankUser() {

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
