package com.cyberspeed.scratchgame.models;

import com.cyberspeed.scratchgame.ScratchCard;
import com.cyberspeed.scratchgame.ScratchGame;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;

/**
 * Class to represent the result of the {@link ScratchGame} containing the {@link ScratchCard} matrix, total reward,
 * name of the wining combination and bonus details, if any applied.
 */
@Getter
@Builder
public class GameOutput {

  @JsonProperty("matrix")
  private String[][] matrix;

  @JsonProperty("reward")
  private int reward;

  @JsonProperty("applied_winning_combinations")
  private Map<String, Set<String>> appliedWinningCombinations;

  @JsonProperty("applied_bonus_symbol")
  private String appliedBonusSymbol;
}
