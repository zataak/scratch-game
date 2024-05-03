package com.cyberspeed.scratchgame.config;

import static com.cyberspeed.scratchgame.utils.Validation.isNonEmptyMap;
import static com.cyberspeed.scratchgame.utils.Validation.isNotNull;
import static com.cyberspeed.scratchgame.utils.Validation.isPositive;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

import com.cyberspeed.scratchgame.GameTestBase;
import com.cyberspeed.scratchgame.configs.GameConfig;
import com.cyberspeed.scratchgame.utils.Validation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class GameConfigTest extends GameTestBase {

  private GameConfig actualConfig;

  private void setup() {
    actualConfig = new GameConfig(gameConfig.rows(), gameConfig.columns(), gameConfig.symbols(),
      gameConfig.probabilities(), gameConfig.winCombinations());
  }

  @Test
  void constructsCorrectly() {
    setup();
    assertEquals(gameConfig.rows(), actualConfig.rows());
    assertEquals(gameConfig.columns(), actualConfig.columns());
    assertEquals(gameConfig.symbols(), actualConfig.symbols());
    assertEquals(gameConfig.probabilities(), actualConfig.probabilities());
    assertEquals(gameConfig.winCombinations(), actualConfig.winCombinations());
  }

  @Test
  void verifyValidations() {
    try (MockedStatic<Validation> validation = Mockito.mockStatic(Validation.class)) {
      setup();
      // validate rows, columns
      validation.verify(() -> Validation.isPositive(anyInt(), anyString()), times(2));
      // validate symbols and winCombinations
      validation.verify(() -> isNonEmptyMap(anyMap(), anyString()), times(2));
      // validate probabilities
      validation.verify(() -> isNotNull(any(), anyString()), times(1));
    }
  }
}
