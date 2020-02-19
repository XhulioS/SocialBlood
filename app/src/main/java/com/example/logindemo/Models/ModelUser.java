package com.example.logindemo.Models;

public class ModelUser {
    String celular,uid,email;

    public ModelUser(){
    }

    public ModelUser(String celular, String uid, String email) {
        this.celular = celular;
        this.uid = uid;
        this.email = email;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
