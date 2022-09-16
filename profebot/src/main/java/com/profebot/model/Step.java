package com.profebot.model;

public class Step {
    // Prosa de la opción
    private String option;

    // Ecuación de la opción. Ejemplo: 1+1/2=2x+3
    private String equationOption;

    // Prosa más detallada de la opción
    private String info;

    public Step(String option, String equationOption, String info) {
        this.option = option;
        this.equationOption = equationOption;
        this.info = info;
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

    public String getInfo() {
        return info;
    }

}
