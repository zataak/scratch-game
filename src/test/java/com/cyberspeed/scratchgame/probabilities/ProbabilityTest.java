package com.cyberspeed.scratchgame.probabilities;

import static com.cyberspeed.scratchgame.utils.Validation.isNonEmptyCollection;
import static com.cyberspeed.scratchgame.utils.Validation.isNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
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
public class ProbabilityTest extends GameTestBase {

  @Test
  void constructsCorrectly() {
    var actualProbability = new Probability(probability.standardSymbolsProbabilities(), probability.bonusSymbolsProbability());
    assertEquals(probability.bonusSymbolsProbability(), actualProbability.bonusSymbolsProbability());
    assertEquals(probability.standardSymbolsProbabilities(), actualProbability.standardSymbolsProbabilities());
  }

  @Test
  void verifyValidations() {
    try (MockedStatic<Validation> validation = Mockito.mockStatic(Validation.class)) {
      new Probability(probability.standardSymbolsProbabilities(), probability.bonusSymbolsProbability());
      // validate symbols
      validation.verify(() -> isNonEmptyCollection(anyList(), anyString()), times(1));
      // validate probabilities
      validation.verify(() -> isNotNull(any(BonusSymbolsProbability.class), anyString()), times(1));
    }
  }
}
