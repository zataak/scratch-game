package com.cyberspeed.scratchgame.symbols;

import static com.cyberspeed.scratchgame.utils.Validation.isNotBlank;
import static com.cyberspeed.scratchgame.utils.Validation.isPositive;

import com.cyberspeed.scratchgame.exceptions.GameException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;

/**
 * Base class of {@link BonusSymbol} and {@link StandardSymbol}
 */
@Getter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true)
@JsonSubTypes({@JsonSubTypes.Type(value = StandardSymbol.class, name = "standard"),
  @JsonSubTypes.Type(value = BonusSymbol.class, name = "bonus")})
public abstract class Symbol {

  private final double rewardMultiplier;
  private final String type;

  /**
   * Constructor that validates and initialize the attributes of a symbol
   *
   * @param rewardMultiplier the multiplier to be applied to the reward
   * @param type             the type of the symbol (e.g. bonus or standard)
   * @throws GameException if rewardMultiplier is not positive, or type is null or empty
   */
  @JsonCreator
  public Symbol(@JsonProperty("reward_multiplier") double rewardMultiplier, @JsonProperty("type") String type) {
    this.rewardMultiplier = rewardMultiplier;
    this.type = type;

    // although `rewardMultiplier` must be greater than 0, but we cannot perform `Validation.isNonZeroPositive`
    // check here because it would affect the BonusSymbol with 'extra' reward and does not have `rewardMultiplier`
    // In such case a child class (such ass StandSymbol) can perform `Validation.isNonZeroPositive` itself
    isPositive(rewardMultiplier, "reward_multiplier must be positive, but was: " + rewardMultiplier);
    isNotBlank(type, "type cannot be null or empty");
  }
}
