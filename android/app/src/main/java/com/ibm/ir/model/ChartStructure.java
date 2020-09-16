package com.ibm.ir.model;

import lombok.Data;

@Data
public class ChartStructure {
    private String login;
    private String period;

    public ChartStructure(String login, String period) {
        this.login = login;
        this.period = period;
    }
}
