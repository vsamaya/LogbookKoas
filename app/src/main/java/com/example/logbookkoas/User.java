package com.example.logbookkoas;

import java.util.Date;

public class User {
    String username;
    String fullName;
    String filterStase;
    String filterJenisJurnal;
    String filterStatus;
    String filterMahasiswa;
    Date sessionExpiryDate;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setSessionExpiryDate(Date sessionExpiryDate) {
        this.sessionExpiryDate = sessionExpiryDate;
    }

    public void setFilterStase(String filterStase) {
        this.filterStase = filterStase;
    }
    public void setFilterJenisJurnal(String filterJenisJurnal) {
        this.filterJenisJurnal = filterJenisJurnal;
    }
    public void setFilterStatus(String status) {
        this.filterStatus = status;
    }
    public void setFilterMahasiswa(String mahasiswa) {
        this.filterMahasiswa= mahasiswa;
    }

    public  String getFilterStase(){return filterStase;}
    public  String getFilterJenisJurnal(){return filterJenisJurnal;}
    public  String getFilterMahasiswa(){return filterMahasiswa;}
    public  String getFilterStatus(){return filterStatus;}


    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }

    public Date getSessionExpiryDate() {
        return sessionExpiryDate;
    }

}
