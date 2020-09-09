package com.ibm.ir.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginStructure {
    private String login;
    private String password;
}
