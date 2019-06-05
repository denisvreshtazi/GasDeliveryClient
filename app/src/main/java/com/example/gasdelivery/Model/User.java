package com.example.gasdelivery.Model;

public class User {
    private String Email;
    private String Name;
    private String Password;
    private String Phone;

    public User(){

    }
    public User(String email, String name, String password, String phone) {
        Email = email;
        Name = name;
        Password = password;
        Phone = phone;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
