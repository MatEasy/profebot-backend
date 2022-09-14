package com.profebot.enums;

public enum Justification {
    SIMPLIFY_ARITHMETIC_OPTION("Simplificar operación aritmética"),
    SIMPLIFY_ARITHMETIC_JUSTIFICATION("Para simplificar términos, resolver las sumas, restas, multiplicaciones, divisiones, potencias o raíces, que involucren solo números"),
    SIMPLIFY_ARITHMETIC_SUMMARY("Resolver operación"),

    DIVISION_BY_NEGATIVE_ONE_OPTION("Simplificar multiplicación o división por -1"),
    DIVISION_BY_NEGATIVE_ONE_JUSTIFICATION("Cambiar los signos de los términos que estén afectados por una división o multiplicación por -1"),
    DIVISION_BY_NEGATIVE_ONE_SUMMARY("Resolver operación"),

    DIVISION_BY_ONE_OPTION("Simplificar multiplicación o división por 1"),
    DIVISION_BY_ONE_JUSTIFICATION("Eliminar las multiplicaciones y divisiones por 1, que no modifican a los términos que afectan"),
    DIVISION_BY_ONE_SUMMARY("Reducir término");

    private String justification;

    Justification(String justification) {
        this.justification = justification;
    }

    public String getJustification() {
        return justification;
    }
}
