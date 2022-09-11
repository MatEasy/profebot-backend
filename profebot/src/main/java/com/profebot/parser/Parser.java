package com.profebot.parser;

import com.profebot.exceptions.InvalidExpressionException;
import com.profebot.scanner.LexicalScanner;
import com.profebot.structures.lexicalScannerStructures.Token;
import com.profebot.structures.parserStructures.Tree;
import com.profebot.structures.parserStructures.TreeNode;
import java.util.HashMap;
import java.util.Map;

public class Parser {

  LexicalScanner scannerService = null;

  public Tree parseExpression(String expression) throws InvalidExpressionException {
    Tree tree = new Tree(); // crea el nodo
    scannerService = new LexicalScanner(expression); // crea el servicio del scanner lexico

    TreeNode rootNode = new TreeNode("="); // crea el nodo del arbol
    tree.setRootNode(rootNode); // setea como raiz el nodo =

    // Expresion = Expresion
    rootNode.setLeftNode(getExpression());
    scannerService.match(Token.IGUAL);
    rootNode.setValue(scannerService.getResultado()); // Reemplazo por el verdadero operador, puede ser =. <. <=, >, >=
    rootNode.setRightNode(getExpression());

    return tree;
  }

  /**
   * Una expresion es una lista de terminos separados por + y -
   *
   * @return
   * @throws InvalidExpressionException
   */
  private TreeNode getExpression() throws InvalidExpressionException {

    // En principio debe tener al menos 1 termino
    TreeNode expression = getTermino();

    // Si al procesar el termino sigue una suma o resta,
    // coloca el operador como raiz y el proximo termino a la derecha
    while (Token.SUMA.equals(scannerService.prox_Token()) || Token.RESTA.equals(scannerService.prox_Token())) {

      TreeNode operacionAditiva = new TreeNode(scannerService.getResultado()); // El operador escaneado
      operacionAditiva.setLeftNode(expression); // el termino inicial queda a la izquierda
      expression = operacionAditiva; // El operador pasa a ser la raiz de la expresion

      scannerService.match(scannerService.prox_Token()); // Descarto el operador
      operacionAditiva.setRightNode(getTermino()); // El nuevo termino a la derecha
    }
    return expression;
  }

  /**
   * Una termino es una lista de primarias (X o Constante) separadas por * y /
   *
   * @return
   * @throws InvalidExpressionException
   */
  private TreeNode getTermino() throws InvalidExpressionException {

    // En principio debe tener al menos 1 primaria (CONSTANTE O X)
    TreeNode termino = getPrimaria();

    // Si al procesar la primaria sigue un producto o division,
    // coloca el operador como raiz y la proxima primaria a la derecha
    while (Token.MULTIPLICACION.equals(scannerService.prox_Token()) || Token.DIVISION.equals(scannerService.prox_Token())) {
      TreeNode operacionMutiplicativa = new TreeNode(scannerService.getResultado()); // El operador escaneado
      operacionMutiplicativa.setLeftNode(termino); // la primaria inicial queda a la izquierda
      termino = operacionMutiplicativa; // El operador pasa a ser la raiz del termino

      scannerService.match(scannerService.prox_Token()); // Descarto el operador
      operacionMutiplicativa.setRightNode(getPrimaria()); // La nueva primaria a la derecha
    }
    return termino;
  }

  /**
   * Una primaria es un termino basico, una X o CTE, o una expresion entera si esta ente parentesis
   *
   * @return
   * @throws InvalidExpressionException
   */
  private TreeNode getPrimaria() throws InvalidExpressionException {

    TreeNode primaria;
    Token Token = scannerService.prox_Token();
    if (com.profebot.structures.lexicalScannerStructures.Token.CONSTANTE.equals(Token) ||
        com.profebot.structures.lexicalScannerStructures.Token.LETRA_X.equals(Token)) {
      primaria = new TreeNode(scannerService.getResultado());
      scannerService.match(Token); // Descarto la primaria

      // Si sigue una potencia, tiene q venir una constante
      if (com.profebot.structures.lexicalScannerStructures.Token.POTENCIA.equals(scannerService.prox_Token())) {
        primaria = getPotencia(primaria);
      }
    } else if (com.profebot.structures.lexicalScannerStructures.Token.RAIZ.equals(Token)) {
      scannerService.match(com.profebot.structures.lexicalScannerStructures.Token.RAIZ);
      primaria = new TreeNode(scannerService.getResultado()); // El operador escaneado
      primaria.setLeftNode(new TreeNode("2")); // TODO definir si no es cuadrada
      primaria.setRightNode(getPrimaria());
    } else {

      // Si viene luego de un igual o al principio, puede ser un "-" valido
      Boolean addUnaryMinus = false;
      if (/*scannerService.isFirstChar() &&*/ com.profebot.structures.lexicalScannerStructures.Token.RESTA.equals(Token)) {
        scannerService.match(com.profebot.structures.lexicalScannerStructures.Token.RESTA);
        addUnaryMinus = true;
      }
      scannerService.match(com.profebot.structures.lexicalScannerStructures.Token.PARENIZQUIERDO);
      primaria = getExpression();
      scannerService.match(com.profebot.structures.lexicalScannerStructures.Token.PARENDERECHO);

      // Si sigue una potencia, encierra el parentesis, tiene q venir una constante
      if (com.profebot.structures.lexicalScannerStructures.Token.POTENCIA.equals(scannerService.prox_Token())) {
        primaria = getPotencia(primaria);
      }

      if (addUnaryMinus) {
        primaria = TreeNode.createUnaryMinus(primaria);
      }
    }
    return primaria;
  }

