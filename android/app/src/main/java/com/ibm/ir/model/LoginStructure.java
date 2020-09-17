package com.ibm.ir.model;

import lombok.Data;

@Data
public class LoginStructure {
    private String fullname;
    private String password;
    private boolean isUser = true;

    public LoginStructure(String username, String password) {
        this.fullname = username;
        this.password = password;
    }

    public String getLogin() {
        return this.getFullname();
    }
}
