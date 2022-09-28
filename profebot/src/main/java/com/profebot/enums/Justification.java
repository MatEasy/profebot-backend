package com.profebot.enums;

public enum Justification {
    SIMPLIFY_ARITHMETIC_OPTION("Simplificar operación aritmética:"),
    SIMPLIFY_ARITHMETIC_JUSTIFICATION("Para simplificar términos, resolver las sumas, restas, multiplicaciones, divisiones, potencias o raíces, que involucren solo números."),
    SIMPLIFY_ARITHMETIC_SUMMARY("Resolver operación"),

    DIVISION_BY_NEGATIVE_ONE_OPTION("Simplificar multiplicación o división por -1:"),
    DIVISION_BY_NEGATIVE_ONE_JUSTIFICATION("Cambiar los signos de los términos que estén afectados por una división o multiplicación por -1."),
    DIVISION_BY_NEGATIVE_ONE_SUMMARY("Resolver operación"),

    DIVISION_BY_ONE_OPTION("Simplificar multiplicación o división por 1:"),
    DIVISION_BY_ONE_JUSTIFICATION("Eliminar las multiplicaciones y divisiones por 1, que no modifican a los términos que afectan."),
    DIVISION_BY_ONE_SUMMARY("Reducir término"),

    MULTIPLY_BY_ZERO_OPTION("Simplificar multiplicación por 0:"),
    MULTIPLY_BY_ZERO_JUSTIFICATION("Eliminar los términos que estén siendo multiplicados por 0."),
    MULTIPLY_BY_ZERO_SUMMARY("Reducir término"),

    REARRANGE_COEFF_OPTION("Reescribir producto de números y variables, como variables con coeficientes:"),
    REARRANGE_COEFF_JUSTIFICATION("Escribir los productos de números y variables, como variables con coeficientes numéricos."),
    REARRANGE_COEFF_SUMMARY("Reescribir coeficiente"),

    REDUCE_EXPONENT_BY_ZERO_OPTION("Reducir a 1 el término elevado a la 0:"),
    REDUCE_EXPONENT_BY_ZERO_JUSTIFICATION("Un término con números o letras, que se encuentra elevado a la 0, equivale a 1."),
    REDUCE_EXPONENT_BY_ZERO_SUMMARY("Reducir término"),

    REDUCE_ZERO_NUMERATOR_OPTION("Reducir a 0 la fracción con numerador igual a 0:"),
    REDUCE_ZERO_NUMERATOR_JUSTIFICATION("0 multiplicado o dividido por cualquier término, equivale a 0."),
    REDUCE_ZERO_NUMERATOR_SUMMARY("Reducir término"),

    REMOVE_ADDING_ZERO_OPTION("Simplificar suma o resta con término igual a 0:"),
    REMOVE_ADDING_ZERO_JUSTIFICATION("Todo término al que se le suma o resta 0, equivale al mismo término."),
    REMOVE_ADDING_ZERO_SUMMARY("Resolver operación"),

    REMOVE_EXPONENT_BY_ONE_OPTION("Eliminar potencia con exponente igual a :"),
    REMOVE_EXPONENT_BY_ONE_JUSTIFICATION("Todo término elevado a 1, equivale al mismo término."),
    REMOVE_EXPONENT_BY_ONE_SUMMARY("Resolver operación"),

    REMOVE_EXPONENT_BASE_ONE_OPTION("Eliminar potencia con base iguale a: "),
    REMOVE_EXPONENT_BASE_ONE_JUSTIFICATION("Todo término con base igual a 1, elevado a cualquier potencia, equivale a 1."),
    REMOVE_EXPONENT_BASE_ONE_SUMMARY("Reducir término"),

    RESOLVE_DOUBLE_MINUS_OPTION("Reescribir secuencia de dos signos (-), como un signo (+):"),
    RESOLVE_DOUBLE_MINUS_JUSTIFICATION("Por regla de los signos, dos 'menos' equivalen a un 'más'. Por ejemplo: 2 - - 3 equivale a 2 + 3."),
    RESOLVE_DOUBLE_MINUS_SUMMARY("Simplificar operadores"),

    COLLECT_AND_COMBINE_LIKE_TERMS_OPTION("Agrupar términos numéricos entre sí, y términos con variables entre sí:"),
    COLLECT_AND_COMBINE_LIKE_TERMS_JUSTIFICATION("En una expresión con sumas y restas de números y variables, se pueden agrupar los números por un lado, y las variables por otro, para simplificar la expresión."),
    COLLECT_AND_COMBINE_LIKE_TERMS_SUMMARY("Agrupar términos"),

