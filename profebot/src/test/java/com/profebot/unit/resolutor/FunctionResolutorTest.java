package com.profebot.unit.resolutor;

import com.profebot.enums.EquationOptionType;
import com.profebot.enums.FunctionType;
import com.profebot.exception.InvalidExpressionException;
import com.profebot.model.EquationOption;
import com.profebot.model.Step;
import com.profebot.resolutor.FunctionResolutor;
import com.profebot.service.SimplifyService;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class FunctionResolutorTest {

  String mockedEquation = "x+1";

  @Autowired
  SimplifyService simplifyService;

  FunctionResolutor functionResolutor = new FunctionResolutor(simplifyService);

  // Test for linear function

  @Test
  void testGetOriginOfLinerFunction() {
    List<EquationOption> result = functionResolutor.getOrigin(mockedEquation, FunctionType.LINEAR);
    Assertions.assertNotNull(result);
    Assertions.assertEquals(result.size(), 2);
    Assertions.assertEquals(result.get(0).getContent(), "En la función, cuando la X toma el valor 0, la ordenada al origen vale: ");
    Assertions.assertEquals(result.get(0).getEquationOptionType(), EquationOptionType.TEXT);
    Assertions.assertEquals(result.get(1).getContent(), "\\operatorname{F}(0) = 1\\implies  y = 1");
    Assertions.assertEquals(result.get(1).getEquationOptionType(), EquationOptionType.LATEX);
  }

  @Test
  void testGetDomainOfLinerFunction() throws InvalidExpressionException {
    List<EquationOption> result = functionResolutor.getDomain(mockedEquation, FunctionType.LINEAR);
    Assertions.assertNotNull(result);
    Assertions.assertEquals(result.size(), 1);
    Assertions.assertEquals(result.get(0).getContent(), "El dominio son todos los reales, es decir cualquier valor.");
    Assertions.assertEquals(result.get(0).getEquationOptionType(), EquationOptionType.TEXT);
  }

  @Test
  void testGetRootsOfLinerFunction() {
    List<EquationOption> result = functionResolutor.getRoots(mockedEquation, FunctionType.LINEAR);
    Assertions.assertNotNull(result);
    Assertions.assertEquals(result.size(), 2);
    Assertions.assertEquals(result.get(0).getContent(), "La función posee las siguientes raices: ");
    Assertions.assertEquals(result.get(0).getEquationOptionType(), EquationOptionType.TEXT);
    Assertions.assertEquals(result.get(1).getContent(), "X=-1");
    Assertions.assertEquals(result.get(1).getEquationOptionType(), EquationOptionType.LATEX);
  }

  @Test
  void testGetImageOfLinerFunction() {
    List<EquationOption> result = functionResolutor.getImage(mockedEquation, FunctionType.LINEAR);
    Assertions.assertNotNull(result);
    Assertions.assertEquals(result.size(), 1);
    Assertions.assertEquals(result.get(0).getContent(),
        "En estas funciones del tipo lineal, el dominio son todos los reales, por lo que la imagen también lo es.");
    Assertions.assertEquals(result.get(0).getEquationOptionType(), EquationOptionType.TEXT);
  }

  // Test for constant function

  @Test
  void testGetOriginOfConstantFunction() {
    mockedEquation = "2";
    List<EquationOption> result = functionResolutor.getOrigin(mockedEquation, FunctionType.CONSTANT);
    Assertions.assertNotNull(result);
    Assertions.assertEquals(result.size(), 2);
    Assertions.assertEquals(result.get(0).getContent(), "En la función, cuando la X toma el valor 0, la ordenada al origen vale: ");
    Assertions.assertEquals(result.get(0).getEquationOptionType(), EquationOptionType.TEXT);
    Assertions.assertEquals(result.get(1).getContent(), "\\operatorname{F}(0) = 2\\implies  y = 2");
    Assertions.assertEquals(result.get(1).getEquationOptionType(), EquationOptionType.LATEX);
  }

  @Test
  void testGetDomainOfConstantFunction() throws InvalidExpressionException {
    mockedEquation = "2";
    List<EquationOption> result = functionResolutor.getDomain(mockedEquation, FunctionType.CONSTANT);
    Assertions.assertNotNull(result);
    Assertions.assertEquals(result.size(), 1);
    Assertions.assertEquals(result.get(0).getContent(), "El dominio son todos los reales, es decir cualquier valor.");
    Assertions.assertEquals(result.get(0).getEquationOptionType(), EquationOptionType.TEXT);
  }

  @Test
  void testGetRootsOfConstantFunction() {
    mockedEquation = "2";
    List<EquationOption> result = functionResolutor.getRoots(mockedEquation, FunctionType.CONSTANT);
    Assertions.assertNotNull(result);
    Assertions.assertEquals(result.size(), 1);
    Assertions.assertEquals(result.get(0).getContent(), "La función no tiene raíces, porque es constante.");
    Assertions.assertEquals(result.get(0).getEquationOptionType(), EquationOptionType.TEXT);
  }

  @Test
  void testGetImageOfConstantFunction() {
    mockedEquation = "2";
    List<EquationOption> result = functionResolutor.getImage(mockedEquation, FunctionType.CONSTANT);
    Assertions.assertNotNull(result);
    Assertions.assertEquals(result.size(), 1);
    Assertions.assertEquals(result.get(0).getContent(),
        "En estas funciones del tipo constante, el dominio son todos los reales, por lo que la imagen también lo es.");
    Assertions.assertEquals(result.get(0).getEquationOptionType(), EquationOptionType.TEXT);
  }

  // Test for homographic function

  @Test
  void testGetOriginOfHomographicFunction() {
    mockedEquation = "(x+1)/(x+2)";
    List<EquationOption> result = functionResolutor.getOrigin(mockedEquation, FunctionType.HOMOGRAPHIC);
    Assertions.assertNotNull(result);
    Assertions.assertEquals(result.size(), 2);
    Assertions.assertEquals(result.get(0).getContent(), "En la función, cuando la X toma el valor 0, la ordenada al origen vale: ");
    Assertions.assertEquals(result.get(0).getEquationOptionType(), EquationOptionType.TEXT);
    Assertions.assertEquals(result.get(1).getContent(), "\\operatorname{F}(0) = \\frac{ 1}{2} \\implies y = \\frac{1}{2}");
    Assertions.assertEquals(result.get(1).getEquationOptionType(), EquationOptionType.LATEX);
  }

  @Test
  void testGetDomainOfHomographicFunction() throws InvalidExpressionException {
    mockedEquation = "(x+1)/(x+2)";
    List<EquationOption> result = functionResolutor.getDomain(mockedEquation, FunctionType.HOMOGRAPHIC);
    Assertions.assertNotNull(result);
    Assertions.assertEquals(result.size(), 2);
    Assertions.assertEquals(result.get(0).getContent(), "El dominio de la función aplica a todos los reales excepto cuando:");
    Assertions.assertEquals(result.get(0).getEquationOptionType(), EquationOptionType.TEXT);
    Assertions.assertEquals(result.get(1).getContent(), "X = -2");
    Assertions.assertEquals(result.get(1).getEquationOptionType(), EquationOptionType.LATEX);
  }

  @Test
  void testGetRootsOfHomographicFunction() {
    mockedEquation = "(x+1)/(x+2)";
    List<EquationOption> result = functionResolutor.getRoots(mockedEquation, FunctionType.HOMOGRAPHIC);
    Assertions.assertNotNull(result);
    Assertions.assertEquals(result.size(), 2);
    Assertions.assertEquals(result.get(0).getContent(), "La función posee las siguientes raices: ");
    Assertions.assertEquals(result.get(0).getEquationOptionType(), EquationOptionType.TEXT);
    Assertions.assertEquals(result.get(1).getContent(), "X=-1");
    Assertions.assertEquals(result.get(1).getEquationOptionType(), EquationOptionType.LATEX);
  }

  @Test
  void testGetImageOfHomographicFunction() {
    mockedEquation = "(x+1)/(x+2)";
    List<EquationOption> result = functionResolutor.getImage(mockedEquation, FunctionType.HOMOGRAPHIC);
    Assertions.assertNotNull(result);
    Assertions.assertEquals(result.size(), 4);
    Assertions.assertEquals(result.get(0).getContent(),
        "La imagen en las funciones homográficas se define por la presencia de una Asíntota Horizontal. Esta asíntota o recta se expresa como: ");
    Assertions.assertEquals(result.get(0).getEquationOptionType(), EquationOptionType.TEXT);
    Assertions.assertEquals(result.get(1).getContent(), "y = \\frac{a}{c}");
    Assertions.assertEquals(result.get(1).getEquationOptionType(), EquationOptionType.LATEX);
    Assertions.assertEquals(result.get(2).getContent(),
        ". La imagen de la función equivale a: ");
    Assertions.assertEquals(result.get(2).getEquationOptionType(), EquationOptionType.TEXT);
    Assertions.assertEquals(result.get(3).getContent(), "\\Re - ( Asintota ) \\implies \\Re - \\frac{1}{1}");
    Assertions.assertEquals(result.get(3).getEquationOptionType(), EquationOptionType.LATEX);
  }


  // Test for quadratic function

  @Test
  void testGetOriginOfQuadraticFunction() {
    mockedEquation = "x^2+2x+1";
    List<EquationOption> result = functionResolutor.getOrigin(mockedEquation, FunctionType.QUADRATIC);
    Assertions.assertNotNull(result);
    Assertions.assertEquals(result.size(), 2);
    Assertions.assertEquals(result.get(0).getContent(), "En la función, cuando la X toma el valor 0, la ordenada al origen vale: ");
    Assertions.assertEquals(result.get(0).getEquationOptionType(), EquationOptionType.TEXT);
    Assertions.assertEquals(result.get(1).getContent(), "\\operatorname{F}(0) = 21\\implies  y = 21");
    Assertions.assertEquals(result.get(1).getEquationOptionType(), EquationOptionType.LATEX);
  }

  @Test
  void testGetDomainOfQuadraticFunction() throws InvalidExpressionException {
    mockedEquation = "x^2+2x+1";
    List<EquationOption> result = functionResolutor.getDomain(mockedEquation, FunctionType.QUADRATIC);
    Assertions.assertNotNull(result);
    Assertions.assertEquals(result.size(), 1);
    Assertions.assertEquals(result.get(0).getContent(), "El dominio son todos los reales, es decir cualquier valor.");
    Assertions.assertEquals(result.get(0).getEquationOptionType(), EquationOptionType.TEXT);
  }

  @Test
  void testGetRootsOfQuadraticFunction() {
    mockedEquation = "x^2+2x+1";
    List<EquationOption> result = functionResolutor.getRoots(mockedEquation, FunctionType.QUADRATIC);
    Assertions.assertNotNull(result);
    Assertions.assertEquals(result.size(), 2);
    Assertions.assertEquals(result.get(0).getContent(), "La función posee las siguientes raices: ");
    Assertions.assertEquals(result.get(0).getEquationOptionType(), EquationOptionType.TEXT);
    Assertions.assertEquals(result.get(1).getContent(), "X=[-1,-1]");
    Assertions.assertEquals(result.get(1).getEquationOptionType(), EquationOptionType.LATEX);
  }

  @Test
  void testGetImageOfQuadraticFunction() {
    mockedEquation = "x^2+2x+1";
    List<EquationOption> result = functionResolutor.getImage(mockedEquation, FunctionType.QUADRATIC);
    Assertions.assertNotNull(result);
    Assertions.assertEquals(result.size(), 6);
    Assertions.assertEquals(result.get(0).getContent(),
        "Esta función cuadrática tiene una ");
    Assertions.assertEquals(result.get(0).getEquationOptionType(), EquationOptionType.TEXT);
    Assertions.assertEquals(result.get(1).getContent(), "concavidad positiva, ");
    Assertions.assertEquals(result.get(1).getEquationOptionType(), EquationOptionType.TEXT);
    Assertions.assertEquals(result.get(2).getContent(),
        "presentando un mínimo global, el conjunto que representa la imagen es: ");
    Assertions.assertEquals(result.get(2).getEquationOptionType(), EquationOptionType.TEXT);
    Assertions.assertEquals(result.get(3).getContent(), "[3, + \\infty )");
    Assertions.assertEquals(result.get(3).getEquationOptionType(), EquationOptionType.LATEX);
    Assertions.assertEquals(result.get(4).getContent(),
        ". Surge de resolver la inecuacion: ");
    Assertions.assertEquals(result.get(4).getEquationOptionType(), EquationOptionType.TEXT);
    Assertions.assertEquals(result.get(5).getContent(), "y = \\operatorname{F}\\bigg(\\frac{-b}{2*a}\\bigg)");
    Assertions.assertEquals(result.get(5).getEquationOptionType(), EquationOptionType.LATEX);
  }

  // Test for all functions

  @Test
  void testValidFunctionType() {
    mockedEquation = mockedEquation + "=0";
    Assertions.assertEquals(FunctionResolutor.getFunctionType(mockedEquation), FunctionType.LINEAR);
  }

  @Test
  void testInvalidFunctionType() {
    mockedEquation = "log(x)=0";
    Assertions.assertEquals(FunctionResolutor.getFunctionType(mockedEquation), FunctionType.INVALID);
  }

  @Test
  void testSolveExercise() {
    List<Step> result = functionResolutor.SolveExercise(mockedEquation);
    Assertions.assertNotNull(result);
    Assertions.assertEquals(result.size(), 4);
  }

  @Test
  void testSolveInvalidExercise() {
    mockedEquation = "log(x)=0";
    Assertions.assertThrows(RuntimeException.class, () -> functionResolutor.SolveExercise(mockedEquation));
  }

}
