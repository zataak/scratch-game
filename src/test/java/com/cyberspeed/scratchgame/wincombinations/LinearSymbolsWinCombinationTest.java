package com.cyberspeed.scratchgame.wincombinations;

import static com.cyberspeed.scratchgame.utils.Validation.isNonEmptyCollection;
import static com.cyberspeed.scratchgame.utils.Validation.isGreaterThanZero;
import static com.cyberspeed.scratchgame.utils.Validation.isNotBlank;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import com.cyberspeed.scratchgame.ScratchCard;
import com.cyberspeed.scratchgame.utils.Validation;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LinearSymbolsWinCombinationTest {

  private static final List<List<String>> HORIZONTAL_COVERED_AREAS = List.of(List.of("0:0", "0:1", "0:2"),
    List.of("1:0", "1:1", "1:1"), List.of("2:0", "2:1", "2:2"));
  private static final List<List<String>> VERTICAL_COVERED_AREAS = List.of(List.of("0:0", "1:0", "2:0"),
    List.of("0:1", "1:1", "2:1"), List.of("0:2", "1:2", "2:2"));
  private static final List<List<String>> LTR_DIAGONAL_COVERED_AREAS = List.of(List.of("0:0", "1:1", "2:2"));
  private static final List<List<String>> RTL_DIAGONAL_COVERED_AREAS = List.of(List.of("0:2", "1:1", "2:0"));

  @Mock
  ScratchCard scratchCard;

  @Test
  void constructsCorrectly() {
    var linearSymbolsWinCombination = new LinearSymbolsWinCombination(2, "horizontal", HORIZONTAL_COVERED_AREAS);
    assertEquals(2, linearSymbolsWinCombination.getRewardMultiplier());
    assertEquals("linear_symbols", linearSymbolsWinCombination.getWhen());
    assertEquals("horizontal", linearSymbolsWinCombination.getGroup());
    assertEquals(HORIZONTAL_COVERED_AREAS, linearSymbolsWinCombination.getCoveredAreas());
  }

  @Test
  void verifyValidations() {
    try (MockedStatic<Validation> validation = Mockito.mockStatic(Validation.class)) {
      new LinearSymbolsWinCombination(2, "horizontal", HORIZONTAL_COVERED_AREAS);
      // validate rewardMultiplier
      validation.verify(() -> isGreaterThanZero(anyDouble(), anyString()), times(1));
      // validate when and group
      validation.verify(() -> isNotBlank(anyString(), anyString()), times(2));
      // validate horizontalCoveredAreas (4)
      validation.verify(() -> isNonEmptyCollection(anyList(), anyString()), times(4));
    }
  }

  @Test
  void apply_horizontalCoveredAreas_noneFound() {
    String[][] matrix = {{"A", "B", "C"}, {"D", "E", "F"}, {"A", "B", "C"}};
    when(scratchCard.getMatrix()).thenReturn(matrix);

    var actualResults = new LinearSymbolsWinCombination(2, "horizontal", HORIZONTAL_COVERED_AREAS).apply(scratchCard);

    assertNotNull(actualResults);
    assertTrue(actualResults.isEmpty());
  }

  @Test
  void apply_horizontalCoveredAreas_oneFound() {
    String[][] matrix = {{"X", "O", "Z"}, {"O", "X", "Z"}, {"Z", "Z", "Z"}};
    when(scratchCard.getMatrix()).thenReturn(matrix);

    var expectedResults = Set.of("Z");
    var actualResults = new LinearSymbolsWinCombination(2, "horizontal", HORIZONTAL_COVERED_AREAS).apply(scratchCard);

    assertNotNull(actualResults);
    assertEquals(expectedResults, actualResults);
  }

  @Test
  void apply_horizontalCoveredAreas_twoFound() {
    String[][] matrix = {{"X", "X", "X"}, {"O", "X", "Z"}, {"Z", "Z", "Z"}};
    when(scratchCard.getMatrix()).thenReturn(matrix);

    var expectedResults = Set.of("X", "Z");
    var actualResults = new LinearSymbolsWinCombination(2, "horizontal", HORIZONTAL_COVERED_AREAS).apply(scratchCard);

    assertNotNull(actualResults);
    assertEquals(expectedResults, actualResults);
  }

  @Test
  void apply_verticalCoveredAreas_oneFound() {
    String[][] matrix = {{"X", "O", "Z"}, {"O", "X", "Z"}, {"O", "X", "Z"}};
    when(scratchCard.getMatrix()).thenReturn(matrix);

    var expectedResults = Set.of("Z");
    var actualResults = new LinearSymbolsWinCombination(2, "vertical", VERTICAL_COVERED_AREAS).apply(scratchCard);

    assertNotNull(actualResults);
    assertEquals(expectedResults, actualResults);
  }

  @Test
  void apply_ltrDiagonalCoveredAreas_oneFound() {
    String[][] matrix = {{"X", "O", "O"}, {"O", "X", "O"}, {"O", "O", "X"}};
    when(scratchCard.getMatrix()).thenReturn(matrix);

    var expectedResults = Set.of("X");
    var actualResults = new LinearSymbolsWinCombination(5, "diagonal", LTR_DIAGONAL_COVERED_AREAS).apply(scratchCard);

    assertNotNull(actualResults);
    assertEquals(expectedResults, actualResults);
  }

  @Test
  void apply_rtlDiagonalCoveredAreas_oneFound() {
    String[][] matrix = {{"O", "O", "Z"}, {"O", "Z", "O"}, {"Z", "O", "O"}};
    when(scratchCard.getMatrix()).thenReturn(matrix);

    var expectedResults = Set.of("Z");
    var actualResults = new LinearSymbolsWinCombination(5, "diagonal", RTL_DIAGONAL_COVERED_AREAS).apply(scratchCard);

    assertNotNull(actualResults);
    assertEquals(expectedResults, actualResults);
  }
}