    COLLECT_CONSTANT_EXPONENTS_OPTION("Reducir la multiplicación de términos de igual base:"),
    COLLECT_CONSTANT_EXPONENTS_JUSTIFICATION("Producto de potencias de igual base, se suman los exponentes."),
    COLLECT_CONSTANT_EXPONENTS_SUMMARY("Simplificar términos"),

    ADD_EXPONENT_OF_ONE_OPTION("Reescribir las variables como bases elevadas a un exponente:"),
    ADD_EXPONENT_OF_ONE_JUSTIFICATION("Toda expresión está elevada a un exponente (que de no aparecer, vale 1)."),
    ADD_EXPONENT_OF_ONE_SUMMARY("Reescribir exponentes"),

    MULTIPLY_POLYNOMIAL_TERMS_OPTION("Reescribir el producto de variables, como una variable elevada a una potencia:"),
    MULTIPLY_POLYNOMIAL_TERMS_JUSTIFICATION("Una variable multiplicada por si misma 2 o más veces, equivale a escribir dicha variable, elevada a un exponente igual a la cantidad de veces que estaba multiplicada."),
    MULTIPLY_POLYNOMIAL_TERMS_SUMMARY("Reescribir producto de varaibles"),

    BREAK_UP_FRACTION_OPTION("Distribuir la división a los distintos términos del paréntesis:"),
    BREAK_UP_FRACTION_JUSTIFICATION("Así como en la multiplicación, la división se puede también distribuir a cada uno de los términos de una suma o resta, multiplicando a cada término por la fracción correspondiente."),
    BREAK_UP_FRACTION_SUMMARY("Distribuir fracción"),

    CANCEL_MINUSES_OPTION("Cancelar los signos 'menos':"),
    CANCEL_MINUSES_JUSTIFICATION("Por regla de los signos, 2 'menos' multiplicados o divididos equivalen a un signo 'más'. Por ejemplo: -1/-3 equivale a 1/3."),
    CANCEL_MINUSES_SUMMARY("Simplificar signos"),

    CANCEL_TERMS_OPTION("Cancelar términos debido a una división:"),
    CANCEL_TERMS_JUSTIFICATION("2 términos iguales divididos entre sí, pueden ser cancelados. De esta forma, una fracción cuyo numerador es igual al denominador, equivale a 1. Por ejemplo: 2/2 equivale a 1."),
    CANCEL_TERMS_SUMMARY("Simplificar fracción"),

        SIMPLIFY_FRACTION_OPTION("Simplificar fracción numérica:"),
    SIMPLIFY_FRACTION_JUSTIFICATION("Una fracción de números se puede simplificar en la medida en la que existe algun divisor en común tanto para el numerador como para el denominador."),
    SIMPLIFY_FRACTION_SUMMARY("Simplificar fracción"),

    SIMPLIFY_SIGNS_OPTION("Reescribir signos en fracción:"),
    SIMPLIFY_SIGNS_JUSTIFICATION("Por regla de los signos, cuando en una fracción, el numerador es positivo y el denominador es negativo, se puede ubicar el signo negativo en el numerador, para simplificar la escritura de la expresión."),
    SIMPLIFY_SIGNS_SUMMARY("Reescribir fracción"),

    ADD_FRACTIONS_OPTION("Resolver la suma o resta de fracciones:"),
    ADD_FRACTIONS_JUSTIFICATION("2 fracciones numéricas se pueden sumar o restar, buscando denominador común (si sus denominadores son diferentes) o unificando sus numeradores (si sus denominadores son iguales)."),
    ADD_FRACTIONS_SUMMARY("Resolver operación"),

    COMMON_DENOMINATOR_OPTION("Buscar común denominador:"),
    COMMON_DENOMINATOR_JUSTIFICATION("2 o más fracciones que se están sumando o restando, y que tienen denominadores diferentes se pueden unificar (sumar o restar), buscando un denominador en común. El mismo se obtiene encontrando el mínimo común múltiplo (mcm) entre los denominadores de cada fracción."),
    COMMON_DENOMINATOR_SUMMARY("Resolver operación"),

    CONVERT_INTEGER_TO_FRACTION_OPTION("Convertir número entero a fracción:"),
    CONVERT_INTEGER_TO_FRACTION_JUSTIFICATION("Para sumar o restar un número entero con una fracción, se puede convertir al entero en una fracción, multiplicándolo y dividiéndolo por un mismo valor que resulte conveniente."),
    CONVERT_INTEGER_TO_FRACTION_SUMMARY("Resolver operación"),

