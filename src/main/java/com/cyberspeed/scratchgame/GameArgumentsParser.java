package com.cyberspeed.scratchgame;

import static com.cyberspeed.scratchgame.utils.Validation.isGreaterThanZero;

import com.cyberspeed.scratchgame.exceptions.GameException;
import com.cyberspeed.scratchgame.models.GameInput;
import java.util.HashMap;

/**
 * Utility class with a method two parse and validate game arguments
 */
public class GameArgumentsParser {

  /**
   * Parse and validate game arguments
   *
   * @param args arguments to the game
   * @return {@link GameInput} which contains game arguments
   * @throws GameException if number of argument is incorrect, when bettingAmount is not greater than 0
   */
  public GameInput parse(String[] args) {
    var arguments = new HashMap<String, String>();

    // args are processed in pair, first arg should be the name and second arg should be the value
    for (int i = 0; i < args.length; i += 2) {
      // check if there are at least two args available to be processed
      if (i + 1 < args.length) {
        // add pair to the map as key and value
        arguments.put(args[i], args[i + 1]);
      }
    }

    int bettingAmount;
    var configFile = arguments.get("--config");
    var amountAsString = arguments.get("--betting-amount");

    // if any of the argument is null throw exception
    if (configFile == null || amountAsString == null) {
      throw new GameException(
        "Unable to parse game input. Usage: java -jar <your-jar-file> --config <config-file> --betting-amount <amount>");
    }

    // assuming betting amount is always an integer value greater than 0;
    try {
      bettingAmount = Integer.parseInt(amountAsString);
    } catch (NumberFormatException e) {
      throw new GameException("Unable to parse betting amount");
    }

    // validate betAmount is greater than 0
    isGreaterThanZero(bettingAmount, "Betting amount must be greater than 0");

    return new GameInput(configFile, bettingAmount);
  }
}
