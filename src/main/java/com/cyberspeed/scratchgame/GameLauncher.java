package com.cyberspeed.scratchgame;

import com.cyberspeed.scratchgame.configs.GameConfig;
import com.cyberspeed.scratchgame.exceptions.GameException;
import com.cyberspeed.scratchgame.models.GameOutput;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;

/**
 * Main class responsible for parsing of game arguments, loading game configuration, running the game, and displaying
 * game output;
 */
public class GameLauncher {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
  private static final GameArgumentsParser GAME_ARGUMENTS_PARSER = new GameArgumentsParser();

  static {
    // indent output for pretty print JSON
    OBJECT_MAPPER.enable(SerializationFeature.INDENT_OUTPUT);
    // only serialize attributes that are non-empty
    OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
  }

  public static void main(String[] args) {
    // Parse and validate input
    var gameInput = GAME_ARGUMENTS_PARSER.parse(args);

    // load gameConfig
    var gameConfig = loadConfig(gameInput.configFile());

    // create the game
    var scratchGame = new ScratchGame(gameConfig);

    // play game with a bet
    var gameOutput = scratchGame.play(gameInput.bettingAmount());

    // print output
    printOutput(gameOutput);
  }

  public static GameConfig loadConfig(String configFile) {
    try {
      return OBJECT_MAPPER.readValue(new File(configFile), GameConfig.class);
    } catch (IOException e) {
      throw new GameException("Unable to parse configuration file", e);
    }
  }

  public static void printOutput(GameOutput gameOutput) {
    try {
      // adjust the json-string so that it look more indented when printed on console
      var outputAsJson = OBJECT_MAPPER.writeValueAsString(gameOutput).replace("[ [", "[\n    [")
        .replace("], [", "],\n    [").replace("] ],", "]\n  ],");
      System.out.println(outputAsJson);
    } catch (JsonProcessingException e) {
      throw new GameException("Unable to parse Scratch Game output", e);
    }
  }

}
