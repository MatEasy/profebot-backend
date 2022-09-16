package com.profebot.service;

import com.profebot.structures.resolutorStructures.NodeStatus;

import java.util.HashMap;
import java.util.Map;

import static com.profebot.enums.Justification.*;
import static com.profebot.structures.resolutorStructures.NodeStatus.ChangeTypes.*;

public class JustificationsService {

    public static Map<String, String> getCorrectJustificationsFrom(NodeStatus.ChangeTypes source){
        Map<String, String> justifications = new HashMap<>();

        if(source.getDescrip().equals(SIMPLIFY_ARITHMETIC.getDescrip())){
                return createTextsFrom(SIMPLIFY_ARITHMETIC_OPTION.getJustification(),
                    SIMPLIFY_ARITHMETIC_JUSTIFICATION.getJustification(),
                    SIMPLIFY_ARITHMETIC_SUMMARY.getJustification());
        }

        if(source.getDescrip().equals(DIVISION_BY_NEGATIVE_ONE.getDescrip())){
            return createTextsFrom(DIVISION_BY_NEGATIVE_ONE_OPTION.getJustification(),
                    DIVISION_BY_NEGATIVE_ONE_JUSTIFICATION.getJustification(),
                    DIVISION_BY_NEGATIVE_ONE_SUMMARY.getJustification());
        }

        if(source.getDescrip().equals(DIVISION_BY_NEGATIVE_ONE.getDescrip())){
            return createTextsFrom(DIVISION_BY_ONE_OPTION.getJustification(),
                    DIVISION_BY_ONE_JUSTIFICATION.getJustification(),
                    DIVISION_BY_ONE_SUMMARY.getJustification());
        }

        if(source.getDescrip().equals(MULTIPLY_BY_ZERO.getDescrip())){
            return createTextsFrom(
                    MULTIPLY_BY_ZERO_OPTION.getJustification(),
                    MULTIPLY_BY_ZERO_JUSTIFICATION.getJustification(),
                    MULTIPLY_BY_ZERO_SUMMARY.getJustification());
        }

        if(source.getDescrip().equals(REARRANGE_COEFF.getDescrip())
                || source.getDescrip().equals(REMOVE_MULTIPLYING_BY_NEGATIVE_ONE.getDescrip())
                || source.getDescrip().equals(REMOVE_MULTIPLYING_BY_ONE.getDescrip())
                || source.getDescrip().equals(ADD_COEFFICIENT_OF_ONE.getDescrip())
                || source.getDescrip().equals(UNARY_MINUS_TO_NEGATIVE_ONE.getDescrip())){
            return createTextsFrom(
                    REARRANGE_COEFF_OPTION.getJustification(),
                    REARRANGE_COEFF_JUSTIFICATION.getJustification(),
                    REARRANGE_COEFF_SUMMARY.getJustification());
        }

        if(source.getDescrip().equals(REDUCE_EXPONENT_BY_ZERO.getDescrip())){
            return createTextsFrom(
                    REDUCE_EXPONENT_BY_ZERO_OPTION.getJustification(),
                    REDUCE_EXPONENT_BY_ZERO_JUSTIFICATION.getJustification(),
                    REDUCE_EXPONENT_BY_ZERO_SUMMARY.getJustification());
        }

        if(source.getDescrip().equals(REDUCE_ZERO_NUMERATOR.getDescrip())){
            return createTextsFrom(
                    REDUCE_ZERO_NUMERATOR_OPTION.getJustification(),
                    REDUCE_ZERO_NUMERATOR_JUSTIFICATION.getJustification(),
                    REDUCE_ZERO_NUMERATOR_SUMMARY.getJustification());
        }

        if(source.getDescrip().equals(REMOVE_ADDING_ZERO.getDescrip())){
            return createTextsFrom(
                    REMOVE_ADDING_ZERO_OPTION.getJustification(),
                    REMOVE_ADDING_ZERO_JUSTIFICATION.getJustification(),
                    REMOVE_ADDING_ZERO_SUMMARY.getJustification());
        }

        if(source.getDescrip().equals(REMOVE_EXPONENT_BY_ONE.getDescrip())){
            return createTextsFrom(
                    REMOVE_EXPONENT_BY_ONE_OPTION.getJustification(),
                    REMOVE_EXPONENT_BY_ONE_JUSTIFICATION.getJustification(),
                    REMOVE_EXPONENT_BY_ONE_SUMMARY.getJustification());
        }

        if(source.getDescrip().equals(REMOVE_EXPONENT_BASE_ONE.getDescrip())){
            return createTextsFrom(
                    REMOVE_EXPONENT_BASE_ONE_OPTION.getJustification(),
                    REMOVE_EXPONENT_BASE_ONE_JUSTIFICATION.getJustification(),
                    REMOVE_EXPONENT_BASE_ONE_SUMMARY.getJustification());
        }

        if(source.getDescrip().equals(RESOLVE_DOUBLE_MINUS.getDescrip())){
            return createTextsFrom(
                    RESOLVE_DOUBLE_MINUS_OPTION.getJustification(),
                    RESOLVE_DOUBLE_MINUS_JUSTIFICATION.getJustification(),
                    RESOLVE_DOUBLE_MINUS_SUMMARY.getJustification());
        }

        if(source.getDescrip().equals(COLLECT_AND_COMBINE_LIKE_TERMS.getDescrip())
                || source.getDescrip().equals(COLLECT_LIKE_TERMS.getDescrip())
                || source.getDescrip().equals(ADD_POLYNOMIAL_TERMS.getDescrip())
                || source.getDescrip().equals(GROUP_COEFFICIENTS.getDescrip())
                || source.getDescrip().equals(MULTIPLY_COEFFICIENTS.getDescrip())
                || source.getDescrip().equals(SIMPLIFY_TERMS.getDescrip())){
            return createTextsFrom(
                    COLLECT_AND_COMBINE_LIKE_TERMS_OPTION.getJustification(),
                    COLLECT_AND_COMBINE_LIKE_TERMS_JUSTIFICATION.getJustification(),
                    COLLECT_AND_COMBINE_LIKE_TERMS_SUMMARY.getJustification());
        }

      /*  if(source.getDescrip().equals(COLLECT_CONSTANT_EXPONENTS.getDescrip())
                || source.getDescrip().equals(COLLECT_POLYNOMIAL_EXPONENTS.getDescrip())){
            return createTextsFrom(
                    COLLECT_CONSTANT_EXPONENTS_OPTION.getJustification(),
                    COLLECT_CONSTANT_EXPONENTS_JUSTIFICATION.getJustification(),
                    COLLECT_CONSTANT_EXPONENTS_SUMMARY.getJustification());
        }

        if(source.getDescrip().equals(ADD_EXPONENT_OF_ONE.getDescrip())){
            return createTextsFrom(
                    ADD_EXPONENT_OF_ONE_OPTION.getJustification(),
                    ADD_EXPONENT_OF_ONE_JUSTIFICATION.getJustification(),
                    ADD_EXPONENT_OF_ONE_SUMMARY.getJustification());
        }

        if(source.getDescrip().equals(MULTIPLY_POLYNOMIAL_TERMS.getDescrip())){
            return createTextsFrom(
                    MULTIPLY_POLYNOMIAL_TERMS_OPTION.getJustification(),
                    MULTIPLY_POLYNOMIAL_TERMS_JUSTIFICATION.getJustification(),
                    MULTIPLY_POLYNOMIAL_TERMS_SUMMARY.getJustification());
        }

        if(source.getDescrip().equals(BREAK_UP_FRACTION.getDescrip())){
            return createTextsFrom(
                    BREAK_UP_FRACTION_OPTION.getJustification(),
                    BREAK_UP_FRACTION_JUSTIFICATION.getJustification(),
                    BREAK_UP_FRACTION_SUMMARY.getJustification());
        }

        if(source.getDescrip().equals(CANCEL_MINUSES.getDescrip())){
            return createTextsFrom(
                    CANCEL_MINUSES_OPTION.getJustification(),
                    CANCEL_MINUSES_JUSTIFICATION.getJustification(),
                    CANCEL_MINUSES_SUMMARY.getJustification());
        }

        if(source.getDescrip().equals(CANCEL_TERMS.getDescrip())){
            return createTextsFrom(
                    CANCEL_TERMS_OPTION.getJustification(),
                    CANCEL_TERMS_JUSTIFICATION.getJustification(),
                    CANCEL_TERMS_SUMMARY.getJustification());
        }

        if(source.getDescrip().equals(SIMPLIFY_FRACTION.getDescrip())){
            return createTextsFrom(
                    SIMPLIFY_FRACTION_OPTION.getJustification(),
                    SIMPLIFY_FRACTION_JUSTIFICATION.getJustification(),
                    SIMPLIFY_FRACTION_SUMMARY.getJustification());
        }

        if(source.getDescrip().equals(SIMPLIFY_SIGNS.getDescrip())){
            return createTextsFrom(
                    SIMPLIFY_SIGNS_OPTION.getJustification(),
                    SIMPLIFY_SIGNS_JUSTIFICATION.getJustification(),
                    SIMPLIFY_SIGNS_SUMMARY.getJustification());
        }

        if(source.getDescrip().equals(ADD_FRACTIONS.getDescrip())
                || source.getDescrip().equals(ADD_NUMERATORS.getDescrip())
                || source.getDescrip().equals(COMBINE_NUMERATORS.getDescrip())){
            return createTextsFrom(
                    ADD_FRACTIONS_OPTION.getJustification(),
                    ADD_FRACTIONS_JUSTIFICATION.getJustification(),
                    ADD_FRACTIONS_SUMMARY.getJustification());
        }

        if(source.getDescrip().equals(COMMON_DENOMINATOR.getDescrip())){
            return createTextsFrom(
                    COMMON_DENOMINATOR_OPTION.getJustification(),
                    COMMON_DENOMINATOR_JUSTIFICATION.getJustification(),
                    COMMON_DENOMINATOR_SUMMARY.getJustification());
        }

        if(source.getDescrip().equals(CONVERT_INTEGER_TO_FRACTION.getDescrip())){
            return createTextsFrom(
                    CONVERT_INTEGER_TO_FRACTION_OPTION.getJustification(),
                    CONVERT_INTEGER_TO_FRACTION_JUSTIFICATION.getJustification(),
                    CONVERT_INTEGER_TO_FRACTION_SUMMARY.getJustification());
        }

        if(source.getDescrip().equals(DIVIDE_FRACTION_FOR_ADDITION.getDescrip())){
            return createTextsFrom(
                    DIVIDE_FRACTION_FOR_ADDITION_OPTION.getJustification(),
                    DIVIDE_FRACTION_FOR_ADDITION_JUSTIFICATION.getJustification(),
                    DIVIDE_FRACTION_FOR_ADDITION_SUMMARY.getJustification());
        }

        if(source.getDescrip().equals(MULTIPLY_DENOMINATORS.getDescrip())){
            return createTextsFrom(context,
                    MULTIPLY_DENOMINATORS_OPTION.getJustification(),
                    MULTIPLY_DENOMINATORS_JUSTIFICATION.getJustification(),
                    MULTIPLY_DENOMINATORS_SUMMARY.getJustification());
        }

        if(source.getDescrip().equals(MULTIPLY_NUMERATORS.getDescrip())){
            return createTextsFrom(
                    MULTIPLY_NUMERATORS_OPTION.getJustification(),
                    MULTIPLY_NUMERATORS_JUSTIFICATION.getJustification(),
                    MULTIPLY_NUMERATORS_SUMMARY.getJustification());
        }

        if(source.getDescrip().equals(MULTIPLY_FRACTIONS.getDescrip())){
            return createTextsFrom(
                    MULTIPLY_FRACTIONS_OPTION.getJustification(),
                    MULTIPLY_FRACTIONS_JUSTIFICATION.getJustification(),
                    MULTIPLY_FRACTIONS_SUMMARY.getJustification());
        }

        if(source.getDescrip().equals(SIMPLIFY_DIVISION.getDescrip())){
            return createTextsFrom(
                    SIMPLIFY_DIVISION_OPTION.getJustification(),
                    SIMPLIFY_DIVISION_JUSTIFICATION.getJustification(),
                    SIMPLIFY_DIVISION_SUMMARY.getJustification());
        }

        if(source.getDescrip().equals(MULTIPLY_BY_INVERSE.getDescrip())){
            return createTextsFrom(
                    MULTIPLY_BY_INVERSE_OPTION.getJustification(),
                    MULTIPLY_BY_INVERSE_JUSTIFICATION.getJustification(),
                    MULTIPLY_BY_INVERSE_SUMMARY.getJustification());
        }

        if(source.getDescrip().equals(DISTRIBUTE.getDescrip())
                || source.getDescrip().equals(DISTRIBUTE_NEGATIVE_ONE.getDescrip())){
            return createTextsFrom(
                    DISTRIBUTE_OPTION.getJustification(),
                    DISTRIBUTE_JUSTIFICATION.getJustification(),
                    DISTRIBUTE_SUMMARY.getJustification());
        }

        if(source.getDescrip().equals(EXPAND_EXPONENT.getDescrip())){
            return createTextsFrom(
                    EXPAND_EXPONENT_OPTION.getJustification(),
                    EXPAND_EXPONENT_JUSTIFICATION.getJustification(),
                    EXPAND_EXPONENT_SUMMARY.getJustification());
        }

        if(source.getDescrip().equals(ABSOLUTE_VALUE.getDescrip())){
            return createTextsFrom(
                    ABSOLUTE_VALUE_OPTION.getJustification(),
                    ABSOLUTE_VALUE_JUSTIFICATION.getJustification(),
                    ABSOLUTE_VALUE_SUMMARY.getJustification());
        }


        if(source.getDescrip().equals(CANCEL_EXPONENT.getDescrip())
                || source.getDescrip().equals(CANCEL_EXPONENT_AND_ROOT.getDescrip())
                || source.getDescrip().equals(CANCEL_ROOT.getDescrip())
                || source.getDescrip().equals(EVALUATE_DISTRIBUTED_NTH_ROOT.getDescrip())
                || source.getDescrip().equals(NTH_ROOT_VALUE.getDescrip())){
            return createTextsFrom(
                    CANCEL_EXPONENT_OPTION.getJustification(),
                    CANCEL_EXPONENT_JUSTIFICATION.getJustification(),
                    CANCEL_EXPONENT_SUMMARY.getJustification());
        }

        if(source.getDescrip().equals(COMBINE_UNDER_ROOT.getDescrip())){
            return createTextsFrom(
                    COMBINE_UNDER_ROOT_OPTION.getJustification(),
                    COMBINE_UNDER_ROOT_JUSTIFICATION.getJustification(),
                    COMBINE_UNDER_ROOT_SUMMARY.getJustification());
        }

        if(source.getDescrip().equals(CONVERT_MULTIPLICATION_TO_EXPONENT.getDescrip())){
            return createTextsFrom(
                    CONVERT_MULTIPLICATION_TO_EXPONENT_OPTION.getJustification(),
                    CONVERT_MULTIPLICATION_TO_EXPONENT_JUSTIFICATION.getJustification(),
                    CONVERT_MULTIPLICATION_TO_EXPONENT_SUMMARY.getJustification());
        }

        if(source.getDescrip().equals(DISTRIBUTE_NTH_ROOT.getDescrip())){
            return createTextsFrom(
                    DISTRIBUTE_NTH_ROOT_OPTION.getJustification(),
                    DISTRIBUTE_NTH_ROOT_JUSTIFICATION.getJustification(),
                    DISTRIBUTE_NTH_ROOT_SUMMARY.getJustification());
        }

        if(source.getDescrip().equals(FACTOR_INTO_PRIMES.getDescrip())){
            return createTextsFrom(
                    FACTOR_INTO_PRIMES_OPTION.getJustification(),
                    FACTOR_INTO_PRIMES_JUSTIFICATION.getJustification(),
                    FACTOR_INTO_PRIMES_SUMMARY.getJustification());
        }

        // TODO: GROUP_TERMS_BY_ROOT

        if(source.getDescrip().equals(ADD_NTH_ROOTS.getDescrip())){
            return createTextsFrom(
                    ADD_NTH_ROOTS_OPTION.getJustification(),
                    ADD_NTH_ROOTS_JUSTIFICATION.getJustification(),
                    ADD_NTH_ROOTS_SUMMARY.getJustification());
        }

        if(source.getDescrip().equals(MULTIPLY_NTH_ROOTS.getDescrip())){
            return createTextsFrom(
                    MULTIPLY_NTH_ROOTS_OPTION.getJustification(),
                    MULTIPLY_NTH_ROOTS_JUSTIFICATION.getJustification(),
                    MULTIPLY_NTH_ROOTS_SUMMARY.getJustification());
        }

        if(source.getDescrip().equals(ADD_TO_BOTH_SIDES.getDescrip())){
            return createTextsFrom(
                    ADD_TO_BOTH_SIDES_OPTION.getJustification(),
                    ADD_TO_BOTH_SIDES_JUSTIFICATION.getJustification(),
                    ADD_TO_BOTH_SIDES_SUMMARY.getJustification());
        }

        if(source.getDescrip().equals(DIVIDE_FROM_BOTH_SIDES.getDescrip())){
            return createTextsFrom(
                    DIVIDE_FROM_BOTH_SIDES_OPTION.getJustification(),
                    DIVIDE_FROM_BOTH_SIDES_JUSTIFICATION.getJustification(),
                    DIVIDE_FROM_BOTH_SIDES_SUMMARY.getJustification());
        }

        if(source.getDescrip().equals(MULTIPLY_BOTH_SIDES_BY_INVERSE_FRACTION.getDescrip())
                || source.getDescrip().equals(MULTIPLY_BOTH_SIDES_BY_NEGATIVE_ONE.getDescrip())
                || source.getDescrip().equals(MULTIPLY_TO_BOTH_SIDES.getDescrip())){
            return createTextsFrom(
                    MULTIPLY_BOTH_SIDES_BY_INVERSE_FRACTION_OPTION.getJustification(),
                    MULTIPLY_BOTH_SIDES_BY_INVERSE_FRACTION_JUSTIFICATION.getJustification(),
                    MULTIPLY_BOTH_SIDES_BY_INVERSE_FRACTION_SUMMARY.getJustification());
        }

        if(source.getDescrip().equals(SIMPLIFY_LEFT_SIDE.getDescrip())){
            return createTextsFrom(
                    SIMPLIFY_LEFT_SIDE_OPTION.getJustification(),
                    SIMPLIFY_LEFT_SIDE_JUSTIFICATION.getJustification(),
                    SIMPLIFY_LEFT_SIDE_SUMMARY.getJustification());
        }

        if(source.getDescrip().equals(SIMPLIFY_RIGHT_SIDE.getDescrip())){
            return createTextsFrom(
                    SIMPLIFY_RIGHT_SIDE_OPTION.getJustification(),
                    SIMPLIFY_RIGHT_SIDE_JUSTIFICATION.getJustification(),
                    SIMPLIFY_RIGHT_SIDE_SUMMARY.getJustification());
        }

        if(source.getDescrip().equals(SUBTRACT_FROM_BOTH_SIDES.getDescrip())){
            return createTextsFrom(
                    SUBTRACT_FROM_BOTH_SIDES_OPTION.getJustification(),
                    SUBTRACT_FROM_BOTH_SIDES_JUSTIFICATION.getJustification(),
                    SUBTRACT_FROM_BOTH_SIDES_SUMMARY.getJustification());
        }


        // TODO: SWAP_SIDES

        if(source.getDescrip().equals(FIND_ROOTS.getDescrip())){
            return createTextsFrom(
                    FIND_ROOTS_OPTION.getJustification(),
                    FIND_ROOTS_JUSTIFICATION.getJustification(),
                    FIND_ROOTS_SUMMARY.getJustification());
        }

        if(source.getDescrip().equals(STATEMENT_IS_TRUE.getDescrip())){
            return createTextsFrom(
                    STATEMENT_IS_TRUE_OPTION.getJustification(),
                    STATEMENT_IS_TRUE_JUSTIFICATION.getJustification(),
                    STATEMENT_IS_TRUE_SUMMARY.getJustification());
        }

        if(source.getDescrip().equals(STATEMENT_IS_FALSE.getDescrip())){
            return createTextsFrom(
                    STATEMENT_IS_FALSE_OPTION.getJustification(),
                    STATEMENT_IS_FALSE_JUSTIFICATION.getJustification(),
                    STATEMENT_IS_FALSE_SUMMARY.getJustification());
        }

        if(source.getDescrip().equals(FACTOR_SYMBOL.getDescrip())){
            return createTextsFrom(
                    FACTOR_SYMBOL_OPTION.getJustification(),
                    FACTOR_SYMBOL_JUSTIFICATION.getJustification(),
                    FACTOR_SYMBOL_SUMMARY.getJustification());
        }

        if(source.getDescrip().equals(FACTOR_DIFFERENCE_OF_SQUARES.getDescrip())){
            return createTextsFrom(
                    FACTOR_DIFFERENCE_OF_SQUARES_OPTION.getJustification(),
                    FACTOR_DIFFERENCE_OF_SQUARES_JUSTIFICATION.getJustification(),
                    FACTOR_DIFFERENCE_OF_SQUARES_SUMMARY.getJustification());
        }

        if(source.getDescrip().equals(FACTOR_PERFECT_SQUARE.getDescrip())){
            return createTextsFrom(
                    FACTOR_PERFECT_SQUARE_OPTION.getJustification(),
                    FACTOR_PERFECT_SQUARE_JUSTIFICATION.getJustification(),
                    FACTOR_PERFECT_SQUARE_SUMMARY.getJustification());
        }

        if(source.getDescrip().equals(FACTOR_SUM_PRODUCT_RULE.getDescrip())){
            return createTextsFrom(
                    FACTOR_SUM_PRODUCT_RULE_OPTION.getJustification(),
                    FACTOR_SUM_PRODUCT_RULE_JUSTIFICATION.getJustification(),
                    FACTOR_SUM_PRODUCT_RULE_SUMMARY.getJustification());
        }*/

        // TODO: BREAK_UP_TERM

        return justifications;
    }

