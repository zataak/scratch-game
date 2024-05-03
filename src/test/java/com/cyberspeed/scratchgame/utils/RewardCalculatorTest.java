package com.cyberspeed.scratchgame.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.cyberspeed.scratchgame.GameTestBase;
import com.cyberspeed.scratchgame.exceptions.GameException;
import com.cyberspeed.scratchgame.symbols.BonusSymbol;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class RewardCalculatorTest extends GameTestBase {

  private static final int BET_AMOUNT = 100;
  private static final RewardCalculator REWARD_CALCULATOR = new RewardCalculator();

  @Test
  void calculateReward_noWinCombination_withMultiplyBonus() {
    String bonusSymbol = "5x";
    var winningCombinations = Collections.<String, Set<String>>emptyMap();

    var expectedRewardAmount = 0;
    var actualRewardAmount = REWARD_CALCULATOR.calculate(gameConfig, BET_AMOUNT, bonusSymbol, winningCombinations);

    assertEquals(expectedRewardAmount, actualRewardAmount);
  }

  @Test
  void calculateReward_oneSymbol_oneWinCombination_withMultiplyBonus() {
    String bonusSymbol = "10x";
    var winningCombinations = Map.of("B", Set.of("same_symbol_5_times"));

    // symbol A reward = 100 x 25 x 2 = 5000
    // total reward = (5000) x 10 = 31000

    var expectedRewardAmount = 50000;
    var actualRewardAmount = REWARD_CALCULATOR.calculate(gameConfig, BET_AMOUNT, bonusSymbol, winningCombinations);

    assertEquals(expectedRewardAmount, actualRewardAmount);
  }

  @Test
  void calculateReward_oneSymbol_twoWinCombination_withExtraBonus() {
    String bonusSymbol = "+1000";
    var winningCombinations = Map.of("A", Set.of("same_symbol_6_times", "same_symbols_horizontally"));

    // symbol A reward = 100 x 50 x 3 x 2 = 30000
    // total reward = (30000) + 1000 = 31000

    var expectedRewardAmount = 31000;
    var actualRewardAmount = REWARD_CALCULATOR.calculate(gameConfig, BET_AMOUNT, bonusSymbol, winningCombinations);

    assertEquals(expectedRewardAmount, actualRewardAmount);
  }

  @Test
  void calculateReward_oneSymbol_threeWinCombination_withExtraBonus() {
    String bonusSymbol = "+1000";
    var winningCombinations = Map.of("A",
      Set.of("same_symbol_8_times", "same_symbols_horizontally", "same_symbols_vertically"));

    // symbol A reward = 100 x 50 x 10 x 2 x 2 = 200000
    // total reward = (200000) + 1000 = 201000

    var expectedRewardAmount = 201000;
    var actualRewardAmount = REWARD_CALCULATOR.calculate(gameConfig, BET_AMOUNT, bonusSymbol, winningCombinations);

    assertEquals(expectedRewardAmount, actualRewardAmount);
  }

  @Test
  void calculateReward_twoSymbols_twoWinCombinationEach_withMissBonus() {
    String bonusSymbol = "MISS";
    var winningCombinations = Map.of("E", Set.of("same_symbol_4_times", "same_symbols_horizontally"), "F",
      Set.of("same_symbol_3_times", "same_symbols_vertically"));

    // symbol E reward = 100 x 3 x 1.5 x 2 = 900
    // symbol F reward = 100 x 1.5 x 1 x 2 = 300
    // total reward = (900 + 300) + 0 = 1200

    var expectedRewardAmount = 1200;
    var actualRewardAmount = REWARD_CALCULATOR.calculate(gameConfig, BET_AMOUNT, bonusSymbol, winningCombinations);

    assertEquals(expectedRewardAmount, actualRewardAmount);
  }

  @Test
  void calculateReward_twoSymbols_twoWinCombinationEach_withExtraBonus() {
    String bonusSymbol = "+1000";
    var winningCombinations = Map.of("A", Set.of("same_symbol_5_times", "same_symbols_vertically"), "B",
      Set.of("same_symbol_3_times", "same_symbols_vertically"));

    // symbol A reward = 100 x 50 x 2 x 2 = 20000
    // symbol B reward = 100 x 25 x 1 x 2 = 500
    // total reward = (20000 + 5000) + 1000 = 26000

    var expectedRewardAmount = 26000;
    var actualRewardAmount = REWARD_CALCULATOR.calculate(gameConfig, BET_AMOUNT, bonusSymbol, winningCombinations);

    assertEquals(expectedRewardAmount, actualRewardAmount);
  }

  @Test
  void calculateReward_throwsException_whenBonusSymbolImpact() {
    var bonusSymbol = "new-bonus-symbol";
    var winningCombinations = Map.of("A", Set.of("same_symbol_5_times"));

    gameConfig.symbols().put(bonusSymbol, new BonusSymbol(10, 0, "unknown-impact"));

    var actualException = assertThrows(GameException.class,
      () -> REWARD_CALCULATOR.calculate(gameConfig, BET_AMOUNT, bonusSymbol, winningCombinations));

    assertEquals("Unknown bonus symbol impact: unknown-impact", actualException.getMessage());
  }
}
