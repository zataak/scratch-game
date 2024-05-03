package com.cyberspeed.scratchgame.symbols;

import static com.cyberspeed.scratchgame.utils.Validation.isGreaterThanZero;
import static com.cyberspeed.scratchgame.utils.Validation.isNotBlank;
import static com.cyberspeed.scratchgame.utils.Validation.isPositive;

import com.cyberspeed.scratchgame.exceptions.GameException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

/**
 * Class that represent attributes of a Bonus symbol.
 */
@Getter
public class BonusSymbol extends Symbol {

  private final int extra;
  private final String impact;

  /**
   * Constructor that validates the attributes of the Bonus symbol and create a {@link BonusSymbol}
   *
   * @param rewardMultiplier the multiplier to be applied to the reward
   * @param extra            additional amount to be added to the reward
   * @param impact           an impact of the symbol on the reward (e.g. 'multiply_reward', 'extra_bonus', 'miss')
   * @throws GameException if rewardMultiplier is not greater than zero, or extra is not positive, or impact is null or
   *                       empty
   */
  @JsonCreator
  public BonusSymbol(@JsonProperty("reward_multiplier") double rewardMultiplier, @JsonProperty("extra") int extra,
    @JsonProperty("impact") String impact) {
    super(rewardMultiplier, "bonus");
    this.extra = extra;
    this.impact = impact;

    isPositive(extra, "extra must be positive and greater than 0, but was: " + extra);
    isNotBlank(impact, "impact cannot be null or empty");

    // `multiply_reward` bonus must contain greater than 0 `reward_multiplier`
    // in parent class we are only checking that rewardMultiplier is positive
    // here we need to check it is greater than 0 as well.
    if ("multiply_reward".equals(impact)) {
      isGreaterThanZero(rewardMultiplier, "reward_multiplier must be greater than 0, but was: " + rewardMultiplier);
    }
  }
}
