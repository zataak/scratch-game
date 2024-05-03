package com.cyberspeed.scratchgame;

import static org.mockito.Mockito.when;

import com.cyberspeed.scratchgame.configs.GameConfig;
import com.cyberspeed.scratchgame.probabilities.BonusSymbolsProbability;
import com.cyberspeed.scratchgame.probabilities.Probability;
import com.cyberspeed.scratchgame.probabilities.StandardSymbolsProbability;
import com.cyberspeed.scratchgame.symbols.BonusSymbol;
import com.cyberspeed.scratchgame.symbols.StandardSymbol;
import com.cyberspeed.scratchgame.symbols.Symbol;
import com.cyberspeed.scratchgame.wincombinations.LinearSymbolsWinCombination;
import com.cyberspeed.scratchgame.wincombinations.SameSymbolsWinCombination;
import com.cyberspeed.scratchgame.wincombinations.WinCombination;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;

public class GameTestBase {

  protected static final Map<String, Integer> STANDARD_SYMBOLS_PROBABILITIES = new HashMap<>();
  protected static final Map<String, Integer> BONUS_SYMBOLS_PROBABILITIES = new HashMap<>();
  protected static final Map<String, Symbol> ALL_SYMBOLS = new HashMap<>();
  protected static final Map<String, WinCombination> WIN_COMBINATIONS = new HashMap<>();

  @Mock
  protected GameConfig gameConfig;

  @Mock
  protected Probability probability;

  @BeforeEach
  void init() {
    when(gameConfig.rows()).thenReturn(3);
    when(gameConfig.columns()).thenReturn(3);

    ALL_SYMBOLS.put("A", new StandardSymbol(50));
    ALL_SYMBOLS.put("B", new StandardSymbol(25));
    ALL_SYMBOLS.put("C", new StandardSymbol(10));
    ALL_SYMBOLS.put("D", new StandardSymbol(5));
    ALL_SYMBOLS.put("E", new StandardSymbol(3));
    ALL_SYMBOLS.put("F", new StandardSymbol(1.5));
    ALL_SYMBOLS.put("10x", new BonusSymbol(10, 0, "multiply_reward"));
    ALL_SYMBOLS.put("5x", new BonusSymbol(50, 0, "multiply_reward"));
    ALL_SYMBOLS.put("+1000", new BonusSymbol(0, 1000, "extra_bonus"));
    ALL_SYMBOLS.put("+500", new BonusSymbol(5, 5000, "extra_bonus"));
    ALL_SYMBOLS.put("MISS", new BonusSymbol(0, 0, "miss"));
    when(gameConfig.symbols()).thenReturn(ALL_SYMBOLS);

    STANDARD_SYMBOLS_PROBABILITIES.put("A", 1);
    STANDARD_SYMBOLS_PROBABILITIES.put("B", 2);
    STANDARD_SYMBOLS_PROBABILITIES.put("C", 3);
    STANDARD_SYMBOLS_PROBABILITIES.put("D", 4);
    STANDARD_SYMBOLS_PROBABILITIES.put("E", 5);
    STANDARD_SYMBOLS_PROBABILITIES.put("F", 6);

    BONUS_SYMBOLS_PROBABILITIES.put("10x", 1);
    BONUS_SYMBOLS_PROBABILITIES.put("5x", 2);
    BONUS_SYMBOLS_PROBABILITIES.put("+1000", 3);
    BONUS_SYMBOLS_PROBABILITIES.put("+500", 4);
    BONUS_SYMBOLS_PROBABILITIES.put("MISS", 5);

    when(probability.standardSymbolsProbabilities()).thenReturn(
      List.of(new StandardSymbolsProbability(0, 0, STANDARD_SYMBOLS_PROBABILITIES),
        new StandardSymbolsProbability(0, 1, STANDARD_SYMBOLS_PROBABILITIES),
        new StandardSymbolsProbability(0, 2, STANDARD_SYMBOLS_PROBABILITIES),
        new StandardSymbolsProbability(1, 0, STANDARD_SYMBOLS_PROBABILITIES),
        new StandardSymbolsProbability(1, 1, STANDARD_SYMBOLS_PROBABILITIES),
        new StandardSymbolsProbability(1, 2, STANDARD_SYMBOLS_PROBABILITIES),
        new StandardSymbolsProbability(2, 0, STANDARD_SYMBOLS_PROBABILITIES),
        new StandardSymbolsProbability(2, 1, STANDARD_SYMBOLS_PROBABILITIES),
        new StandardSymbolsProbability(2, 2, STANDARD_SYMBOLS_PROBABILITIES)));

    when(probability.bonusSymbolsProbability()).thenReturn(new BonusSymbolsProbability(BONUS_SYMBOLS_PROBABILITIES));
    when(gameConfig.probabilities()).thenReturn(probability);

    WIN_COMBINATIONS.put("same_symbol_3_times", new SameSymbolsWinCombination(1, "same_symbols", 3));
    WIN_COMBINATIONS.put("same_symbol_4_times", new SameSymbolsWinCombination(1.5, "same_symbols", 4));
    WIN_COMBINATIONS.put("same_symbol_5_times", new SameSymbolsWinCombination(2, "same_symbols", 5));
    WIN_COMBINATIONS.put("same_symbol_6_times", new SameSymbolsWinCombination(3, "same_symbols", 6));
    WIN_COMBINATIONS.put("same_symbol_7_times", new SameSymbolsWinCombination(5, "same_symbols", 7));
    WIN_COMBINATIONS.put("same_symbol_8_times", new SameSymbolsWinCombination(10, "same_symbols", 8));
    WIN_COMBINATIONS.put("same_symbol_9_times", new SameSymbolsWinCombination(20, "same_symbols", 9));

    WIN_COMBINATIONS.put("same_symbols_horizontally", new LinearSymbolsWinCombination(2, "horizontally_linear_symbols",
      List.of(List.of("0:0", "0:1", "0:2"), List.of("1:0", "1:1", "1:2"), List.of("2:0", "2:1", "2:2"))));
    WIN_COMBINATIONS.put("same_symbols_vertically", new LinearSymbolsWinCombination(2, "vertically_linear_symbols",
      List.of(List.of("0:0", "1:0", "2:0"), List.of("0:1", "1:1", "2:1"), List.of("0:2", "1:2", "2:2"))));
    WIN_COMBINATIONS.put("same_symbols_diagonally_left_to_right",
      new LinearSymbolsWinCombination(2, "ltr_diagonally_linear_symbols", List.of(List.of("0:0", "1:1", "2:2"))));
    WIN_COMBINATIONS.put("same_symbols_diagonally_right_to_left",
      new LinearSymbolsWinCombination(2, "rtl_diagonally_linear_symbols", List.of(List.of("0:2", "1:1", "2:0"))));
    when(gameConfig.winCombinations()).thenReturn(WIN_COMBINATIONS);
  }
}
