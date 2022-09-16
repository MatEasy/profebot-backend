package com.profebot.resolutors;

import com.profebot.enums.FunctionType;
import com.profebot.exceptions.InvalidExpressionException;
import com.profebot.model.Step;
import com.profebot.parser.Parser;
import com.profebot.service.SimplifyService;
import com.profebot.solvers.Solver;
import com.profebot.structures.parserStructures.Tree;
import com.profebot.structures.parserStructures.TreeNode;
import com.profebot.utils.TreeUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

public class FunctionResolutor implements IResolutor {

  SimplifyService simplifyService;

  @Autowired
  public FunctionResolutor(SimplifyService simplifyService) {
    this.simplifyService = simplifyService;
  }

  public static FunctionType getFunctionType(String function) {

    Tree functionTree;
    FunctionType functionType;
    try {
      // F(X) = 0
      functionTree = (new Parser()).parseExpression(function);
      // Left node is evaluated
      TreeNode functionNode = functionTree.getLeftNode();

      if (isConstant(functionNode)) {
        functionType = FunctionType.CONSTANT;
      } else if (isLinear(functionNode)) {
        functionType = FunctionType.LINEAR;
      } else if (isHomographic(functionNode)) {
        functionType = FunctionType.HOMOGRAPHIC;
      } else if (isQuadratic(functionNode)) {
        functionType = FunctionType.QUADRATIC;
      } else {
        functionType = FunctionType.INVALID;
      }
    } catch (Exception e) {
      functionType = FunctionType.INVALID;
    }
    return functionType;
  }

  private static Boolean isLinear(TreeNode functionNode) {
    // Linear = ax + b

    // Non null node
    if (functionNode == null) {
      return false;
    }

    // Remove parentesis
    if (functionNode.isParenthesis()) {
      return isLinear(functionNode.getContent());
    }

    // Flatten
    TreeNode functionNodeFlattened = TreeUtils.flattenOperands(functionNode);

    if (functionNodeFlattened.getArgs().get(0) == null) {
      if (TreeUtils.isConstantFraction(functionNodeFlattened)) {
        return true;
      }

      // Symbol / Constant?
      if (TreeUtils.isSymbolFraction(functionNodeFlattened, false, 1)) {
        return true;
      }

      // Constant?
      if (TreeUtils.isConstant(functionNodeFlattened)) {
        return true;
      }

      // Symbol?
      if (TreeUtils.isSymbol(functionNodeFlattened, false, 1)) {
        return true;
      }
    }
    // Root node must be adition
    if (!functionNodeFlattened.esSuma()) {
      return false;
    }

    if (functionNodeFlattened.getArgs().size() > 2) {
      return false;
    }

    int constantsCount = 0;
    int symbolCount = 0;
    // All args must be symbols, constants, symboil fractions or constant fractions
    for (TreeNode node : functionNodeFlattened.getArgs()) {

      // Constant fraction?
      if (TreeUtils.isConstantFraction(node)) {
        constantsCount = constantsCount + 1;
        continue;
      }

      // Symbol / Constant?
      if (TreeUtils.isSymbolFraction(node, false, 1)) {
        symbolCount = symbolCount + 1;
        continue;
      }

      // Constant?
      if (TreeUtils.isConstant(node)) {
        constantsCount = constantsCount + 1;
        continue;
      }

      // Symbol?
      if (TreeUtils.isSymbol(node, false, 1)) {
        symbolCount = symbolCount + 1;
        continue;
      }

      // No valid term found, not linear
      return false;
    }

    return (symbolCount < 2) && (constantsCount < 2);
    // is linear
  }

  private static Boolean isConstant(TreeNode functionNode) {
    // Non null node
    return functionNode != null &&
        !functionNode.toExpression().contains("X");
  }

