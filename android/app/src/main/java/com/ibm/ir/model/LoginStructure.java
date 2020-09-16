package com.ibm.ir.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class LoginStructure {
    private static String login;
    private String password;

    public LoginStructure(String username, String password) {
        this.login = username;
        this.password = password;
    }

    public static String getLogin() {
        return login;
    }
}
