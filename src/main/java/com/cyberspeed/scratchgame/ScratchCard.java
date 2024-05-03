package com.cyberspeed.scratchgame;

import static com.cyberspeed.scratchgame.utils.Validation.isNotNull;

import com.cyberspeed.scratchgame.configs.GameConfig;
import com.cyberspeed.scratchgame.exceptions.GameException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;

/**
 * Scratch card contains a matrix to hold symbols. Symbols are selected randomly according to their probability
 * configured in {@link GameConfig}. There can at most one bonus symbol which may appear randomly.
 */
@EqualsAndHashCode
public class ScratchCard {

  private static final int BONUS_GENERATION_PROBABILITY_PERCENTAGE = 75;

  @EqualsAndHashCode.Exclude
  private final Random random;

  @Getter
  private final String[][] matrix;

  @Getter
  @Nullable
  private final String bonusSymbol;

  /**
   * Constructor that validates {@link GameConfig} to make sure {@link ScratchCard} is always created correctly
   *
   * @param gameConfig configuration of the game that is used to populate {@link ScratchCard} matrix accordingly
   * @throws GameException if gameConfig is null
   */
  public ScratchCard(GameConfig gameConfig) {
    // GameConfig validates all its attributes before its creation.
    // A non-null gameConfig means it has all the elements that we need to create Scratch Card correctly
    isNotNull(gameConfig, "Config cannot be null");

    random = new Random();
    matrix = populateMatrix(gameConfig);
    bonusSymbol = setBonusSymbolBasedOnProbability(gameConfig, matrix);
  }

  /**
   * Created and populate matrix according to the {@link GameConfig}
   *
   * @param gameConfig configuration of the game that is used to populate {@link ScratchCard} matrix accordingly
   * @return matrix which holds the symbols randomly
   */
  private String[][] populateMatrix(GameConfig gameConfig) {
    String[][] matrix = new String[gameConfig.rows()][gameConfig.columns()];

    // iterate over Standard symbols probabilities
    gameConfig.probabilities().standardSymbolsProbabilities().forEach(probability -> {
      // row and colum of the cell to place a Standard symbol randomly
      int row = probability.row();
      int column = probability.column();
      // Set a random Standard symbol based on probabilities
      matrix[row][column] = getRandomSymbol(probability.symbols());
    });

    return matrix;
  }

  /**
   * Randomly decides if the matrix should be populated with a Bonus symbol and then selects one of the Bonus symbol
   * randomly according to their probability|
   *
   * @param gameConfig configuration of the game that is used to populate {@link ScratchCard} matrix accordingly
   * @param matrix     the matrix of the {@link ScratchCard}
   * @return bonus symbol if populated on the matrix otherwise null
   */
  private String setBonusSymbolBasedOnProbability(GameConfig gameConfig, String[][] matrix) {
    boolean setBonusSymbol = (random.nextInt(100) + 1 <= BONUS_GENERATION_PROBABILITY_PERCENTAGE);

    if (setBonusSymbol) {
      // Select a random Bonus symbol based on probabilities
      var bonusSymbol = getRandomSymbol(gameConfig.probabilities().bonusSymbolsProbability().symbols());
      var randomRow = random.nextInt(matrix.length);
      var randomColumn = random.nextInt(matrix[0].length);
      matrix[randomRow][randomColumn] = bonusSymbol;
      return bonusSymbol;
    }

    return null;
  }

  /**
   * Randomly selects a symbol to be places on the matrix
   *
   * @param symbolProbabilities a map of symbols and their probability
   * @return randomly selected symbol
   */
  private String getRandomSymbol(Map<String, Integer> symbolProbabilities) {
    // sum of probability of all the symbols
    var totalProbabilities = symbolProbabilities.values().stream().mapToInt(Integer::intValue).sum();

    // generate a random number between 0 and totalProbabilities
    var randomNumber = random.nextInt(totalProbabilities) + 1;

    // to maintain cumulative sum
    final int[] cumulativeSum = {0};

    // to hold the highest probability of all symbols
    final int[] highestProbability = {0};

    // to hold the symbol with the highest probability.
    final String[] highestProbabilitySymbol = new String[1];

    // sort map according to probability value, so that the symbol is correctly selected according to its
    // contribution to cumulativeSum when compare to randomNumber.
    return symbolProbabilities.entrySet().stream().sorted(Map.Entry.comparingByValue())
      .collect(Collectors.toMap(Map.Entry::getKey, entry -> {
        var symbolProbability = entry.getValue();
        // keep highestProbability and highestProbabilitySymbol updated
        if (symbolProbability > highestProbability[0]) {
          highestProbability[0] = symbolProbability;
          highestProbabilitySymbol[0] = entry.getKey();
        }
        return cumulativeSum[0] += entry.getValue();
      }, (oldValue, newValue) -> oldValue, LinkedHashMap::new)).entrySet().stream()
      .filter(entry -> entry.getValue() >= randomNumber).map(Map.Entry::getKey).findFirst()
      .orElse(highestProbabilitySymbol[0]); // this should never happen
  }
}
