package com.cyberspeed.scratchgame;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.cyberspeed.scratchgame.utils.RewardCalculator;
import com.cyberspeed.scratchgame.utils.WinCombinationFinder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.ReflectionUtils;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ScratchGameTest extends GameTestBase {

  private ScratchGame scratchGame;
  @Mock
  private ScratchCard scratchCard;
  @Mock
  private WinCombinationFinder winCombinationFinder;
  @Mock
  private RewardCalculator rewardCalculator;

  @BeforeEach
  public void setup() throws IllegalAccessException {
    scratchGame = new ScratchGame(gameConfig);
    setPrivateField(scratchGame, "scratchCard", scratchCard);
    setPrivateField(scratchGame, "winCombinationFinder", winCombinationFinder);
    setPrivateField(scratchGame, "rewardCalculator", rewardCalculator);
  }

  @Test
  void play_verifyOutput() {
    String[][] matrix = {{"A", "A", "A"}, {"X", "B", "X"}, {"B", "B", "Y"}};
    Map<String, Set<String>> winningCombinations = new HashMap<>();

    when(scratchCard.getMatrix()).thenReturn(matrix);
    when(scratchCard.getBonusSymbol()).thenReturn("+1000");
    when(scratchCard.getMatrix()).thenReturn(matrix);
    when(winCombinationFinder.find(scratchCard, gameConfig.winCombinations())).thenReturn(winningCombinations);
    when(rewardCalculator.calculate(gameConfig, 100, "+1000", winningCombinations)).thenReturn(2500.00);

    var actualGameOutput = scratchGame.play(100);

    verify(scratchCard).getMatrix();
    verify(winCombinationFinder).find(scratchCard, gameConfig.winCombinations());
    verify(rewardCalculator).calculate(gameConfig, 100, "+1000", winningCombinations);
    verify(scratchCard, times(2)).getBonusSymbol(); //

    assertNotNull(actualGameOutput);
    assertEquals(2500, actualGameOutput.getReward());
    assertEquals(matrix, actualGameOutput.getMatrix());
    assertEquals(winningCombinations, actualGameOutput.getAppliedWinningCombinations());
    assertEquals("+1000", actualGameOutput.getAppliedBonusSymbol());

    System.out.println();
  }

  private void setPrivateField(ScratchGame scratchGame, String fieldName, Object newField)
    throws IllegalAccessException {
    var field = ReflectionUtils.findFields(ScratchGame.class, f -> f.getName().equals(fieldName),
      ReflectionUtils.HierarchyTraversalMode.TOP_DOWN).get(0);

    field.setAccessible(true);
    field.set(scratchGame, newField);
  }
}
