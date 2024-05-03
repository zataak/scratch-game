package com.cyberspeed.scratchgame.exceptions;

public class GameException extends RuntimeException {

  public GameException(String message) {
    super(message);
  }

  public GameException(String message, Throwable rootCause) {
    super(message, rootCause);
  }
}
