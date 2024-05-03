package com.cyberspeed.scratchgame.wincombinations;

import static com.cyberspeed.scratchgame.utils.Validation.checkLength;
import static com.cyberspeed.scratchgame.utils.Validation.isNonEmptyCollection;
import static com.cyberspeed.scratchgame.utils.Validation.isNotNull;
import static com.cyberspeed.scratchgame.utils.Validation.isWithinBounds;

import com.cyberspeed.scratchgame.ScratchCard;
import com.cyberspeed.scratchgame.exceptions.GameException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Getter;

/**
 * Represents a liner win combination that all the reward details associated to it
 */
@Getter
public class LinearSymbolsWinCombination extends WinCombination {

  private final List<List<String>> coveredAreas;

  /**
   * Constructor that validates attributes of {@link LinearSymbolsWinCombination} and create its instance
   *
   * @param rewardMultiplier the multiplier to be applied to the reward
   * @param group            the group this win combination belongs to (e.g. horizontally_linear_symbols,
   *                         vertically_linear_symbols, etc)
   * @param coveredAreas     represents cells on the {@link ScratchCard} matrix
   * @throws GameException if covered areas is null or empty
   */
  @JsonCreator
  public LinearSymbolsWinCombination(@JsonProperty("reward_multiplier") double rewardMultiplier,
    @JsonProperty("group") String group, @JsonProperty("covered_areas") List<List<String>> coveredAreas) {
    super(rewardMultiplier, "linear_symbols", group);
    this.coveredAreas = coveredAreas;

    isNonEmptyCollection(coveredAreas, "covered_areas cannot be null or empty");
    coveredAreas.forEach(area -> isNonEmptyCollection(area, "coordinates cannot be null or empty"));
  }


  /**
   * {@inheritDoc}
   * <p>
   * If a symbol appears in every cell of {@link ScratchCard} matrix defined as covered areas then win combination is
   * considered applied
   */
  @Override
  public Set<String> apply(ScratchCard scratchCard) {
    isNotNull(scratchCard, "Game Scratch Card cannot be null");
    return findSymbolsInCoveredAreas(scratchCard.getMatrix(), coveredAreas);
  }

  private Set<String> findSymbolsInCoveredAreas(String[][] matrix, List<List<String>> coveredAreas) {
    var symbols = new HashSet<String>();

    // iterate over list of all the covered areas
    for (List<String> coveredArea : coveredAreas) {
      var symbolsInCoveredArea = new HashSet<String>();

      // iterate over list of matrix coordinates in this covered area
      for (var area : coveredArea) {
        // split to get row and column number
        var coordinates = area.split(":");
        checkLength(coordinates, 2, "Incorrect coordinates: " + area);

        var row = Integer.parseInt(coordinates[0]);
        var col = Integer.parseInt(coordinates[1]);

        // ensure coordinates falls within matrix dimension
        isWithinBounds(row, col, matrix.length, matrix[0].length,
          "Coordinates [%d:%d] are not within bounds: ".formatted(row, col));

        symbolsInCoveredArea.add(matrix[row][col]);
      }

      // if same symbol has appeared in all matrix cells represented by this covered area then this symbol has fulfilled the criteria
      if (symbolsInCoveredArea.size() == 1) {
        symbols.add(symbolsInCoveredArea.stream().toList().get(0));
      }
    }

    // symbol(s) that were present in covered area(s)
    return symbols;
  }
}
