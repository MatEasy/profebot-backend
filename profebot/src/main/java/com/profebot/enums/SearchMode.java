package com.profebot.enums;

public enum SearchMode {
  // Basic simplifications that we always try first e.g. (...)^0 => 1
  BASIC_SEARCH(true),

  // Simplify any division chains so there's at most one division operation.
  // e.g. 2/x/6 -> 2/(x*6)        e.g. 2/(x/6) => 2 * 6/x
  DIVISION_SEARCH(true),

  // Adding fractions, cancelling out things in fractions
  FRACTION_SEARCH(true),

  // e.g. addition of polynomial terms: 2x + 4x^2 + x => 4x^2 + 3x
  // e.g. multiplication of polynomial terms: 2x * x * x^2 => 2x^3
  // e.g. multiplication of constants: 10^3 * 10^2 => 10^5
  COLLECT_AND_COMBINE_SEARCH(false),

  // e.g. 2 + 2 => 4
  ARITHMETIC_SEARCH(false),

  // e.g. (2 + x) / 4 => 2/4 + x/4
  BREAKUP_NUMERATOR_SEARCH(false),

  // e.g. 3/x * 2x/5 => (3 * 2x) / (x * 5)
  MULTIPLY_FRACTION_SEARCH(false),

  // e.g. (2x + 3)(x + 4) => 2x^2 + 11x + 12
  DISTRIBUTE_SEARCH(false),

  // e.g. abs(-4) => 4
  FUNCTION_SEARCH(false);

  private final Boolean preOrder;

  SearchMode(Boolean preOrder) {
    this.preOrder = preOrder;
  }

  public Boolean isPreOrder() {
    return preOrder;
  }
}
