package com.profebot.model;

import com.profebot.enums.EquationOptionType;

public class EquationOption {
    private String content;

    private EquationOptionType type;

    public EquationOption(String content, EquationOptionType type) {
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public EquationOptionType getEquationOptionType() {
        return type;
    }
}
