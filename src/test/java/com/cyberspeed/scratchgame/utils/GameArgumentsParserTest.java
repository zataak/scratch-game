package com.cyberspeed.scratchgame.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.cyberspeed.scratchgame.GameArgumentsParser;
import com.cyberspeed.scratchgame.exceptions.GameException;
import com.cyberspeed.scratchgame.models.GameInput;
import org.junit.jupiter.api.Test;

public class GameArgumentsParserTest {

  private static final GameArgumentsParser GAME_ARGUMENTS_PARSER = new GameArgumentsParser();

  @Test
  public void parse_validInput() {
    String[] input = {"--config", "config.json", "--betting-amount", "100"};
    GameInput gameInput = GAME_ARGUMENTS_PARSER.parse(input);

    assertNotNull(gameInput);
    assertEquals("config.json", gameInput.configFile());
    assertEquals(100, gameInput.bettingAmount());
  }

  @Test
  public void throwsScratchGameException_whenConfigIsMissing() {
    String[] input = {"--betting-amount", "100"};

    var exception = assertThrows(GameException.class, () -> GAME_ARGUMENTS_PARSER.parse(input));
    assertEquals(
      "Unable to parse game input. Usage: java -jar <your-jar-file> --config <config-file> --betting-amount <amount>",
      exception.getMessage());
  }

  @Test
  public void throwsScratchGameException_whenBettingAmountIsMissing() {
    String[] input = {"--config", "config.json"};

    var exception = assertThrows(GameException.class, () -> GAME_ARGUMENTS_PARSER.parse(input));
    assertEquals(
      "Unable to parse game input. Usage: java -jar <your-jar-file> --config <config-file> --betting-amount <amount>",
      exception.getMessage());
  }

  @Test
  public void throwsScratchGameException_whenBettingAmountIsInvalid() {
    String[] input = {"--config", "config.json", "--betting-amount", "abc"};

    var exception = assertThrows(GameException.class, () -> GAME_ARGUMENTS_PARSER.parse(input));
    assertEquals("Unable to parse betting amount", exception.getMessage());
  }

  @Test
  public void throwsScratchGameException_whenBettingAmountIsNegative() {
    String[] input = {"--config", "config.json", "--betting-amount", "-100"};

    var exception = assertThrows(GameException.class, () -> GAME_ARGUMENTS_PARSER.parse(input));
    assertEquals("Betting amount must be greater than 0", exception.getMessage());
  }

  @Test
  public void throwsScratchGameException_whenBettingAmountIsZero() {
    String[] input = {"--config", "config.json", "--betting-amount", "0"};

    var exception = assertThrows(GameException.class, () -> GAME_ARGUMENTS_PARSER.parse(input));
    assertEquals("Betting amount must be greater than 0", exception.getMessage());
  }
}

