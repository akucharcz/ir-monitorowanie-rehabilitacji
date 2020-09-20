package com.ibm.ir.model;

import lombok.Data;

@Data
public class LoginStructure {
    private String fullname;
    private String password;
    private boolean patient = true;

    public LoginStructure(String fullname, String password) {
        this.fullname = fullname;
        this.password = password;
    }

    public String getLogin() {
        return getFullname();
    }
}
