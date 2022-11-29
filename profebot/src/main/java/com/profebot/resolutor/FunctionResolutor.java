package com.profebot.resolutor;

import com.profebot.enums.EquationOptionType;
import com.profebot.enums.FunctionType;
import com.profebot.exception.InvalidExpressionException;
import com.profebot.model.EquationOption;
import com.profebot.model.Step;
import com.profebot.parser.Parser;
import com.profebot.service.SimplifyService;
import com.profebot.solver.Solver;
import com.profebot.structure.parserStructures.Tree;
import com.profebot.structure.parserStructures.TreeNode;
import com.profebot.util.TreeUtils;
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

  public List<EquationOption> getImage(String equation, FunctionType equationType) {
    List<EquationOption> image = new ArrayList<>();
    switch (equationType) {
      case CONSTANT:
        image.add(new EquationOption(
            "En estas funciones del tipo constante, el dominio son todos los reales, por lo que la imagen también lo es.",
            EquationOptionType.TEXT));
        break;
      case LINEAR:
        image.add(
            new EquationOption("En estas funciones del tipo lineal, el dominio son todos los reales, por lo que la imagen también lo es.",
                EquationOptionType.TEXT));
        break;
      case QUADRATIC:
        //Special Cases in Quadratic
        if (equation == "X^2") {
          image.add(new EquationOption(
              "En el tipo de función cuadrática, la imagen son los reales positivos, a pesar de que el dominio acepta todos los reales.",
              EquationOptionType.TEXT));
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

              image.add(new EquationOption("Esta función cuadrática tiene una ", EquationOptionType.TEXT));
              image.add(new EquationOption(concavidad + ", ", EquationOptionType.TEXT));
              image.add(
                  new EquationOption("presentando un mínimo global, el conjunto que representa la imagen es: ", EquationOptionType.TEXT));
              image.add(new EquationOption(intervalo, EquationOptionType.LATEX));
              image.add(new EquationOption(". Surge de resolver la inecuacion: ", EquationOptionType.TEXT));
              image.add(new EquationOption(second_equation, EquationOptionType.LATEX));
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
              image.add(new EquationOption("Esta función cuadrática tiene una ", EquationOptionType.TEXT));
              image.add(new EquationOption(concavidad + ", ", EquationOptionType.TEXT));
              image.add(
                  new EquationOption("presentando un mínimo global, el conjunto que representa la imagen es: ", EquationOptionType.TEXT));
              image.add(new EquationOption(intervalo, EquationOptionType.LATEX));
              image.add(new EquationOption(". Surge de resolver la inecuacion: ", EquationOptionType.TEXT));
              image.add(new EquationOption(second_equation, EquationOptionType.LATEX));
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

        image.add(new EquationOption(
            "La imagen en las funciones homográficas se define por la presencia de una Asíntota Horizontal. Esta asíntota o recta se expresa como: ",
            EquationOptionType.TEXT));
        image.add(new EquationOption(firstEquation, EquationOptionType.LATEX));
        image.add(new EquationOption(". La imagen de la función equivale a: ", EquationOptionType.TEXT));
        image.add(new EquationOption(solution, EquationOptionType.LATEX));
        break;
      case INVALID:
        image.add(new EquationOption("La función es inválida.", EquationOptionType.TEXT));
      default:
        image.add(new EquationOption("Error imagen función. No se encontró ningún tipo de función para analizar la imagen.",
            EquationOptionType.TEXT));
        break;
    }
    return image;
  }

  public List<EquationOption> getDomain(String equation, FunctionType equationType) throws InvalidExpressionException {
    List<EquationOption> domain = new ArrayList<>();
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
          status = (new Solver()).resolveExpression(denominatorHomographic).substring(2);
          status = "X = " + status;
        }
        domain.add(new EquationOption("El dominio de la función aplica a todos los reales excepto cuando: ", EquationOptionType.TEXT));
        domain.add(new EquationOption(status, EquationOptionType.LATEX));
        break;
      }
      case CONSTANT:
      case LINEAR:
      case QUADRATIC: {
        domain.add(new EquationOption("El dominio son todos los reales, es decir cualquier valor.", EquationOptionType.TEXT));
        break;
      }
      case INVALID:
        domain.add(new EquationOption("La función es inválida.", EquationOptionType.TEXT));
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

  public List<EquationOption> getRoots(String equation, FunctionType equationType) {
    List<EquationOption> roots = new ArrayList<>();
    switch (equationType) {
      case CONSTANT: {
        roots.add(new EquationOption("La función no tiene raíces, porque es constante.", EquationOptionType.TEXT));
        break;
      }
      case HOMOGRAPHIC:
      case LINEAR:
      case QUADRATIC: {
        String rootExpression = equation + " = 0";
        try {
          String status = (new Solver()).resolveExpression(rootExpression);
          if (status.equals("NO_STEPS") || startsWithResolvingRoot(status)) {
            roots.add(new EquationOption("La función no tiene raíces enteras.", EquationOptionType.TEXT));

          } else {
            roots.add(new EquationOption("La función posee las siguientes raices: ", EquationOptionType.TEXT));
            roots.add(new EquationOption(status, EquationOptionType.LATEX));
          }

        } catch (InvalidExpressionException e) {
          e.printStackTrace();
        }
        break;
      }
      case INVALID:
        roots.add(new EquationOption("La función es inválida.", EquationOptionType.TEXT));
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

  public List<EquationOption> getOrigin(String equation, FunctionType equationType) {
    List<EquationOption> origin = new ArrayList<>();
    String function;
    if (equationType == FunctionType.HOMOGRAPHIC) {
      int divisionPosition = equation.lastIndexOf("/");
      String denominatorHomographic = equation.substring(divisionPosition + 1).replaceAll("\\(", "").replaceAll("\\)", "");
      Map<Integer, Double> denominatorMapped = (new Parser()).parsePolinomialToHashMap(denominatorHomographic);
      if (denominatorMapped.get(0) == null) {
        origin.add(new EquationOption(
            "La función no tiene ordenada al origen, dado que el dominio de la función excluye al valor x = 0, por lo tanto, la función no se intersecta con el eje Y.",
            EquationOptionType.TEXT));
      } else {
        try {
          function = solveOrigin(equation);

          origin
              .add(new EquationOption("En la función, cuando la X toma el valor 0, la ordenada al origen vale: ", EquationOptionType.TEXT));
          origin.add(new EquationOption(function, EquationOptionType.LATEX));
        } catch (InvalidExpressionException e) {
          origin.add(new EquationOption(
              "La función no tiene ordenada al origen, dado que el dominio de la función excluye al valor x = 0, por lo tanto, la función no se intersecta con el eje Y.",
              EquationOptionType.TEXT));
        }
      }
    } else {
      try {
        function = solveOrigin(equation);

        origin.add(new EquationOption("En la función, cuando la X toma el valor 0, la ordenada al origen vale: ", EquationOptionType.TEXT));
        origin.add(new EquationOption(function, EquationOptionType.LATEX));
      } catch (InvalidExpressionException e) {
        origin.add(new EquationOption(
            "La función no tiene ordenada al origen, dado que el dominio de la función excluye al valor x = 0, por lo tanto, la función no se intersecta con el eje Y.",
            EquationOptionType.TEXT));
      }
    }
    return origin;
  }

  public List<Step> SolveExercise(String exercise) {
    FunctionType functionType = getFunctionType(exercise + "=0");
    if (functionType.equals(FunctionType.INVALID)) {
      throw new RuntimeException("¡Funcion invalida!");
    }
    Step domainStep;
    try{
      domainStep = new Step("Dominio:", getDomain(exercise, functionType),
          "En matemáticas, el dominio (conjunto de definición o conjunto de partida) de una función es el conjunto de existencia de ella misma, es decir, los valores para los cuales la función está definida.");
    } catch (InvalidExpressionException e) {
      throw new RuntimeException("¡Funcion invalida!");
    }
    Step imageStep = new Step("Imagen:", getImage(exercise, functionType),
        "La imagen es el rango de valores de la función f(x) para los que existe un valor de x. La forma de calcularlo dependerá del tipo de función que analizamos.");
    Step rootsSteps = new Step("Raíces:", getRoots(exercise, functionType),
        "En matemática, se conoce como raíz (o cero) de un polinomio o de una función f(x) a todo valor x (perteneciente al dominio de la función) tal que se cumpla la siguiente condición: f(x)=0.");
    Step originStep = new Step("Ordenada a la origen:", getOrigin(exercise, functionType),
        "La ordenada al origen nos indica cuándo una función se intersecta con el eje Y, que es el eje de ordenadas. Para lograr esto reemplazamos la incógnita de la función por 0, es decir, hallamos el valor de la función F(x) cuando x = 0.");
    List<Step> stepList = new ArrayList<>();
    stepList.add(domainStep);
    stepList.add(imageStep);
    stepList.add(rootsSteps);
    stepList.add(originStep);
    return stepList;
  }
}
