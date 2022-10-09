package com.profebot.unit.resolutor;

import com.profebot.exception.InvalidExpressionException;
import com.profebot.model.Step;
import com.profebot.resolutor.EquationResolutor;
import com.profebot.service.ExpressionsService;
import com.profebot.service.JustificationsService;
import com.profebot.service.SimplifyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class EquationResolutorTest {
  EquationResolutor equationResolutor = new EquationResolutor(new SimplifyService(), new JustificationsService(), new ExpressionsService());

  @Test
  public void stepThrough_ok8()  throws InvalidExpressionException {
    String expression = "5*X/2 + 2 = 3*X/2 + 10";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("X=8",text);
  }

  @Test
  public void stepThrough_ok4()  throws InvalidExpressionException {
    String expression = "2 + -3 = X";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("X=-1",text);
  }

  @Test
  public void stepThrough_ok5()  throws InvalidExpressionException {
    String expression = "X + 3 = 4";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("X=1",text);
  }

  @Test
  public void stepThrough_ok6()  throws InvalidExpressionException {
    String expression = "2X - 3 = 0";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("X=3/2",text);
  }

  @Test
  public void stepThrough_ok7()  throws InvalidExpressionException {
    String expression = "X/3 - 2 = -1";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("X=3",text);
  }

  @Test
  public void stepThrough_ok9()  throws InvalidExpressionException {
    String expression = "2X - 1 = -X";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("X=1/3",text);
  }

  @Test
  public void stepThrough_ok10()  throws InvalidExpressionException {
    String expression = "2 - X = -4 + X";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("X=3",text);
  }

  @Test
  public void stepThrough_ok11()  throws InvalidExpressionException {
    String expression = "2X/3 = 2";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("X=3",text);
  }

  @Test
  public void stepThrough_ok12()  throws InvalidExpressionException {
    String expression = "2X - 3 = X";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("X=3",text);
  }

  @Test
  public void stepThrough_ok13()  throws InvalidExpressionException {
    String expression = "8 - 2X = X + 3 - 1";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("X=2",text);
  }

  @Test
  public void stepThrough_ok14()  throws InvalidExpressionException {
    String expression = "2 - X = 4";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("X=-2",text);
  }

  @Test
  public void stepThrough_ok15()  throws InvalidExpressionException {
    String expression = "2 - 4X = X";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("X=2/5",text);
  }

  @Test
  public void stepThrough_ok16()  throws InvalidExpressionException {
    String expression = "9X + 4 - 3 = 2X";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("X=(-1)/7",text);
  }

  @Test
  public void stepThrough_ok17()  throws InvalidExpressionException {
    String expression = "9X + 4 - 3 = -2X";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("X=(-1)/11",text);
  }

  @Test
  public void stepThrough_ok18()  throws InvalidExpressionException {
    String expression = "5X + (1/2)*X = 27";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("X=54/11",text);
  }

  @Test
  public void stepThrough_ok19()  throws InvalidExpressionException {
    String expression = "2X/3 = 2X - 4 ";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("X=3",text);
  }

  @Test
  public void stepThrough_ok20()  throws InvalidExpressionException {
    String expression = "(-2/3)* X + 3/7 = 1/2 ";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("X=(-3)/28",text);
  }

  @Test
  public void stepThrough_ok21()  throws InvalidExpressionException {
    String expression = "(-9/4) * X + 4/5 = 7/8";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("X=(-1)/30",text);
  }

  @Test
  public void stepThrough_ok22()  throws InvalidExpressionException {
    String expression = "X^2 - 2 = 0";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("X^2=2",text);
  }

  @Test
  public void stepThrough_ok23()  throws InvalidExpressionException {
    String expression = "X/(2/3) = 1";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("X=2/3",text);
  }

  @Test
  public void stepThrough_ok24()  throws InvalidExpressionException {
    String expression = "(X+1)/3 = 4";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("X=11",text);
  }

  @Test
  public void stepThrough_ok25()  throws InvalidExpressionException {
    String expression = "2*(X+3)/3 = 2";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("X=0",text);
  }

  @Test
  public void stepThrough_ok26()  throws InvalidExpressionException {
    String expression = "5X^2 - 5X - 30 = 0";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("X=[-2,3]",text);
  }

  @Test
  public void stepThrough_ok27()  throws InvalidExpressionException {
    String expression = "X^2 + 3X + 2 = 0";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("X=[-1,-2]",text);
  }

  @Test
  public void stepThrough_ok28()  throws InvalidExpressionException {
    String expression = "X^2 - X = 0";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("X=[0,1]",text);
  }

  @Test
  public void stepThrough_ok29()  throws InvalidExpressionException {
    String expression = "X^2 + 2X - 15 = 0";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("X=[3,-5]",text);
  }

  @Test
  public void stepThrough_ok30()  throws InvalidExpressionException {
    String expression = "X^2 + 2X = 0";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("X=[0,-2]",text);
  }

  @Test
  public void stepThrough_ok31()  throws InvalidExpressionException {
    String expression = "X^2 - 4 = 0";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("X=[-2,2]",text);
  }

  @Test
  public void stepThrough_ok32()  throws InvalidExpressionException {
    String expression = "X^2 + 2X + 1 = 0";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("X=[-1,-1]",text);
  }

  @Test
  public void stepThrough_ok33()  throws InvalidExpressionException {
    String expression = "X^2 + 4X + 4 = 0";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("X=[-2,-2]",text);
  }

  @Test
  public void stepThrough_ok34()  throws InvalidExpressionException {
    String expression = "X^2 - 6X + 9 = 0";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("X=[3,3]",text);
  }

  @Test
  public void stepThrough_ok35()  throws InvalidExpressionException {
    String expression = "(X + 4)^2 = 0";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("X=[-4,-4]",text);
  }

  @Test
  public void stepThrough_ok36()  throws InvalidExpressionException {
    String expression = "(X - 5)^2 = 0";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("X=[5,5]",text);
  }

  @Test
  public void stepThrough_ok37()  throws InvalidExpressionException {
    String expression = "4X^2 - 81 = 0";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("X=[(-9)/2,9/2]",text);
  }

  @Test
  public void stepThrough_ok38()  throws InvalidExpressionException {
    String expression = "X^2 - 9 = 0";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("X=[-3,3]",text);
  }

  @Test
  public void stepThrough_ok39()  throws InvalidExpressionException {
    String expression = "16X^2 - 25 = 0";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("X=[(-5)/4,5/4]",text);
  }

  @Test
  public void stepThrough_ok40()  throws InvalidExpressionException {
    String expression = "X * X + 12X + 36 = 0";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("X=[-6,-6]",text);
  }

  @Test
  public void stepThrough_ok41()  throws InvalidExpressionException {
    String expression = "0 = X^2 + 3X + 2";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("X=[-1,-2]",text);
  }

  @Test
  public void stepThrough_ok42()  throws InvalidExpressionException {
    String expression = "0 = X * X + 3X + 2";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("X=[-1,-2]",text);
  }

  @Test
  public void stepThrough_ok43()  throws InvalidExpressionException {
    String expression = "X * X + (X + X) + 1 = 0";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("X=[-1,-1]",text);
  }

  @Test
  public void stepThrough_ok44()  throws InvalidExpressionException {
    String expression = "0 = X * X + (X + X) + 1";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("X=[-1,-1]",text);
  }

  @Test
  public void stepThrough_ok45()  throws InvalidExpressionException {
    String expression = "(X^3 / X) + (3X - X) + 1 = 0";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("X=[-1,-1]",text);
  }

  @Test
  public void stepThrough_ok46()  throws InvalidExpressionException {
    String expression = "0 = (X^3 / X) + (3X - X) + 1";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("X=[-1,-1]",text);
  }

  @Test
  public void stepThrough_ok47()  throws InvalidExpressionException {
    String expression = "2^7 * (X + 2) = 0";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("X=-2",text);
  }

  @Test
  public void stepThrough_ok48()  throws InvalidExpressionException {
    String expression = "(33 + 89) * (X - 99) = 0";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("X=99",text);
  }

  @Test
  public void stepThrough_ok49()  throws InvalidExpressionException {
    String expression = "X^2 * (X - 5)^2 = 0";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("X=[0,0,5,5]",text);
  }

  @Test
  public void stepThrough_ok50()  throws InvalidExpressionException {
    String expression = "X^2 = 0";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("X=[0,0]",text);
  }

  @Test
  public void stepThrough_ok51()  throws InvalidExpressionException {
    String expression = "X^2 = 0";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("X=[0,0]",text);
  }

  @Test
  public void stepThrough_ok52()  throws InvalidExpressionException {
    String expression = "(X+2)^2 -X^2 = 4*(X+1)";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("4=4",text);
  }

  @Test
  public void stepThrough_ok53()  throws InvalidExpressionException {
    String expression = "2/X = 1";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("X=2",text);
  }

  @Test
  public void stepThrough_ok54()  throws InvalidExpressionException {
    String expression = "2/(4X) = 1";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("X=1/2",text);
  }

  @Test
  public void stepThrough_ok55()  throws InvalidExpressionException {
    String expression = "2/(8 - 4X) = 1/2";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("X=1",text);
  }

  @Test
  public void stepThrough_ok56()  throws InvalidExpressionException {
    String expression = "2/(1 + 1 + 4X) = 1/3";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("X=1",text);
  }

  /*
  @Test
  public void stepThrough_ok57()  throws InvalidExpressionException {
    String expression = "(3 + X) / (X^2 + 3) = 1";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("X=[0,1]",text);
  }
  */
  
  @Test
  public void stepThrough_ok58()  throws InvalidExpressionException {
    String expression = "6/X + 8/(2X) = 10";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("X=1",text);
  }

  @Test
  public void stepThrough_ok66()  throws InvalidExpressionException {
    String expression = "X=-1*(4^2)+8*4-2";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("X=14", text);
  }

  /*
  @Test
  public void stepThrough_ok67()  throws InvalidExpressionException {
    String expression = "X=-(4^2)+8*4-2";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("X=14", text);
  }
  */

  @Test
  public void inecuacion_cuadratica_1()  throws InvalidExpressionException {
    String expression = "X^2+2X+1>0";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("I=(-INF,-1)U(-1,INF)", text);
  }

  @Test
  public void inecuacion_cuadratica_2()  throws InvalidExpressionException {
    String expression = "X^2+2X+1>=0"; // (X+1)*(X+1)

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("I=(-INF,INF)", text);
  }

  @Test
  public void inecuacion_cuadratica_3()  throws InvalidExpressionException {
    String expression = "(X+3)*(X-5)>0";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("I=(-INF,-3)U(5,INF)", text);
  }

  @Test
  public void inecuacion_cuadratica_4()  throws InvalidExpressionException {
    String expression = "(X+3)*(X-5)>=0";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("I=(-INF,-3]U[5,INF)", text);
  }

  @Test
  public void inecuacion_cuadratica_5()  throws InvalidExpressionException {
    String expression = "(X+1)*(X+1)<0";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("I=VACIO", text);
  }

  @Test
  public void inecuacion_cuadratica_6()  throws InvalidExpressionException {
    String expression = "(X+1)*(X+1)<=0";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("I=-1", text);
  }

  @Test
  public void inecuacion_cuadratica_7()  throws InvalidExpressionException {
    String expression = "4X^2-16>=0";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("I=(-INF,-2]U[2,INF)", text);
  }

  @Test
  public void inecuacion_cuadratica_8()  throws InvalidExpressionException {
    String expression = "X^2+12X-64>0";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("I=(-INF,-16)U(4,INF)", text);
  }

  @Test
  public void inecuacion_cuadratica_9()  throws InvalidExpressionException {
    String expression = "X*(X+9)>0";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("I=(-INF,-9)U(0,INF)", text);
  }

  @Test
  public void inecuacion_cuadratica_10()  throws InvalidExpressionException {
    String expression = "X^2<=0";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("I=0", text);
  }

  @Test
  public void inecuacion_cuadratica_11()  throws InvalidExpressionException {
    String expression = "5X^2<0";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("I=VACIO", text);
  }

  @Test
  public void inecuacion_cuadratica_12()  throws InvalidExpressionException {
    String expression = "(X+7)*(X-3)<0";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("I=(-7,3)", text);
  }

  @Test
  public void inecuacion_cuadratica_13()  throws InvalidExpressionException {
    String expression = "(X+7)*(X+3)<=0";

    List<Step> steps = equationResolutor.SolveExercise(expression);

    var solution =  steps.get(steps.size() - 2).getEquationOptions();

    var text = solution.get(0).getContent();

    Assertions.assertEquals("I=[-7,-3]", text);
  }
}
