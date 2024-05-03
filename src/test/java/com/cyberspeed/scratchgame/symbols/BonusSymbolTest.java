package com.cyberspeed.scratchgame.symbols;

import static com.cyberspeed.scratchgame.utils.Validation.isGreaterThanZero;
import static com.cyberspeed.scratchgame.utils.Validation.isNotBlank;
import static com.cyberspeed.scratchgame.utils.Validation.isPositive;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

import com.cyberspeed.scratchgame.utils.Validation;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

public class BonusSymbolTest {

  BonusSymbol actualBonusSymbol;

  public void setup() {
    actualBonusSymbol = new BonusSymbol(10, 0, "multiply_reward");
  }

  @Test
  void constructsCorrectly() {
    setup();
    assertEquals(0, actualBonusSymbol.getExtra());
    assertEquals(10, actualBonusSymbol.getRewardMultiplier());
    assertEquals("bonus", actualBonusSymbol.getType());
    assertEquals("multiply_reward", actualBonusSymbol.getImpact());
  }

  @Test
  void verifyValidations() {
    try (MockedStatic<Validation> validation = Mockito.mockStatic(Validation.class)) {
      setup();
      // validate extra
      validation.verify(() -> isPositive(anyInt(), anyString()), times(1));
      validation.verify(() -> isGreaterThanZero(anyDouble(), anyString()), times(1));
      // validate rewardMultiplier
      validation.verify(() -> isPositive(anyDouble(), anyString()), times(1));
      // validate type and impact
      validation.verify(() -> isNotBlank(anyString(), anyString()), times(2));
    }
  }

}
