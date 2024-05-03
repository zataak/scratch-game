package com.cyberspeed.scratchgame;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.cyberspeed.scratchgame.exceptions.GameException;
import org.junit.jupiter.api.Test;

public class GameLauncherTest {

  @Test
  void loadConfig_throwsException_whenFileDoesntExist() {
    var exception = assertThrows(GameException.class, () -> GameLauncher.loadConfig("invalid-file"));

    assertEquals("Unable to parse configuration file", exception.getMessage());
    assertNotNull(exception.getCause(), "The GameException should have thrown with a throwable root cause");
  }
}
