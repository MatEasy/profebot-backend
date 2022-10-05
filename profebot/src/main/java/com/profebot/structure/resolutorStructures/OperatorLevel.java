package com.profebot.structure.resolutorStructures;

import java.util.HashMap;
import java.util.Map;

public enum OperatorLevel {

  SUMA(1, "+"),
  RESTA(1, "-"),
  MULTIPLICACION(2, "*"),
  DIVISION(2, "/"),
  POTENCIA(3, "^"),
  RAIZ(3, "R");

  private static final Map<String, OperatorLevel> map = new HashMap<String, OperatorLevel>();

  static {
    for (OperatorLevel op : OperatorLevel.values()) {
      map.put(op.getSymbol(), op);
    }
  }

  Integer code;
  String symbol;

  OperatorLevel(Integer code, String symbol) {
    this.code = code;
    this.symbol = symbol;
  }

  public static OperatorLevel getBySimbol(String symbol) {
    return map.get(symbol);
  }

  public Integer getCode() {
    return code;
  }

  public String getSymbol() {
    return symbol;
  }
}