    DIVIDE_FRACTION_FOR_ADDITION_OPTION("Convertir fracción a número decimal:"),
    DIVIDE_FRACTION_FOR_ADDITION_JUSTIFICATION("Para operar con fracciones y números decimales (con coma), se puede convertir dichas fracciones en números decimales, efectuando las divisiones de los numeradores y sus denominadores respectivos."),
    DIVIDE_FRACTION_FOR_ADDITION_SUMMARY("Resolver operación"),

    MULTIPLY_DENOMINATORS_OPTION("Unificar denominadores de fracciones, para realizar una suma o resta:"),
    MULTIPLY_DENOMINATORS_JUSTIFICATION("Si en una suma o resta de fracciones, los denominadores son iguales pero se encuetran factorizados (expresados como un producto de factores), se deben realizar las multiplicaciones en cada denominador para luego, operar los numeradores."),
    MULTIPLY_DENOMINATORS_SUMMARY("Resolver operación"),

    MULTIPLY_NUMERATORS_OPTION("Unificar numeradores de fracciones, para realizar una suma o resta:"),
    MULTIPLY_NUMERATORS_JUSTIFICATION("Si en una suma o resta de fracciones, los numeradores son iguales pero se encuetran factorizados (expresados como un producto de factores), se deben realizar las multiplicaciones en cada numerador para luego, buscar denominador común."),
    MULTIPLY_NUMERATORS_SUMMARY("Resolver operación"),

    MULTIPLY_FRACTIONS_OPTION("Multiplicar fracciones numéricas:"),
    MULTIPLY_FRACTIONS_JUSTIFICATION("El resultado de multiplicar 2 fracciones, será una nueva fracción, cuyo numerador será el producto de los numeradores de las fracciones que se multiplican, y el denominador será el producto de los denominadores."),
    MULTIPLY_FRACTIONS_SUMMARY("Resolver operación"),

    SIMPLIFY_DIVISION_OPTION("Simplificar divisiones sucesivas:"),
    SIMPLIFY_DIVISION_JUSTIFICATION("Una cadena de divisiones se puede simplificar generando una fracción en la que se expresen todos los cocientes como un denominador con multiplicaciones."),
    SIMPLIFY_DIVISION_SUMMARY("Simplificar fracción"),

    MULTIPLY_BY_INVERSE_OPTION("Multiplicar por el inverso de la fracción que está dividiendo:"),
    MULTIPLY_BY_INVERSE_JUSTIFICATION("Un término está siendo dividido por una fracción, es equivalente a multiplicar dicho término por la inversa de la fracción. Por ejemplo: 2 / (2/3) equivale a decir 2 * 3/2."),
    MULTIPLY_BY_INVERSE_SUMMARY("Simplificar operadores"),

    DISTRIBUTE_OPTION("Aplicar distributiva:"),
    DISTRIBUTE_JUSTIFICATION("Una expresión que se encuentra multiplicando o dividiendo a un paréntesis que encierra una suma o resta de términos, se puede distribuir, de manera que se genere una suma o resta de términos multiplicados o divididos por dicha expresión. Por ejemplo: si se tiene 2*(1+3), el 2 se puede distribuir a los términos 1 y 3, de manera que se genere una nueva suma con la forma: 2*1 + 2*3."),
    DISTRIBUTE_SUMMARY("Aplicar distributiva"),

    EXPAND_EXPONENT_OPTION("Expresar término elevado a una potencia, como una multiplicación con sigo mismo tantas veces como indique la potencia:"),
    EXPAND_EXPONENT_JUSTIFICATION("Una expresión que se encuentra elevada a un exponente, puede reescribirse como la misma expresión, multiplicada por si misma, tantas veces como indica la potencia."),
    EXPAND_EXPONENT_SUMMARY("Expandir potencia"),

    ABSOLUTE_VALUE_OPTION("Resolver el módulo (valor absoluto):"),
    ABSOLUTE_VALUE_JUSTIFICATION("El valor absoluto o módulo de un término equivale a tomar dicho término, con signo positivo, independientemente del signo que tenga. Por ejemplo, el valor absoluto de 2 es 2, y de -2 también es 2."),
    ABSOLUTE_VALUE_SUMMARY("Aplicar valor absoluto"),

    CANCEL_EXPONENT_OPTION("Simplificar potencias e índices de raíces:"),
    CANCEL_EXPONENT_JUSTIFICATION("Cuando una expresión está dentro de una raíz y elevada a un exponente, se puede simplificar dicho exponente con el índice de la raíz, si existiese algún divisor en común entre ambos valores."),
    CANCEL_EXPONENT_SUMMARY("Simplificar términos"),