  public static Boolean validateHomographicDenominator(TreeNode functionNode) {
    //Linear function Cx + d with C != 0

    // Non null node
    if (functionNode == null) {
      return false;
    }

    // Remove parentesis
    if (functionNode.isParenthesis()) {
      return validateHomographicDenominator(functionNode.getContent());
    }

    // Flatten
    TreeNode functionNodeFlattened = TreeUtils.flattenOperands(functionNode);

    if (functionNodeFlattened.getArgs().get(0) == null) {
      // Symbol / Constant?
      if (TreeUtils.isSymbolFraction(functionNodeFlattened, false, 1) && functionNodeFlattened.getBase() != "0") {
        return true;
      }

      // Symbol?
      if (TreeUtils.isSymbol(functionNodeFlattened, false, 1) && functionNodeFlattened.getBase() != "0") {
        return true;
      }
    }
    // Root node must be adition
    if (!functionNodeFlattened.esSuma()) {
      return false;
    }

    if (functionNodeFlattened.getArgs().size() > 2) {
      return false;
    }
    // All args must be symbols, constants, symboil fractions or constant fractions
    int constantsCount = 0;
    int symbolCount = 0;
    for (TreeNode node : functionNodeFlattened.getArgs()) {

      // Constant fraction?
      if (TreeUtils.isConstantFraction(node)) {
        constantsCount = constantsCount + 1;
        continue;
      }

      // Symbol / Constant?
      if (TreeUtils.isSymbolFraction(node, false, 1)) {
        symbolCount = symbolCount + 1;
        continue;
      }

      // Constant?
      if (TreeUtils.isConstant(node)) {
        constantsCount = constantsCount + 1;
        continue;
      }

      // Symbol?
      if (TreeUtils.isSymbol(node, false, 1)) {
        symbolCount = symbolCount + 1;
        continue;
      }

      // No valid term found, not linear
      return false;
    }

    return (symbolCount < 2) && (constantsCount < 2);
    // is linear
  }

  private static Boolean isHomographic(TreeNode functionNode) {

    // Homographic = Linear / Linear

    // Non null node
    if (functionNode == null) {
      return false;
    }

    // Remove parentesis
    if (functionNode.isParenthesis()) {
      return isHomographic(functionNode.getContent());
    }

    // Root node must be /
    if (!functionNode.esDivision()) {
      return false;
    }

    // Left node and right node must be linear
    //if (!isLinear(functionNode.getLeftNode())){return false;}
    //return isLinear(functionNode.getRightNode());

    if (!isLinear(functionNode.getLeftNode())) {
      return false;
    }
    return validateHomographicDenominator(functionNode.getRightNode());
  }

  private static Boolean isQuadratic(TreeNode functionNode) {

    // Linear = ax^2 + bx + c

    // Non null node
    if (functionNode == null) {
      return false;
    }

    // Remove parentesis
    if (functionNode.isParenthesis()) {
      return isQuadratic(functionNode.getContent());
    }

    // Flatten
    TreeNode functionNodeFlattened = TreeUtils.flattenOperands(functionNode);

    return TreeUtils.hasQuadraticNode(functionNodeFlattened);
  }

  private int invert(int numToInvert) {
    if (numToInvert < 0) {
      return Math.abs(numToInvert);
    } else {
      return numToInvert * -1;
    }
  }

  private String cleanInterval(String intervalBegin) {
    return intervalBegin.replaceAll("x", "").replaceAll("X", "").replaceAll("=", "");
  }

