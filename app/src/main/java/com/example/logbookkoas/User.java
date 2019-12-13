package com.example.logbookkoas;

import java.util.Date;

public class User {
    String username;
    String fullName;
    String level;
    Date sessionExpiryDate;

    public void setLevel(String level) {
        this.level = level;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setSessionExpiryDate(Date sessionExpiryDate) {
        this.sessionExpiryDate = sessionExpiryDate;
    }

    public String getLevel() {
        return level;
    }

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
