package com.cyberspeed.scratchgame.probabilities;

import static com.cyberspeed.scratchgame.utils.Validation.isNonEmptyMap;
import static com.cyberspeed.scratchgame.utils.Validation.isPositive;

import com.cyberspeed.scratchgame.ScratchCard;
import com.cyberspeed.scratchgame.exceptions.GameException;
import java.util.Map;

/**
 * Record that represents the probability of each Standard symbol appearing in specific cell of {@link ScratchCard}
 * matrix.
 *
 * @param row     {@link ScratchCard} row for which the symbol probability is given
 * @param column  {@link ScratchCard} column for which the symbol probability is given
 * @param symbols map of Standard symbol to its corresponding probability
 */
public record StandardSymbolsProbability(int row, int column, Map<String, Integer> symbols) {

  /**
   * Constructor which validates symbol probability map and create {@link StandardSymbolsProbability}
   *
   * @throws GameException If row or column are not positive, or symbols map is null or empty, or the symbol probability
   *                       is not positive.
   */
  public StandardSymbolsProbability {
    // validate row is positive
    isPositive(row, "Standard Symbol probability row must be positive");

    // validate column is positive
    isPositive(column, "Standard Symbol probability row must be positive");

    // Ensure probability map is not null or empty as it is needed for populating Scratch Card matrix
    isNonEmptyMap(symbols, "Symbol probability map cannot be null or empty");

    // Probability needs to be positive in order to determine symbol's chances to appear on the Scratch Card matrix
    symbols.forEach((s, p) -> isPositive(p, "Probability must be positive: " + p));
  }

}
