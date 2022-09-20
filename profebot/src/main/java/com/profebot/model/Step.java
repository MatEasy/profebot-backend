package com.profebot.model;

import java.util.List;

public class Step {
    // Prosa de la opción
    private String option;

    // Ecuación de la opción. Ejemplo: 1+1/2=2x+3
    private List<EquationOption> equationOptions;

    // Prosa más detallada de la opción
    private String info;

    public Step(String option, List<EquationOption> equationOptions, String info) {
        this.option = option;
        this.equationOptions = equationOptions;
        this.info = info;
    }

    public String getOption() {
        return option;
    }

    public List<EquationOption> getEquationOptions() {
        return equationOptions;
    }

    public void setEquationOption(List<EquationOption> equationOption) {
        this.equationOptions = equationOption;
    }

    public String getInfo() {
        return info;
    }

}
