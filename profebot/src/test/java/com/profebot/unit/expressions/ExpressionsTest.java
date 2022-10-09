package com.profebot.unit.expressions;

import com.profebot.service.ExpressionsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ExpressionsTest {
  @Test
  public void inecuacionesLineales() {
    Assertions.assertEquals("(1, +∞)", ExpressionsService.getIntervalFrom("x>1"));
    Assertions.assertEquals("[1, +∞)", ExpressionsService.getIntervalFrom("x>=1"));
    Assertions.assertEquals("(-∞, 1)", ExpressionsService.getIntervalFrom("x<1"));
    Assertions.assertEquals("(-∞, 1]", ExpressionsService.getIntervalFrom("x<=1"));
  }

  @Test
  public void inecuacionCuadraticaDeParabolaPositivaSinRaices() {
    Assertions.assertEquals("(-∞, ∞)", ExpressionsService.getIntervalFrom("x^2+x>-1"));
    Assertions.assertEquals("(-∞, ∞)", ExpressionsService.getIntervalFrom("x^2+x>=-1"));
    Assertions.assertEquals("", ExpressionsService.getIntervalFrom("x^2+x<-1"));
    Assertions.assertEquals("", ExpressionsService.getIntervalFrom("x^2+x<=-1"));
  }

  @Test
  public void inecuacionCuadraticaDeParabolaNegativaSinRaices() {
    Assertions.assertEquals("", ExpressionsService.getIntervalFrom("x^2+x>1"));
    Assertions.assertEquals("", ExpressionsService.getIntervalFrom("x^2+x>=1"));
    Assertions.assertEquals("(-∞, ∞)", ExpressionsService.getIntervalFrom("x^2+x<1"));
    Assertions.assertEquals("(-∞, ∞)", ExpressionsService.getIntervalFrom("x^2+x<=1"));
  }

}
