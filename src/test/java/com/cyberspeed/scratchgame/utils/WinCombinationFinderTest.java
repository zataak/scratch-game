package com.cyberspeed.scratchgame.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import com.cyberspeed.scratchgame.GameTestBase;
import com.cyberspeed.scratchgame.ScratchCard;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class WinCombinationFinderTest extends GameTestBase {

  private static final WinCombinationFinder winCombinationFinder = new WinCombinationFinder();

  @Mock
  ScratchCard scratchCard;

  @Test
  void find_noWinCombinationFound() {
    String[][] matrix = {{"A", "B", "C"}, {"E", "B", "5x"}, {"F", "D", "C"}};
    when(scratchCard.getMatrix()).thenReturn(matrix);

    var actualWiningCombinations = winCombinationFinder.find(scratchCard, gameConfig.winCombinations());

    assertNotNull(actualWiningCombinations);
    assertEquals(0, actualWiningCombinations.size());
  }

  @Test
  void find_oneSymbol_twoWinCombinationFound() {
    String[][] matrix = {{"A", "A", "A"}, {"X", "Y", "Z"}, {"A", "A", "A"}};
    when(scratchCard.getMatrix()).thenReturn(matrix);

    var expectedWiningCombinationForSymbolA = Set.of("same_symbol_6_times", "same_symbols_horizontally");
    var actualWiningCombinations = winCombinationFinder.find(scratchCard, gameConfig.winCombinations());

    assertNotNull(actualWiningCombinations);
    assertEquals(1, actualWiningCombinations.size());
    assertSymbolWinCombinations(expectedWiningCombinationForSymbolA, actualWiningCombinations.get("A"));
  }

  @Test
  void find_oneSymbol_threeWinCombinationFound() {
    String[][] matrix = {{"A", "A", "A"}, {"X", "Y", "A"}, {"B", "B", "A"}};
    when(scratchCard.getMatrix()).thenReturn(matrix);

    var expectedWiningCombinationForSymbolA = Set.of("same_symbol_5_times", "same_symbols_horizontally",
      "same_symbols_vertically");
    var actualWiningCombinations = winCombinationFinder.find(scratchCard, gameConfig.winCombinations());

    assertNotNull(actualWiningCombinations);
    assertEquals(1, actualWiningCombinations.size());
    assertSymbolWinCombinations(expectedWiningCombinationForSymbolA, actualWiningCombinations.get("A"));
  }

  @Test
  void find_twoSymbol_threeWinCombinationFound() {
    String[][] matrix = {{"A", "B", "B"}, {"X", "A", "X"}, {"B", "C", "A"}};
    when(scratchCard.getMatrix()).thenReturn(matrix);

    var expectedWiningCombinationForSymbolA = Set.of("same_symbol_3_times", "same_symbols_diagonally_left_to_right");
    var expectedWiningCombinationForSymbolB = Set.of("same_symbol_3_times");
    var actualWiningCombinations = winCombinationFinder.find(scratchCard, gameConfig.winCombinations());

    assertNotNull(actualWiningCombinations);
    assertEquals(2, actualWiningCombinations.size());
    assertSymbolWinCombinations(expectedWiningCombinationForSymbolA, actualWiningCombinations.get("A"));
    assertSymbolWinCombinations(expectedWiningCombinationForSymbolB, actualWiningCombinations.get("B"));
  }

  @Test
  void find_oneSymbol_fiveWinCombinationFound_matrixWithOnlyOneSymbol() {
    String[][] matrix = {{"A", "A", "A"}, {"A", "A", "A"}, {"A", "A", "A"}};
    when(scratchCard.getMatrix()).thenReturn(matrix);

    var expectedWiningCombinationForSymbolA = Set.of("same_symbol_9_times", "same_symbols_horizontally",
      "same_symbols_vertically", "same_symbols_diagonally_left_to_right", "same_symbols_diagonally_right_to_left");
    var actualWiningCombinations = winCombinationFinder.find(scratchCard, gameConfig.winCombinations());

    assertNotNull(actualWiningCombinations);
    assertEquals(1, actualWiningCombinations.size());
    assertSymbolWinCombinations(expectedWiningCombinationForSymbolA, actualWiningCombinations.get("A"));
  }

  private void assertSymbolWinCombinations(Set<String> expectedWinCombination, Set<String> actualWinCombinations) {
    assertNotNull(actualWinCombinations);
    assertEquals(expectedWinCombination, actualWinCombinations);
  }

}
