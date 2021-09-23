package com.appsnipp.creativelogindesigns;

public class UserHelper {
    String name,mobileno,email,password,SapId;

    public UserHelper(String name,String SapId, String mobileno, String email, String password) {
        this.name = name;
        this.SapId = SapId;
        this.mobileno = mobileno;
        this.email = email;
        this.password = password;
    }

    public UserHelper() {

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getSapId() {
        return SapId;
    }

    public void setSapId(String SapId) {
        this.SapId = SapId;
    }
    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