    COMBINE_UNDER_ROOT_OPTION("Unificar 2 o más raíces en una, multiplicando sus índices:"),
    COMBINE_UNDER_ROOT_JUSTIFICATION("Una multiplicación de 2 o más raíces sobre una misma expresión (radicando) se puede simplificar en una única raíz, cuyo índice resultará del producto de los índices de las raíces iniciales."),
    COMBINE_UNDER_ROOT_SUMMARY("Unificar raíces"),

    CONVERT_MULTIPLICATION_TO_EXPONENT_OPTION("Unificar término multiplicado por si mismo 2 o más veces, en dicho término elevado a una potencia:"),
    CONVERT_MULTIPLICATION_TO_EXPONENT_JUSTIFICATION("Un término que se encuentra multiplicado por si mismo 2 o más veces, equivale al mismo término elevado a un exponente, que representa la cantidad de veces por las que el término está multiplicado por si mismo."),
    CONVERT_MULTIPLICATION_TO_EXPONENT_SUMMARY("Unificar productos"),

    DISTRIBUTE_NTH_ROOT_OPTION("Distribuir raíz a su radicando:"),
    DISTRIBUTE_NTH_ROOT_JUSTIFICATION("Si una raíz tiene en su radicando una multiplicación de 2 o más términos, la misma se puede distribuir, de manera que la raiz de la multiplicación, quede como la multiplicación de las raíces."),
    DISTRIBUTE_NTH_ROOT_SUMMARY("Distribuir raíz"),

    FACTOR_INTO_PRIMES_OPTION("Factorear número:"),
    FACTOR_INTO_PRIMES_JUSTIFICATION("Factorear un número implica expresarlo como un producto de factores primos."),
    FACTOR_INTO_PRIMES_SUMMARY("Factorear número"),

    ADD_NTH_ROOTS_OPTION("Sumar raíces:"),
    ADD_NTH_ROOTS_JUSTIFICATION("2 o más raíces con mismo índice y mismo radicando, que se están sumando o restando, se pueden unificar, tratandolas como si fueran variables. De esta forma, la raíz cuadrada de 2 más la raíz cuadrada de 2, resulta en 2 por la raíz cuadrada de 2."),
    ADD_NTH_ROOTS_SUMMARY("Sumar raíces"),

    MULTIPLY_NTH_ROOTS_OPTION("Multiplicar raíces:"),
    MULTIPLY_NTH_ROOTS_JUSTIFICATION("2 o más raíces con mismo índice y mismo radicando, que se están multiplicando o dividiendo, se pueden unificar, tratandolas como si fueran variables. De esta forma, la raíz cuadrada de 2 por la raíz cuadrada de 2, resulta en la raíz cuarta de 2."),
    MULTIPLY_NTH_ROOTS_SUMMARY("Multiplicar raíces"),

    ADD_TO_BOTH_SIDES_OPTION("Sumar a ambos miembros para cancelar términos:"),
    ADD_TO_BOTH_SIDES_JUSTIFICATION("Para cancelar un término que está restando de un lado del /comparador/, se puede sumar miembro a miembro dicho término; de esta forma, se logrará 'pasar' dicho término de un lado del /comparador/ al otro, con el objetivo de despejar la X."),
    ADD_TO_BOTH_SIDES_SUMMARY("Sumar miembro a miembro"),

    DIVIDE_FROM_BOTH_SIDES_OPTION("Dividir a ambos miembros para cancelar términos:"),
    DIVIDE_FROM_BOTH_SIDES_JUSTIFICATION("Para cancelar un término que está multiplicando de un lado del /comparador/, se puede dividir miembro a miembro por dicho término; de esta forma, se logrará 'pasar' dicho término de un lado del /comparador/ al otro, con el objetivo de despejar la X."),
    DIVIDE_FROM_BOTH_SIDES_SUMMARY("Dividir miembro a miembro"),

    MULTIPLY_BOTH_SIDES_BY_INVERSE_FRACTION_OPTION("Multiplicar a ambos miembros para cancelar términos:"),
    MULTIPLY_BOTH_SIDES_BY_INVERSE_FRACTION_JUSTIFICATION("Para cancelar un término que está dividiendo de un lado del /comparador/, se puede multiplicar miembro a miembro por dicho término; de esta forma, se logrará 'pasar' dicho término de un lado del /comparador/ al otro, con el objetivo de despejar la X."),
    MULTIPLY_BOTH_SIDES_BY_INVERSE_FRACTION_SUMMARY("Multiplicar miembro a miembro"),

