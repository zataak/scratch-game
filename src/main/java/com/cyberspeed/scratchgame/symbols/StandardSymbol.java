package com.cyberspeed.scratchgame.symbols;

import static com.cyberspeed.scratchgame.utils.Validation.isGreaterThanZero;

import com.cyberspeed.scratchgame.exceptions.GameException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class that represent attributes of a Standard symbol.
 */
public class StandardSymbol extends Symbol {

  /**
   * Constructor that validates the attributes of the Bonus symbol and create a {@link StandardSymbol}
   *
   * @param rewardMultiplier the multiplier to be applied to the reward
   * @throws GameException if rewardMultiplier is not greater than zero, or extra is not positive, or impact is null or
   *                       empty
   */
  @JsonCreator
  public StandardSymbol(@JsonProperty("reward_multiplier") double rewardMultiplier) {
    super(rewardMultiplier, "standard");

    // in parent class we are only checking that rewardMultiplier is positive
    // here we need to check it is greater than 0 as well.
    isGreaterThanZero(rewardMultiplier, "reward_multiplier must be greater than 0, but was: " + rewardMultiplier);
  }
}

