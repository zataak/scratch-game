package com.cyberspeed.scratchgame.wincombinations;

import static com.cyberspeed.scratchgame.utils.Validation.isGreaterThanZero;
import static com.cyberspeed.scratchgame.utils.Validation.isNotBlank;
import static com.cyberspeed.scratchgame.utils.Validation.isPositive;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import com.cyberspeed.scratchgame.ScratchCard;
import com.cyberspeed.scratchgame.utils.Validation;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class SameSymbolsWinCombinationTest {

  @Mock
  ScratchCard scratchCard;

  @Test
  void constructsCorrectly() {
    var sameSymbolsWinCombination = new SameSymbolsWinCombination(1, "same_symbols", 3);
    assertEquals(1, sameSymbolsWinCombination.getRewardMultiplier());
    assertEquals(3, sameSymbolsWinCombination.getCount());
    assertEquals("same_symbols", sameSymbolsWinCombination.getWhen());
    assertEquals("same_symbols", sameSymbolsWinCombination.getGroup());
  }

  @Test
  void verifyValidations() {
    try (MockedStatic<Validation> validation = Mockito.mockStatic(Validation.class)) {
      new SameSymbolsWinCombination(1, "same_symbols", 3);
      // validate count
      validation.verify(() -> Validation.isGreaterThanZero(anyInt(), anyString()), times(1));
      // validate rewardMultiplier
      validation.verify(() -> isGreaterThanZero(anyDouble(), anyString()), times(1));
      // validate when and group
      validation.verify(() -> isNotBlank(anyString(), anyString()), times(2));
    }
  }

  @Test
  void apply_3x_noneFound() {
    String[][] matrix = {{"A", "B", "C"}, {"E", "B", "5x"}, {"F", "D", "C"}};
    when(scratchCard.getMatrix()).thenReturn(matrix);

    var actualResults = new SameSymbolsWinCombination(1, "same_symbols", 3).apply(scratchCard);

    assertNotNull(actualResults);
    assertEquals(0, actualResults.size());
  }

  @Test
  void apply_3x_twoFound() {
    String[][] matrix = {{"X", "O", "Z"}, {"A", "X", "Z"}, {"O", "Z", "X"}};
    when(scratchCard.getMatrix()).thenReturn(matrix);

    var expectedResults = Set.of("Z", "X");
    var actualResults = new SameSymbolsWinCombination(1, "same_symbols", 3).apply(scratchCard);

    assertNotNull(actualResults);
    assertEquals(expectedResults, actualResults);
  }

  @Test
  void apply_4x_oneFound() {
    String[][] matrix = {{"X", "X", "A"}, {"A", "X", "B"}, {"E", "X", "C"}};
    when(scratchCard.getMatrix()).thenReturn(matrix);

    var expectedResults = Set.of("X");
    var actualResults = new SameSymbolsWinCombination(1.5, "same_symbols", 4).apply(scratchCard);

    assertNotNull(actualResults);
    assertEquals(expectedResults, actualResults);
  }

}