    private static Map<String, String> createTextsFrom(String optionJustification, String correctOptionJustification, String summaryJustification){
        Map<String, String> justifications = new HashMap<>();
        //justifications.put("option", optionJustification.replace("/comparador/", textOfComparator(ExpressionsManager.comparatorOperator)));
        //justifications.put("correctOptionJustification", correctOptionJustification.replace("/comparador/", textOfComparator(ExpressionsManager.comparatorOperator)));
        justifications.put("option", optionJustification);
        justifications.put("correctOptionJustification", correctOptionJustification);
        justifications.put("summary", summaryJustification);
        return justifications;
    }

    public static String textOfComparator(String comparator){
        switch (comparator){
            case ">":
                return "mayor";
            case "<":
                return "menor";
            case ">=":
                return "mayor o igual";
            case "<=":
                return "menor o igual";
            default:
                return "igual";
        }
    }

/*    private static Map<String, String> createTextsFrom(int optionId, int justificationId){
        Map<String, String> justifications = new HashMap<>();
        justifications.put("option", context.getString(optionId).replace("/comparador/", textOfComparator(ExpressionsManager.comparatorOperator)));
        justifications.put("incorrectOptionJustification", context.getString(justificationId).replace("/comparador/", textOfComparator(ExpressionsManager.comparatorOperator)));
        return justifications;
    }*/