  private String getImage(String equation, FunctionType equationType) {
    String image = null;
    switch (equationType) {
      case CONSTANT:
        image = "En estas funciones del tipo constante, el dominio son todos los reales, por lo que la imagen también lo es.";
        break;
      case LINEAR:
        image = "En estas funciones del tipo lineal, el dominio son todos los reales, por lo que la imagen también lo es.";
        break;
      case QUADRATIC:
        //Special Cases in Quadratic
        if (equation == "X^2") {
          image =
              "En el tipo de función cuadrática, la imagen son los reales positivos, a pesar de que el dominio acepta todos los reales.";
        } else {
          //Analizo concavidad e intervalo
          String concavidad;
          String intervalo;
          String second_equation = "y = \\operatorname{F}\\bigg(\\frac{-b}{2*a}\\bigg)";
          Map<Integer, Double> equationMapped = Parser.parsePolinomialToHashMap(equation);
          if (equationMapped.get(2) > 0) {
            concavidad = "concavidad positiva";
            int numToGetBeginInterval =
                (equationMapped.get(1) == null) ? 0 : (invert(equationMapped.get(1).intValue()) / (2 * equationMapped.get(2).intValue()));
            String equationToSolve =
                equation.replaceAll("x", String.valueOf(numToGetBeginInterval)).replaceAll("X", String.valueOf(numToGetBeginInterval));
            try {
              String intervalBegin = (new Solver()).resolveExpression(equationToSolve + "=x");
              intervalo = "[" + cleanInterval(intervalBegin) + ", + \\infty )";
              //intervalo = (equationMapped.get(1) == null) ? "[ 0, + \\infty )" : "[ \\frac{" + equationMapped.get(1) + "}{2*" + equationMapped.get(2) + "}, + \\infty)";
              image = String.format(
                  "Esta función cuadrática tiene una %s, presentando un mínimo global, el conjunto que representa la imagen es : %s. Surge de resolver la inecuacion: %s",
                  concavidad, intervalo, second_equation);
            } catch (InvalidExpressionException e) {
              e.printStackTrace();
            }
          } else {
            //intervalo =  (equationMapped.get(1) == 0) ?  "( - \\infty , 0 ]" : "[- \\infty , " + equationMapped.get(1)+"/2"+ equationMapped.get(2) +"]";
            concavidad = "concavidad negativa";
            int numToGetBeginInterval =
                (equationMapped.get(1) == null) ? 0 : (invert(equationMapped.get(1).intValue()) / (2 * equationMapped.get(2).intValue()));
            String equationToSolve =
                equation.replaceAll("x", String.valueOf(numToGetBeginInterval)).replaceAll("X", String.valueOf(numToGetBeginInterval));
            try {
              String intervalBegin = (new Solver()).resolveExpression(equationToSolve + "=x");
              Integer intervalBeginInt = Integer.parseInt(cleanInterval(intervalBegin));
              intervalBeginInt -=
                  ((equationMapped.get(2).intValue() * numToGetBeginInterval) * (equationMapped.get(2).intValue() * numToGetBeginInterval) *
                      2);
              intervalo = "(- \\infty , " + intervalBeginInt + " ]";
              //intervalo = (equationMapped.get(1) == null) ? "[ 0, + \\infty )" : "[ \\frac{" + equationMapped.get(1) + "}{2*" + equationMapped.get(2) + "}, + \\infty)";
              image = String.format(
                  "Esta función cuadrática tiene una %s, presentando un mínimo global, el conjunto que representa la imagen es : %s. Surge de resolver la inecuación: %s.",
                  concavidad, intervalo, second_equation);
            } catch (InvalidExpressionException e) {
              e.printStackTrace();
            }
          }

        }
        break;
      case HOMOGRAPHIC:
        //Special Cases in HOMOGRAPHIC
        int divisionPosition = equation.lastIndexOf("/");
        String solution;
        String denominatorHomographic = equation.substring(divisionPosition + 1).replaceAll("\\(", "").replaceAll("\\)", "");
        String numeratorHomographic = equation.substring(0, divisionPosition).replaceAll("\\(", "").replaceAll("\\)", "");
        Map<Integer, Double> denominatorMapped = Parser.parsePolinomialToHashMap(denominatorHomographic);
        Map<Integer, Double> numeratorMapped = Parser.parsePolinomialToHashMap(numeratorHomographic);
        if (numeratorMapped.get(1) == null) {
          solution = "\\Re - ( Asintota ) \\implies \\Re - {0}";
        } else {
          solution = "\\Re - ( Asintota ) \\implies \\Re - \\frac{" + numeratorMapped.get(1).intValue() + "}{" +
              denominatorMapped.get(1).intValue() + "}";
        }

        String firstEquation = "y = \\frac{a}{c}";

        image = String.format(
            "La imagen en las funciones homográficas se define por la presencia de una Asíntota Horizontal. Esta asíntota o recta se expresa como: %s. La imagen de la función equivale a: %s",
            firstEquation, solution);

        //setTrivialPopUp("Función Homográfica", getString(R.string.explicacionImagenHomografica, solucion));
        break;
      default:
        System.out.println("Error Imagen Funcion. NO encontro ningun tipo de funcion para analizar la imagen");
        break;
    }
    return image;
  }

  private String getDomain(String equation, FunctionType equationType) {
    String domain = null;
    switch (equationType) {
      case HOMOGRAPHIC: {
        String status = null;
        int divisionPosition = equation.lastIndexOf("/");
        String denominatorHomographic = equation.substring(divisionPosition + 1).replaceAll("\\(", "").replaceAll("\\)", "");
        Map<Integer, Double> denominatorMapped = Parser.parsePolinomialToHashMap(denominatorHomographic);
        denominatorHomographic = denominatorHomographic + " = 0";
        if (denominatorMapped.get(0) == null) {
          status = "X \\neq 0";
        } else {
          //status = (new ResolutorService()).resolveExpression(denominatorHomographic).substring(2);
          //status = "X = " + status;
        }
        domain = "El dominio de la función aplica a todos los reales excepto cuando:" + status;
        break;
      }
      case CONSTANT:
      case LINEAR:
      case QUADRATIC: {
        domain = "El dominio son todos los reales, es decir cualquier valor";
        break;
      }
      case INVALID:
        domain = "La función es inválida.";
        break;
      default:
        break;
    }
    return domain;
  }

