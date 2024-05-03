package com.cyberspeed.scratchgame.probabilities;

import static com.cyberspeed.scratchgame.utils.Validation.isNonEmptyMap;
import static com.cyberspeed.scratchgame.utils.Validation.isPositive;

import com.cyberspeed.scratchgame.ScratchCard;
import com.cyberspeed.scratchgame.exceptions.GameException;
import java.util.Map;

/**
 * Record that represents the probability of each Bonus symbol appearing on the {@link ScratchCard}.
 *
 * @param symbols map of Bonus symbol to its corresponding probability
 */
public record BonusSymbolsProbability(Map<String, Integer> symbols) {

  /**
   * Constructor which validates symbol probability map and create {@link BonusSymbolsProbability}
   *
   * @throws GameException If symbols map is null or empty, or the symbol probability is not positive.
   */
  public BonusSymbolsProbability {
    // Ensure probability map is not null or empty as it is needed for populating Scratch Card matrix
    isNonEmptyMap(symbols, "symbols cannot be null or empty");

    // Probability needs to be positive in order to determine symbol's chances to appear on the Scratch Card matrix
    symbols.forEach((s, p) -> isPositive(p, "Probability must be positive: " + p));
  }

}
