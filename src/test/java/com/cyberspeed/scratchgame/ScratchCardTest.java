package com.cyberspeed.scratchgame;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ScratchCardTest extends GameTestBase {

  @Test
  void constructsGridCorrectly() {
    // Create a Grid object
    ScratchCard scratchCard = new ScratchCard(gameConfig);

    // assert matrix is of correct size
    var matrix = scratchCard.getMatrix();
    assertNotNull(matrix);
    assertEquals(3, matrix.length);
    assertEquals(3, matrix[0].length);

    // assert matrix is correctly populated
    var symbolsCount = Arrays.stream(matrix).flatMap(Arrays::stream).filter(ALL_SYMBOLS::containsKey).count();
    assertEquals(9, symbolsCount);

    var getProbabilitiesCount = scratchCard.getBonusSymbol() == null ? 1 : 2;

    // Verify config method calls
    verify(gameConfig).rows();
    verify(gameConfig).columns();
    verify(gameConfig, times(getProbabilitiesCount)).probabilities(); // one for each symbol
  }

  @Test
  void noTwoGridsAreSame() {
    var grid1 = new ScratchCard(gameConfig);
    var grid2 = new ScratchCard(gameConfig);

    assertNotEquals(grid1, grid2);
  }

}