  private TreeNode getPotencia(TreeNode primaria) throws InvalidExpressionException {

    scannerService.match(Token.POTENCIA);
    TreeNode operacionPotencia = new TreeNode(scannerService.getResultado()); // El operador escaneado

    scannerService.match(Token.CONSTANTE);
    operacionPotencia.setLeftNode(primaria); // la primaria inicial queda a la izquierda
    operacionPotencia.setRightNode(new TreeNode(scannerService.getResultado())); // la constante

    return operacionPotencia;
  }

  public static Double fractionToDouble(String fraction) {
    Double d = null;
    if (fraction != null) {
      if (fraction.contains("/")) {
        String[] numbers = fraction.split("/");
        if (numbers.length == 2) {
          Double d1 = Double.valueOf(numbers[0]);
          Double d2 = Double.valueOf(numbers[1]);
          d = Double.parseDouble(String.format("%.2f", d1/d2).replace(",","."));
        }
      }
      else {
        d = Double.parseDouble(fraction);
      }
    }
    if (d == null) {
    }
    return d;
  }

  private static Map<Integer, Double> AddTerm(String potential, String coefficient, Map<Integer, Double> polynomialMap) {
    Double newCoefficient;
    if(!polynomialMap.containsKey(Integer.parseInt(potential))){
      newCoefficient = fractionToDouble(coefficient);
    }else {
      newCoefficient = polynomialMap.get(Integer.parseInt(potential)) + fractionToDouble(coefficient);
      polynomialMap.remove(potential);
    }
    if(newCoefficient != 0){
      polynomialMap.put(Integer.parseInt(potential), newCoefficient);
    }
    return polynomialMap;
  }

  public static Map<Integer, Double> parsePolinomialToHashMap(String equation) {
    Map<Integer, Double> polynomialMap = new HashMap<>();
    equation = equation.replaceAll("-", "+!").replaceAll("\\s+", "").replaceAll("X","x").replaceAll("\\*","");;
    String[] terms = equation.split("\\+");

    for (String term : terms) {
      if (!term.isEmpty()) {
        String potential, coefficient;
        if (term.contains("x^")) {
          int position = term.indexOf("x^");
          switch (position) {
            case 1:
              if (term.substring(0, position).contains("!")) {
                coefficient = "-1";
              } else {
                coefficient = term.substring(0, position);
              }
              break;
            case 0:
              coefficient = "1";
              break;
            default:
              coefficient = term.substring(0, position).replace("!", "-");
              break;
          }
          potential = term.substring(position + 2, term.length());
          coefficient = coefficient.replaceAll("\\{", "").replaceAll("\\}", "").replaceAll("\\)", "").replaceAll("\\(", "");
          potential = potential.replaceAll("\\{", "").replaceAll("\\}", "").replaceAll("\\)", "").replaceAll("\\(", "");
          polynomialMap = AddTerm(potential, coefficient, polynomialMap);
        } else if (term.contains("x")) {//es un coeficeinte lineal
          int positionLineal = term.indexOf("x");
          switch (positionLineal) {
            case 1:
              if (term.substring(0, positionLineal).contains("!")) {
                coefficient = "-1";
              } else {
                coefficient = term.substring(0, positionLineal);
              }
              break;
            case 0:
              coefficient = "1";
              break;
            default:
              coefficient = term.substring(0, positionLineal).replace("!", "-");
              break;
          }
          polynomialMap = AddTerm("1", coefficient, polynomialMap);
        } else {//es un termino independiente
          if (term.contains("!")) {
            term = term.replaceAll("!","-");
          }
          polynomialMap = AddTerm("0", term, polynomialMap);
        }
      }
    }
    return polynomialMap;
  }

}
