package com.cyberspeed.scratchgame.wincombinations;

import static com.cyberspeed.scratchgame.utils.Validation.isGreaterThanZero;
import static com.cyberspeed.scratchgame.utils.Validation.isNotBlank;

import com.cyberspeed.scratchgame.ScratchCard;
import com.cyberspeed.scratchgame.exceptions.GameException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.Set;
import lombok.Getter;

/**
 * Base class of {@link SameSymbolsWinCombination} and {@link LinearSymbolsWinCombination}
 */
@Getter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "when", visible = true)
@JsonSubTypes({@JsonSubTypes.Type(value = SameSymbolsWinCombination.class, name = "same_symbols"),
  @JsonSubTypes.Type(value = LinearSymbolsWinCombination.class, name = "linear_symbols")})
public abstract class WinCombination {

  private final String when;
  private final String group;
  @JsonProperty("reward_multiplier")
  private double rewardMultiplier;

  /**
   * Constructor that validates and initialize some of a win combination
   *
   * @param rewardMultiplier the multiplier to be applied to the reward
   * @param when             defines the type of win combination (e.g. same_symbols, linear_symbols)
   * @param group            name of the group to put win condition in same group
   * @throws GameException if rewardMultiplier is not greater than zero, or when is null or empty, or group is null or
   *                       empty
   */
  @JsonCreator
  public WinCombination(@JsonProperty("reward_multiplier") double rewardMultiplier, @JsonProperty("when") String when,
    @JsonProperty("group") String group) {
    this.rewardMultiplier = rewardMultiplier;
    this.when = when;
    this.group = group;

    isGreaterThanZero(rewardMultiplier, "reward_multiplier must be greater than 0, but was: " + rewardMultiplier);
    isNotBlank(when, "when cannot be null or empty");
    isNotBlank(group, "group cannot be null or empty");
  }

  /**
   * Apply win combination to the {@link ScratchCard} matrix
   *
   * @param scratchCard the contains the symbol matrix
   * @return set of all symbols which fulfills criteria of this win combination
   */

  public abstract Set<String> apply(ScratchCard scratchCard);
}