    /*public static Map<String, String> getContextOfResolutionTexts(String contextOfResolution){
        if(SolvePolynomialActivity.CONTEXT_OF_RESOLUTION_IS_POLYNOMIAL_FACTORIZED.equals(contextOfResolution)){
            return createContextOfResolutionTextsFrom(context, POLYNOMIAL_FACTORIZED_FIRST_TEXT, POLYNOMIAL_FACTORIZED_SECOND_TEXT, SolvePolynomialActivity.CONTEXT_OF_RESOLUTION_IS_POLYNOMIAL_FACTORIZED);
        }

        if(SolveEquationActivity.CONTEXT_OF_RESOLUTION_IS_DOMAIN.equals(contextOfResolution)){
            return createContextOfResolutionTextsFrom(context, DOMAIN_FIRST_TEXT, DOMAIN_SECOND_TEXT, SolveEquationActivity.CONTEXT_OF_RESOLUTION_IS_DOMAIN);
        }

        if(SolveEquationActivity.CONTEXT_OF_RESOLUTION_IS_IMAGE.equals(contextOfResolution)){
            return createContextOfResolutionTextsFrom(context, IMAGE_FIRST_TEXT, IMAGE_SECOND_TEXT, SolveEquationActivity.CONTEXT_OF_RESOLUTION_IS_IMAGE);
        }

        if(SolveEquationActivity.CONTEXT_OF_RESOLUTION_IS_ORIGIN_ORD.equals(contextOfResolution)){
            return createContextOfResolutionTextsFrom(context, ORIGIN_ORD_FIRST_TEXT, ORIGIN_ORD_SECOND_TEXT, SolveEquationActivity.CONTEXT_OF_RESOLUTION_IS_ORIGIN_ORD);
        }

        if(SolveEquationActivity.CONTEXT_OF_RESOLUTION_IS_ROOTS.equals(contextOfResolution)){
            return createContextOfResolutionTextsFrom(context, ROOTS_FIRST_TEXT, ROOTS_SECOND_TEXT, SolveEquationActivity.CONTEXT_OF_RESOLUTION_IS_ROOTS);
        }

        if(SolveEquationActivity.CONTEXT_OF_RESOLUTION_IS_EQUATION_WITH_FINITE_SOLUTIONS.equals(contextOfResolution)){
            return createContextOfResolutionTextsFrom(context, EQUATION_WITH_FINITE_SOLUTIONS_FIRST_TEXT, EQUATION_WITH_FINITE_SOLUTIONS_SECOND_TEXT, SolveEquationActivity.CONTEXT_OF_RESOLUTION_IS_EQUATION_WITH_FINITE_SOLUTIONS);
        }

        if(SolveEquationActivity.CONTEXT_OF_RESOLUTION_IS_EQUATION_WITH_INFINITE_SOLUTIONS.equals(contextOfResolution)){
            return createContextOfResolutionTextsFrom(context, EQUATION_WITH_INFINITE_SOLUTIONS_FIRST_TEXT, EQUATION_WITH_INFINITE_SOLUTIONS_SECOND_TEXT, SolveEquationActivity.CONTEXT_OF_RESOLUTION_IS_EQUATION_WITH_INFINITE_SOLUTIONS);
        }

        if(SolveEquationActivity.CONTEXT_OF_RESOLUTION_IS_EQUATION_WITHOUT_SOLUTIONS.equals(contextOfResolution)){
            return createContextOfResolutionTextsFrom(context, EQUATION_WITHOUT_SOLUTIONS_FIRST_TEXT, EQUATION_WITHOUT_SOLUTIONS_SECOND_TEXT, SolveEquationActivity.CONTEXT_OF_RESOLUTION_IS_EQUATION_WITHOUT_SOLUTIONS);
        }

        if(SolveEquationActivity.CONTEXT_OF_RESOLUTION_IS_INEQUATION_WITH_INTERVAL_SOLUTIONS.equals(contextOfResolution)){
            return createContextOfResolutionTextsFrom(context, INEQUATION_WITH_INTERVAL_SOLUTIONS_FIRST_TEXT, INEQUATION_WITH_INTERVAL_SOLUTIONS_SECOND_TEXT, SolveEquationActivity.CONTEXT_OF_RESOLUTION_IS_INEQUATION_WITH_INTERVAL_SOLUTIONS);
        }

        if(SolveEquationActivity.CONTEXT_OF_RESOLUTION_IS_INEQUATION_WITHOUT_SOLUTIONS.equals(contextOfResolution)){
            return createContextOfResolutionTextsFrom(context, INEQUATION_WITHOUT_SOLUTIONS_FIRST_TEXT, INEQUATION_WITHOUT_SOLUTIONS_SECOND_TEXT, SolveEquationActivity.CONTEXT_OF_RESOLUTION_IS_INEQUATION_WITH_INTERVAL_SOLUTIONS);
        }

        return createContextOfResolutionTextsFrom(context, COMPLEX_SOLUTIONS_FIRST_TEXT, COMPLEX_SOLUTIONS_SECOND_TEXT, "complex_solutions");
    }*/

/*    private static Map<String, String> createContextOfResolutionTextsFrom(Context context, int firstTextId, int secondTextId, String type){
        Map<String, String> texts = new HashMap<>();
        texts.put("first", context.getString(firstTextId));
        texts.put("second", context.getString(secondTextId));
        texts.put("type", type);
        return texts;
    }*/

/*    public static Map<String, String> replacePatterns(Map<String, String> contextOfResolutionTexts, String key, String pattern, String newVal){
        String value = contextOfResolutionTexts.get(key);
        contextOfResolutionTexts.remove(key);
        contextOfResolutionTexts.put(key, ExpressionsManager.removeDecimals(value.replace(pattern, newVal)));
        return contextOfResolutionTexts;
    }*/

}
