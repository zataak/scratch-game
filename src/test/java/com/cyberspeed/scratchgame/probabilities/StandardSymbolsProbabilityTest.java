package com.cyberspeed.scratchgame.probabilities;

import static com.cyberspeed.scratchgame.utils.Validation.isNonEmptyMap;
import static com.cyberspeed.scratchgame.utils.Validation.isPositive;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

import com.cyberspeed.scratchgame.GameTestBase;
import com.cyberspeed.scratchgame.utils.Validation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class StandardSymbolsProbabilityTest extends GameTestBase {

  private StandardSymbolsProbability standardSymbolsProbability;
  private StandardSymbolsProbability expectedStandardSymbolsProbability;

  private void setup() {
    expectedStandardSymbolsProbability = gameConfig.probabilities().standardSymbolsProbabilities().get(0);
    standardSymbolsProbability = new StandardSymbolsProbability(expectedStandardSymbolsProbability.row(),
      expectedStandardSymbolsProbability.column(), expectedStandardSymbolsProbability.symbols());
  }

  @Test
  void constructsCorrectly() {
    setup();
    assertEquals(expectedStandardSymbolsProbability, standardSymbolsProbability);
  }

  @Test
  void verifyValidations() {
    try (MockedStatic<Validation> validation = Mockito.mockStatic(Validation.class)) {
      setup();
      // validate rows and probabilities
      validation.verify(() -> Validation.isPositive(anyInt(), anyString()), times(8));
      // validate symbols
      validation.verify(() -> isNonEmptyMap(anyMap(), anyString()), times(1));
    }
  }
}
