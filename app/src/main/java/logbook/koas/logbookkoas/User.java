package logbook.koas.logbookkoas;

import java.util.Date;

public class User {
    String username;
    String level;
    String nama;
    Date sessionExpiryDate;
    String fullName;
    String password;

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setLevel(String level) {
        this.level= level;
    }
    public void setNama(String fullName) {
        this.nama= fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setSessionExpiryDate(Date sessionExpiryDate) {
        this.sessionExpiryDate = sessionExpiryDate;
    }




    public String getUsername() {
        return username;
    }

    public String getLevel() {
        return level;
    }
    public String getNama() {
        return nama;
    }
    public String getFullName() {
        return fullName;
    }

    public Date getSessionExpiryDate() {
        return sessionExpiryDate;
    }

}