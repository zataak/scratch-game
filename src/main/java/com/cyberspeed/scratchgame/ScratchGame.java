package com.cyberspeed.scratchgame;

import static com.cyberspeed.scratchgame.utils.Validation.isNotNull;

import com.cyberspeed.scratchgame.configs.GameConfig;
import com.cyberspeed.scratchgame.exceptions.GameException;
import com.cyberspeed.scratchgame.models.GameOutput;
import com.cyberspeed.scratchgame.utils.RewardCalculator;
import com.cyberspeed.scratchgame.utils.WinCombinationFinder;
import com.cyberspeed.scratchgame.wincombinations.WinCombination;

/**
 * Scratch game that has a method to play the game. It takes the bettingAmount as input, find the winning combination,
 * calculates the rewards, and print the output
 */
public class ScratchGame {

  private final ScratchCard scratchCard;
  private final GameConfig gameConfig;
  private final RewardCalculator rewardCalculator;
  private final WinCombinationFinder winCombinationFinder;

  /**
   * Validates {@link GameConfig} and initialize its attributes
   *
   * @param gameConfig configuration of the game that is used to create {@link ScratchCard}, finding
   *                   {@link WinCombination} and calculating reward
   * @throws GameException if gameConfig is null
   */
  public ScratchGame(GameConfig gameConfig) {
    // GameConfig validates all its attributes before its creation.
    // A non-null gameConfig means it has all the elements that we need to create Scratch Card correctly
    isNotNull(gameConfig, "Config cannot be null");

    this.gameConfig = gameConfig;
    this.scratchCard = new ScratchCard(gameConfig);
    this.rewardCalculator = new RewardCalculator();
    this.winCombinationFinder = new WinCombinationFinder();
  }

  public GameOutput play(int bettingAmount) {
    // get bonus symbol
    var bonusSymbol = scratchCard.getBonusSymbol();

    // find wining combination
    var winningCombinations = winCombinationFinder.find(scratchCard, gameConfig.winCombinations());

    // calculate reward
    var finalReward = rewardCalculator.calculate(gameConfig, bettingAmount, bonusSymbol, winningCombinations);

    // build Game output
    var outputBuilder = GameOutput.builder().matrix(scratchCard.getMatrix()).reward((int) finalReward)
      .appliedWinningCombinations(winningCombinations);

    // only add bonus symbol if it is applied (i.e. not null or MISS) and there is a reward
    if (finalReward > 0 && bonusSymbol != null && !bonusSymbol.equalsIgnoreCase("miss")) {
      outputBuilder.appliedBonusSymbol(scratchCard.getBonusSymbol());
    }

    return outputBuilder.build();
  }
}
