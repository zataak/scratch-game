package com.cyberspeed.scratchgame.wincombinations;

import static com.cyberspeed.scratchgame.utils.Validation.isGreaterThanZero;
import static com.cyberspeed.scratchgame.utils.Validation.isNotNull;

import com.cyberspeed.scratchgame.ScratchCard;
import com.cyberspeed.scratchgame.exceptions.GameException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;

/**
 * Represents a same symbols win combination that all the reward details associated to it
 */
@Getter
public class SameSymbolsWinCombination extends WinCombination {

  private final int count;

  /**
   * Constructor that validates attributes of {@link SameSymbolsWinCombination} and create its instance
   *
   * @param rewardMultiplier the multiplier to be applied to the reward
   * @param group            the group this win combination belongs to. There is only one group 'same_symbol' for this
   *                         win combination
   * @param count            exact number of symbol that should be present of the {@link ScratchCard} matrix
   * @throws GameException if count is not greater than 0
   */
  @JsonCreator
  public SameSymbolsWinCombination(@JsonProperty("reward_multiplier") double rewardMultiplier,
    @JsonProperty("group") String group, @JsonProperty("count") int count) {
    super(rewardMultiplier, "same_symbols", group);
    this.count = count;

    isGreaterThanZero(count, "count must be positive, but was: " + count);
  }

  /**
   * {@inheritDoc}
   * <p>
   * If number of Standard symbols on {@link ScratchCard} matrix is equals to count then SameSymbolsWinCombination is
   * considered as applied
   */
  @Override
  public Set<String> apply(ScratchCard scratchCard) {
    isNotNull(scratchCard, "Game Scratch Card cannot be null");

    // create a map between symbol and its count and then collect all symbols in to set if symbol count is equals to win combination count
    return Arrays.stream(scratchCard.getMatrix()).flatMap(Arrays::stream)
      .collect(Collectors.groupingBy(symbol -> symbol, Collectors.counting())).entrySet().stream()
      .filter(entry -> entry.getValue() == count).map(Map.Entry::getKey).collect(Collectors.toSet());
  }
}
