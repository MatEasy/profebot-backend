package com.profebot.model;

public class Step {
    // Prosa de la opción
    private String option;

    // Ecuación de la opción. Ejemplo: 1+1/2=2x+3
    private String equationOption;

    public Step(String option, String equationOption) {
        this.option = option;
        this.equationOption = equationOption;
    }

    public String getOption() {
        return option;
    }

    public String getEquationOption() {
        return equationOption;
    }

    public void setEquationOption(String equationOption) {
        this.equationOption = equationOption;
    }

}
