package com.cyberspeed.scratchgame.configs;

import static com.cyberspeed.scratchgame.utils.Validation.isNonEmptyMap;
import static com.cyberspeed.scratchgame.utils.Validation.isNotNull;

import com.cyberspeed.scratchgame.ScratchCard;
import com.cyberspeed.scratchgame.ScratchGame;
import com.cyberspeed.scratchgame.exceptions.GameException;
import com.cyberspeed.scratchgame.probabilities.Probability;
import com.cyberspeed.scratchgame.symbols.Symbol;
import com.cyberspeed.scratchgame.utils.Validation;
import com.cyberspeed.scratchgame.wincombinations.WinCombination;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

/**
 * Configuration of the game, which holds the dimension of the scratch card matrix, the game symbols, probabilities for
 * symbol appearance, and the win combinations.
 *
 * @param rows            number of {@link ScratchCard} matrix rows
 * @param columns         number of {@link ScratchCard} matrix columns
 * @param symbols         game symbols to be populated in {@link ScratchCard} matrix
 * @param probabilities   of each symbol being populated in {@link ScratchCard} matrix
 * @param winCombinations that represent {@link ScratchGame} wining conditions
 */
public record GameConfig(int rows, int columns, Map<String, Symbol> symbols, Probability probabilities,
                         @JsonProperty("win_combinations") Map<String, WinCombination> winCombinations) {

  /**
   * Constructor which validates configuration before creating {@link GameConfig}
   *
   * @throws GameException If the matrix size is not positive, or symbols are null or empty, or probabilities is null,
   *                       or win combinations are null or empty.
   */
  public GameConfig {
    // validate rows and columns
    Validation.isPositive(rows, "rows must be positive: " + rows);
    Validation.isPositive(columns, "columns must be positive: " + rows);

    // validate symbols
    isNonEmptyMap(symbols, "symbols cannot be null or empty");

    // validate probabilities
    isNotNull(probabilities, "probabilities cannot be null");

    // validate winCombinations
    isNonEmptyMap(winCombinations, "win_combinations cannot be null or empty");
  }
}
