package com.cyberspeed.scratchgame.symbols;

import static com.cyberspeed.scratchgame.utils.Validation.isGreaterThanZero;
import static com.cyberspeed.scratchgame.utils.Validation.isPositive;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

import com.cyberspeed.scratchgame.utils.Validation;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

public class StandardSymbolTest {

  StandardSymbol standardSymbol;

  public void setup() {
    standardSymbol = new StandardSymbol(25);
  }

  @Test
  void constructsCorrectly() {
    setup();
    assertEquals(25, standardSymbol.getRewardMultiplier());
    assertEquals("standard", standardSymbol.getType());
  }

  @Test
  void verifyValidations() {
    try (MockedStatic<Validation> validation = Mockito.mockStatic(Validation.class)) {
      setup();
      // validate rewardMultiplier
      validation.verify(() -> isPositive(anyDouble(), anyString()), times(1));
      validation.verify(() -> isGreaterThanZero(anyDouble(), anyString()), times(1));

      // additionally validate rewardMultiplier for greater than 0
      validation.verify(() -> isGreaterThanZero(anyDouble(), anyString()), times(1));
    }
  }

}
