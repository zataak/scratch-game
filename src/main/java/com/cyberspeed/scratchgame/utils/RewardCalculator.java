package com.cyberspeed.scratchgame.utils;

import static com.cyberspeed.scratchgame.utils.Validation.isGreaterThanZero;
import static com.cyberspeed.scratchgame.utils.Validation.isNotNull;

import com.cyberspeed.scratchgame.ScratchCard;
import com.cyberspeed.scratchgame.configs.GameConfig;
import com.cyberspeed.scratchgame.exceptions.GameException;
import com.cyberspeed.scratchgame.symbols.BonusSymbol;
import java.util.Map;
import java.util.Set;
import org.jetbrains.annotations.Nullable;

/**
 * Responsible for calculating the total reward amount
 */
public class RewardCalculator {

  /**
   * Calculates the total reward based of betting amount, bonus symbol and standard symbols win combinations.
   *
   * @param gameConfig             configuration of the game
   * @param bettingAmount          the amount placed on the bet
   * @param bonusSymbol            the bonus symbol appeared on the {@link ScratchCard} matrix or null if didn't appear
   * @param appliedWinCombinations map of Standard symbol and win combination name.
   * @return the total reward calculated. Total reward is 0 if appliedWinCombinations is empty
   * @throws GameException if bonusAmount is not greater than 0, or win combinations is null, or game configuration is
   *                       null
   */
  public double calculate(GameConfig gameConfig, int bettingAmount, @Nullable String bonusSymbol,
    Map<String, Set<String>> appliedWinCombinations) {

    // ensure game config is not null
    isNotNull(gameConfig, "Game config cannot be null");
    // ensure betting amount is valid
    isGreaterThanZero(bettingAmount, "Betting amount must be greater than zero");
    // ensure appliedWinCombinations is not null, it can be empty
    isNotNull(appliedWinCombinations, "Applied win combination cannot be null");

    var finalReward = 0.0;

    // final reward is 0 if there are no applied win combinations
    if (appliedWinCombinations.isEmpty()) {
      return finalReward;
    }

    /*
     * Calculate reward based on win combination
     */

    // iterate over applies win combinations map to determine involved symbols and win combinations to calculate total reward
    for (Map.Entry<String, Set<String>> entry : appliedWinCombinations.entrySet()) {
      // get reward multiplier of the symbol from the game configuration and set to the current symbol reward
      var symbolReward = gameConfig.symbols().get(entry.getKey()).getRewardMultiplier();

      // now iterate over all the symbol win combinations
      for (String combinationName : entry.getValue()) {
        // get reward multiplier of the win combination from the game configuration
        var combinationReward = gameConfig.winCombinations().get(combinationName).getRewardMultiplier();
        // multiply current symbol reward with win combination reward multiplier and update current symbol reward with the result
        symbolReward *= combinationReward;
      }

      // multiply current symbol reward with betting amount and update current symbol reward with the result
      symbolReward *= bettingAmount;
      // add current symbol reward to final reward and update final reward with the result
      finalReward += symbolReward;
      // continue loop to update final reward with the next symbol and its win combination reward
    }

    /*
     * Apply bonus symbol reward if it is available
     */

    if (bonusSymbol != null) {
      // get bonus symbol configuration
      var bonusSymbolConfig = (BonusSymbol) gameConfig.symbols().get(bonusSymbol);

      // based of symbol impact, multiply final reward with bonus symbol reward multiplier, or add extra amount to
      // final reward, or do nothing if impact is miss
      return switch (bonusSymbolConfig.getImpact()) {
        case "multiply_reward" -> finalReward * bonusSymbolConfig.getRewardMultiplier();
        case "extra_bonus" -> finalReward + bonusSymbolConfig.getExtra();
        case "miss" -> finalReward; // no change to finalReward
        default -> throw new GameException("Unknown bonus symbol impact: " + bonusSymbolConfig.getImpact());
      };
    }

    // calculated total reward
    return finalReward;
  }

}
