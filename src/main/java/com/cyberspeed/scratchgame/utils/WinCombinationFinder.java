package com.cyberspeed.scratchgame.utils;

import static com.cyberspeed.scratchgame.utils.Validation.isNonEmptyMap;
import static com.cyberspeed.scratchgame.utils.Validation.isNotNull;

import com.cyberspeed.scratchgame.ScratchCard;
import com.cyberspeed.scratchgame.exceptions.GameException;
import com.cyberspeed.scratchgame.wincombinations.WinCombination;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Utility class with a method to apply win combinations to {@link ScratchCard}
 */
public record WinCombinationFinder() {

  /**
   * Applies all the provided {@link WinCombination} to {@link ScratchCard} to determine which symbols on the Scratch
   * Card matrix are placed according to the win combination.
   *
   * @param scratchCard          the Scratch card to apply win combinations to
   * @param winCombinationConfig map of win combinations to apply on Scratch card
   * @return map of symbol and list of all the win combinations name that are applied successfully
   * @throws GameException if {@link ScratchCard} is null or winCombinationConfig is empty
   */
  public Map<String, Set<String>> find(ScratchCard scratchCard, Map<String, WinCombination> winCombinationConfig) {
    isNotNull(scratchCard, "Game Scratch Card cannot be null");
    isNonEmptyMap(winCombinationConfig, "Win combination cannot be null or empty");

    // map between a symbol and it applied win combinations name
    var winningCombination = new HashMap<String, Set<String>>();

    // Iterate through win combinations
    winCombinationConfig.forEach((combinationName, winCombination) -> {
      // get list of all the symbols which meets the criteria of this win combination
      var winningCombinations = winCombination.apply(scratchCard);
      // prepares a map where key is the symbol and value is a set of applied win combination name
      winningCombinations.forEach(
        symbol -> winningCombination.computeIfAbsent(symbol, s -> new HashSet<>()).add(combinationName));
    });

    return winningCombination;
  }

}
