package com.profebot.model;

public class MultipleChoiceStep {
    // Prosa de la opción A
    private String optionA;
    // Ecuación de la opción A. Ejemplo: 1+1/2=2x+3
    private String equationOptionA;

    // Prosa de la opción B
    private String optionB;
    // Ecuación de la opción B. Ejemplo: 1+1/2=2x+3
    private String equationOptionB;

    // Prosa de la opción C
    private String optionC;
    // Ecuación de la opción C. Ejemplo: 1+1/2=2x+3
    private String equationOptionC;

    private Integer correctOption;

    public MultipleChoiceStep(String optionA, String equationOptionA, String optionB, String equationOptionB,
                              String optionC, String equationOptionC, Integer correctOption) {
        this.optionA = optionA;
        this.equationOptionA = equationOptionA;
        this.optionB = optionB;
        this.equationOptionB = equationOptionB;
        this.optionC = optionC;
        this.equationOptionC = equationOptionC;
        this.correctOption = correctOption;
    }

    public String getOptionA() {
        return optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public Integer getCorrectOption() {
        return correctOption;
    }

    public String getEquationOptionA() {
        return equationOptionA;
    }

    public void setEquationOptionA(String equationOptionA) {
        this.equationOptionA = equationOptionA;
    }

    public String getEquationOptionB() {
        return equationOptionB;
    }

    public void setEquationOptionB(String equationOptionB) {
        this.equationOptionB = equationOptionB;
    }

    public String getEquationOptionC() {
        return equationOptionC;
    }

    public void setEquationOptionC(String equationOptionC) {
        this.equationOptionC = equationOptionC;
    }
}
