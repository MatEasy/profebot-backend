package com.profebot.enums;

public enum Conclusion {
    EQUATION_WITH_FINITE_SOLUTIONS_FIRST_TEXT("El conjunto de soluciones de la ecuación:"),
    EQUATION_WITH_FINITE_SOLUTIONS_SECOND_TEXT("se lo puede escribir como: X = { /raices/ }."),

    EQUATION_WITH_INFINITE_SOLUTIONS_FIRST_TEXT("El conjunto de soluciones de la ecuación:"),
    EQUATION_WITH_INFINITE_SOLUTIONS_SECOND_TEXT("es infinito, dado que cualquier valor reemplazado en X, verifica la igualdad."),

    EQUATION_WITHOUT_SOLUTIONS_FIRST_TEXT("El conjunto de soluciones de la ecuación:"),
    EQUATION_WITHOUT_SOLUTIONS_SECOND_TEXT("es vacio, dado que ningún valor reemplazado en X, verifica la igualdad."),

    INEQUATION_WITH_INTERVAL_SOLUTIONS_FIRST_TEXT("El conjunto de soluciones de la inecuación:"),
    INEQUATION_WITH_INTERVAL_SOLUTIONS_SECOND_TEXT("se lo puede expresar como: /intervalos/"),

    INEQUATION_WITHOUT_SOLUTIONS_FIRST_TEXT("La inecuación:"),
    INEQUATION_WITHOUT_SOLUTIONS_SECOND_TEXT("no se verifica para ningún valor de X. El conjunto solución entonces, es el conjunto vacío."),

    COMPLEX_SOLUTIONS_FIRST_TEXT("No existen soluciones reales para:"),
    COMPLEX_SOLUTIONS_SECOND_TEXT("por tanto, no se puede seguir operando.");

    private String conclusion;

    Conclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public String getConclusion() {
        return conclusion;
    }
}
