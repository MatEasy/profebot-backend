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
    DIVISION_BY_ONE_SUMMARY("Reducir término"),

    MULTIPLY_BY_ZERO_OPTION("Simplificar multiplicación por 0"),
    MULTIPLY_BY_ZERO_JUSTIFICATION("Eliminar los términos que estén siendo multiplicados por 0"),
    MULTIPLY_BY_ZERO_SUMMARY("Reducir término"),

    REARRANGE_COEFF_OPTION("Reescribir producto de números y variables, como variables con coeficientes"),
    REARRANGE_COEFF_JUSTIFICATION("Escribir los productos de números y variables, como variables con coeficientes numéricos"),
    REARRANGE_COEFF_SUMMARY("Reescribir coeficiente"),

    REDUCE_EXPONENT_BY_ZERO_OPTION("Reducir a 1 el término elevado a la 0"),
    REDUCE_EXPONENT_BY_ZERO_JUSTIFICATION("Un término con números o letras, que se encuentra elevado a la 0, equivale a 1"),
    REDUCE_EXPONENT_BY_ZERO_SUMMARY("Reducir término"),

    REDUCE_ZERO_NUMERATOR_OPTION("Reducir a 0 la fracción con numerador igual a 0"),
    REDUCE_ZERO_NUMERATOR_JUSTIFICATION("0 multiplicado o dividido por cualquier término, equivale a 0"),
    REDUCE_ZERO_NUMERATOR_SUMMARY("Reducir término"),

    REMOVE_ADDING_ZERO_OPTION("Simplificar suma o resta con término igual a 0"),
    REMOVE_ADDING_ZERO_JUSTIFICATION("Todo término al que se le suma o resta 0, equivale al mismo término"),
    REMOVE_ADDING_ZERO_SUMMARY("Resolver operación"),

    REMOVE_EXPONENT_BY_ONE_OPTION("Eliminar potencia con exponente igual a 1"),
    REMOVE_EXPONENT_BY_ONE_JUSTIFICATION("Todo término elevado a 1, equivale al mismo término"),
    REMOVE_EXPONENT_BY_ONE_SUMMARY("Resolver operación"),

    REMOVE_EXPONENT_BASE_ONE_OPTION("Eliminar potencia con base iguale a "),
    REMOVE_EXPONENT_BASE_ONE_JUSTIFICATION("Todo término con base igual a 1, elevado a cualquier potencia, equivale a 1"),
    REMOVE_EXPONENT_BASE_ONE_SUMMARY("Reducir término"),

    RESOLVE_DOUBLE_MINUS_OPTION("Reescribir secuencia de dos signos (-), como un signo (+)"),
    RESOLVE_DOUBLE_MINUS_JUSTIFICATION("Por regla de los signos, dos \"menos\" equivalen a un \"más\". Por ejemplo: 2 - - 3 equivale a 2 + 3"),
    RESOLVE_DOUBLE_MINUS_SUMMARY("Simplificar operadores"),

    COLLECT_AND_COMBINE_LIKE_TERMS_OPTION("Agrupar términos numéricos entre sí, y términos con variables entre sí"),
    COLLECT_AND_COMBINE_LIKE_TERMS_JUSTIFICATION("En una expresión con sumas y restas de números y variables, se pueden agrupar los números por un lado, y las variables por otro, para simplificar la expresión"),
    COLLECT_AND_COMBINE_LIKE_TERMS_SUMMARY("Agrupar términos");

    private String justification;

    Justification(String justification) {
        this.justification = justification;
    }

    public String getJustification() {
        return justification;
    }
}
