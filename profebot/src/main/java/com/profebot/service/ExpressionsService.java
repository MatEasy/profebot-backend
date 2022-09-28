package com.profebot.service;

public class ExpressionsService {
    public static String removeDecimals(String expression){
        return expression.trim()
                .replaceAll("\\.0\\+", "+")
                .replaceAll("\\.0\\-", "-")
                .replaceAll("\\.0\\/", "/")
                .replaceAll("\\.0\\*", "*")
                .replaceAll("\\.0\\^", "^")
                .replaceAll("\\.0\\(", "(")
                .replaceAll("\\.0\\)", ")")
                .replaceAll("\\.0x", "x")
                .replaceAll("\\.0</b>x", "</b>x")
                .replaceAll("\\.0 ", " ")
                .replaceAll("\\.0\\}", "}")
                .replaceAll("\\.0\\]", "]")
                .replaceAll("\\.0\\,", ",")
                .replaceAll("\\.0\\.", ".")
                .replaceAll("\\.0$", "");
    }

    public static String getRootOfEquation(String infixEquation){
        if(infixEquation.contains("<=")){
            return "<=";
        }else if(infixEquation.contains(">=")){
            return ">=";
        }else if(infixEquation.contains("<")){
            return "<";
        }else if(infixEquation.contains(">")){
            return ">";
        }else if(infixEquation.contains("!=")){
            return "!=";
        }

        return "=";
    }

    public static String getIntervalFrom(String equation){
        String comparatorOperator = getRootOfEquation(equation);
        String[] members = equation.split(comparatorOperator);
        Double ordenadaAlOrigen = Double.parseDouble(members[1]) * -1;
        Boolean ordenadaAlOrigenEsPositiva = ordenadaAlOrigen > 0;
        // Si el miembro izquierdo contiene X (pero no es X) --> no se pudo resolver la cuadrática --> no hay raíces
        if(members[0].toUpperCase().contains("X") && !members[0].toUpperCase().equals("X")){
            if(ordenadaAlOrigenEsPositiva){
                if(">".equals(comparatorOperator) || ">=".equals(comparatorOperator)){
                    return "(-∞, ∞)";
                }else{
                    return "";
                }
            }

            // Ordenada al origen es negativa
            if(">".equals(comparatorOperator) || ">=".equals(comparatorOperator)){
                return "";
            }
            return "(-∞, ∞)";
        }

        String result = "";
        switch (comparatorOperator){
            case ">":
                result = "(AUX, +∞)";
                break;
            case ">=":
                result = "[AUX, +∞)";
                break;
            case "<":
                result = "(-∞, AUX)";
                break;
            case "<=":
                result = "(-∞, AUX]";
                break;
        }

        return removeDecimals(result.replace("AUX", "" + (-1 * ordenadaAlOrigen)));
    }

}
