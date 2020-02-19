package com.example.logindemo.Models;

public class ModelPost {

    String uId,uName,uSurname, uEmail, uPhone, pId, pTitle, pDesc, pBlood, pTypeh, pHospital, pTimestap;

    public ModelPost() {
    }

    public ModelPost(String uId, String uName, String uSurname, String uEmail, String uPhone, String pId, String pTitle, String pDesc, String pBlood, String pTypeh, String pHospital, String pTimestap) {
        this.uId = uId;
        this.uName = uName;
        this.uSurname = uSurname;
        this.uEmail = uEmail;
        this.uPhone = uPhone;
        this.pId = pId;
        this.pTitle = pTitle;
        this.pDesc = pDesc;
        this.pBlood = pBlood;
        this.pTypeh = pTypeh;
        this.pHospital = pHospital;
        this.pTimestap = pTimestap;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuSurname() {
        return uSurname;
    }

    public void setuSurname(String uSurname) {
        this.uSurname = uSurname;
    }

    public String getuEmail() {
        return uEmail;
    }

    public void setuEmail(String uEmail) {
        this.uEmail = uEmail;
    }

    public String getuPhone() {
        return uPhone;
    }

    public void setuPhone(String uPhone) {
        this.uPhone = uPhone;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getpTitle() {
        return pTitle;
    }

    public void setpTitle(String pTitle) {
        this.pTitle = pTitle;
    }

    public String getpDesc() {
        return pDesc;
    }

    public void setpDesc(String pDesc) {
        this.pDesc = pDesc;
    }

    public String getpBlood() {
        return pBlood;
    }

    public void setpBlood(String pBlood) {
        this.pBlood = pBlood;
    }

    public String getpTypeh() {
        return pTypeh;
    }

    public void setpTypeh(String pTypeh) {
        this.pTypeh = pTypeh;
    }

    public String getpHospital() {
        return pHospital;
    }

    public void setpHospital(String pHospital) {
        this.pHospital = pHospital;
    }

    public String getpTimestap() {
        return pTimestap;
    }

    public void setpTimestap(String pTimestap) {
        this.pTimestap = pTimestap;
    }
}
