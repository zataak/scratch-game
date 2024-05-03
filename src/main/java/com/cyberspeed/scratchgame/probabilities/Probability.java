package com.cyberspeed.scratchgame.probabilities;

import static com.cyberspeed.scratchgame.utils.Validation.isNonEmptyCollection;
import static com.cyberspeed.scratchgame.utils.Validation.isNotNull;

import com.cyberspeed.scratchgame.ScratchCard;
import com.cyberspeed.scratchgame.exceptions.GameException;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Record that holds the probabilities for both Standard and Bonus symbols
 *
 * @param standardSymbolsProbabilities list of Standard symbol probabilities for each cell of {@link ScratchCard}
 *                                     matrix
 * @param bonusSymbolsProbability      Bonus symbol probabilities
 */
public record Probability(
  @JsonProperty("standard_symbols") List<StandardSymbolsProbability> standardSymbolsProbabilities,
  @JsonProperty("bonus_symbols") BonusSymbolsProbability bonusSymbolsProbability) {

  /**
   * Constructor which validates symbol probabilities and create {@link Probability}
   *
   * @throws GameException If standardSymbolsProbabilities list is null or empty, or symbols is null
   */
  public Probability {
    isNonEmptyCollection(standardSymbolsProbabilities, "standard_symbols cannot be null or empty");
    isNotNull(bonusSymbolsProbability, "bonus_symbols cannot be null");
  }

}