    SIMPLIFY_LEFT_SIDE_OPTION("Simplificar el lado izquierdo del /comparador/:"),
    SIMPLIFY_LEFT_SIDE_JUSTIFICATION("Simplificar el lado izquierdo del /comparador/ para despejar la X."),
    SIMPLIFY_LEFT_SIDE_SUMMARY("Simplificar términos"),

    SIMPLIFY_RIGHT_SIDE_OPTION("Simplificar el lado derecho del /comparador/:"),
    SIMPLIFY_RIGHT_SIDE_JUSTIFICATION("Simplificar el lado derecho del /comparador/ para despejar la X."),
    SIMPLIFY_RIGHT_SIDE_SUMMARY("Simplificar términos"),

    SUBTRACT_FROM_BOTH_SIDES_OPTION("Simplificar ambos miembros del /comparador/:"),
    SUBTRACT_FROM_BOTH_SIDES_JUSTIFICATION("Simplificar ambos miembros del /comparador/ para despejar la X."),
    SUBTRACT_FROM_BOTH_SIDES_SUMMARY("Simplificar términos"),

    FIND_ROOTS_OPTION("Extraer raíces del polinomio factorizado:"),
    FIND_ROOTS_JUSTIFICATION("Un polinomio factorizado permite extraer sus raíces, tomando los números de cada binomio cambiado de signo. Estos valores son los que reemplazados en la X, si se resuelven las operaciones aritméticas se llega a 0."),
    FIND_ROOTS_SUMMARY("Extraer raíces"),

    STATEMENT_IS_TRUE_OPTION("Existen infinitas soluciones para X:"),
    STATEMENT_IS_TRUE_JUSTIFICATION("Cuando los dos miembros del /comparador/ son iguales y numéricos, se dice que existen infinitas soluciones, porque para cualquier valor de X, el /comparador/ se cumple."),
    STATEMENT_IS_TRUE_SUMMARY("Infinitas soluciones"),

    STATEMENT_IS_FALSE_OPTION("No existen soluciones para X:"),
    STATEMENT_IS_FALSE_JUSTIFICATION("Cuando los dos miembros del /comparador/ son distintos y numéricos, se dice que no existen soluciones, porque para cualquier valor de X, el /comparador/ nunca se cumple."),
    STATEMENT_IS_FALSE_SUMMARY("Ninguna solución"),

    FACTOR_SYMBOL_OPTION("Extraer factor común:"),
    FACTOR_SYMBOL_JUSTIFICATION("Se puede extraer factor común de 'letra' o 'número', en la medida que haya una suma o resta de términos, con operandos repetidos. Por ejemplo: x+3x puede ser factoreado por 'x', quedando x(3+1)."),
    FACTOR_SYMBOL_SUMMARY("Factor común"),

    FACTOR_DIFFERENCE_OF_SQUARES_OPTION("Aplicar diferencia de cuadrados:"),
    FACTOR_DIFFERENCE_OF_SQUARES_JUSTIFICATION("Se puede factorear por diferencia de cuadrados, cuando se tiene una resta, en donde ambos términos pueden convertirse en otras expresiones elevadas al cuadrado. Por ejemplo, 9 puede ser reescrito como 3 al cuadrado."),
    FACTOR_DIFFERENCE_OF_SQUARES_SUMMARY("Diferencia de cuadrados"),

    FACTOR_PERFECT_SQUARE_OPTION("Aplicar trinomio cuadrado perfecto:"),
    FACTOR_PERFECT_SQUARE_JUSTIFICATION("Factorear por trinomio cuadrado perfecto implica convertir un trinomio, en un binomio al cuadrado. Esto se puede lograr, cuando en el trinomio (3 términos sumados o restados) existen 2 que pueden reescribirse como una expresión al cuadrado, y un tercero que resulta del doble producto de las expresiones elevadas al cuadrado (sin el cuadrado)."),
    FACTOR_PERFECT_SQUARE_SUMMARY("Trinomio cuadrado perfecto"),

    FACTOR_SUM_PRODUCT_RULE_OPTION("Aplicar fórmula resolvente:"),
    FACTOR_SUM_PRODUCT_RULE_JUSTIFICATION("Factorear por fórmula resolvente es posible cuando el polinomio es cuadrático. La fórmula permitirá extraer las 2 raíces del polinomio, el cual podrá ser reescrito en forma factorizada como (x - raíz1) * (x - raíz2):"),
    FACTOR_SUM_PRODUCT_RULE_SUMMARY("Fómula resolvente");

    private String justification;

    Justification(String justification) {
        this.justification = justification;
    }

    public String getJustification() {
        return justification;
    }
}