  private boolean startsWithResolvingRoot(String status) {
    String startWith = status.substring(0, 2).toLowerCase();
    return !startWith.equals("x=");
  }

  private String getRoots(String equation, FunctionType equationType) {
    String roots = null;
    switch (equationType) {
      case CONSTANT: {
        roots = "La función no tiene raíces, porque es constante.";
        break;
      }
      case HOMOGRAPHIC:
      case LINEAR:
      case QUADRATIC: {
        String rootExpression = equation + " = 0";
        try {
          String status = (new Solver()).resolveExpression(rootExpression);
          if (status.equals("NO_STEPS") || startsWithResolvingRoot(status)) {
            roots = "La función no tiene raíces enteras.";
          } else {
            roots = String.format("La función posee las siguientes raices:%s", status);
          }

        } catch (InvalidExpressionException e) {
          e.printStackTrace();
        }
        break;
      }
      case INVALID:
        roots = "La función es inválida.";
        break;
      default:
        break;
    }
    return roots;
  }

  private String cleanEquation(String solution) {
    return solution
        .replaceAll("/", "}{")
        .replaceAll("\\(", "")
        .replaceAll("\\)", "")
        .replaceAll("\\*", ".")
        .replaceAll("X", "x");
  }

  private String solveOrigin(String equation) throws InvalidExpressionException {
    String equationModified;
    String function;
    equationModified = equation.replaceAll("x", "0").replaceAll("X", "0") + "=x";
    String solution = (new Solver()).resolveExpression(equationModified).substring(2);
    if (solution.contains("/")) {
      function = "\\operatorname{F}(0) = \\frac{ " + cleanEquation(solution) + "} \\implies y = \\frac{" + cleanEquation(solution) + "}";
    } else {
      function = "\\operatorname{F}(0) = " + solution + "\\implies  y = " + solution;
    }
    return function;
  }

  private String getOrigin(String equation, FunctionType equationType) {
    String origin = null;
    String function;
    if (equationType == FunctionType.HOMOGRAPHIC) {
      int divisionPosition = equation.lastIndexOf("/");
      String denominatorHomographic = equation.substring(divisionPosition + 1).replaceAll("\\(", "").replaceAll("\\)", "");
      Map<Integer, Double> denominatorMapped = (new Parser()).parsePolinomialToHashMap(denominatorHomographic);
      if (denominatorMapped.get(0) == null) {
        origin =
            "La función no tiene ordenada al origen, dado que el dominio de la función excluye al valor x = 0, por lo tanto, la función no se intersecta con el eje Y.";
      } else {
        try {
          function = solveOrigin(equation);
          origin = String.format("En la función, cuando la X toma el valor 0, la ordenada al origen vale:", function);

        } catch (InvalidExpressionException e) {
          origin =
              "La función no tiene ordenada al origen, dado que el dominio de la función excluye al valor x = 0, por lo tanto, la función no se intersecta con el eje Y.";
        }
      }
    } else {
      try {
        function = solveOrigin(equation);
        origin = String.format("En la función, cuando la X toma el valor 0, la ordenada al origen vale: %s.", function);

      } catch (InvalidExpressionException e) {
        origin =
            "La función no tiene ordenada al origen, dado que el dominio de la función excluye al valor x = 0, por lo tanto, la función no se intersecta con el eje Y.";
      }
    }
    return origin;
  }

  public List<Step> SolveExercise(String exercise) {
    FunctionType functionType = getFunctionType(exercise + "=0");
    if (functionType.equals(FunctionType.INVALID)) {
      throw new RuntimeException("¡Funcion invalida!");
    }
    Step domainStep = new Step("Dominio", getDomain(exercise, functionType), null);
    Step imageStep = new Step("Imagen", getImage(exercise, functionType), null);
    Step rootsSteps = new Step("Raíces", getRoots(exercise, functionType), null);
    Step originStep = new Step("Ordenada a la origen", getOrigin(exercise, functionType), null);
    List<Step> stepList = new ArrayList<>();
    stepList.add(domainStep);
    stepList.add(imageStep);
    stepList.add(rootsSteps);
    stepList.add(originStep);
    return stepList;
  }
}
