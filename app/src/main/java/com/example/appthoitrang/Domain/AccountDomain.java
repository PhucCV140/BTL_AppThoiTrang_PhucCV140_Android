package com.example.appthoitrang.Domain;

import java.io.Serializable;

public class AccountDomain implements Serializable {
    private String username, password, address, email;
    private int isAdmin;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    public AccountDomain() {
    }

    public AccountDomain(String username, String password, String address, String email, int isAdmin) {
        this.username = username;
        this.password = password;
        this.address = address;
        this.email = email;
        this.isAdmin = isAdmin;
    }
}
