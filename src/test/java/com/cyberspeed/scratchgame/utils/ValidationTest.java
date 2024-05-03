package com.cyberspeed.scratchgame.utils;

import static com.cyberspeed.scratchgame.utils.Validation.isNonEmptyCollection;
import static com.cyberspeed.scratchgame.utils.Validation.isNonEmptyMap;
import static com.cyberspeed.scratchgame.utils.Validation.isGreaterThanZero;
import static com.cyberspeed.scratchgame.utils.Validation.isNotBlank;
import static com.cyberspeed.scratchgame.utils.Validation.isNotNull;
import static com.cyberspeed.scratchgame.utils.Validation.isPositive;
import static com.cyberspeed.scratchgame.utils.Validation.checkLength;
import static com.cyberspeed.scratchgame.utils.Validation.isWithinBounds;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.cyberspeed.scratchgame.exceptions.GameException;
import java.util.Collections;
import org.junit.jupiter.api.Test;

class ValidationTest {

  @Test
  void requireNotNull_Test() {
    var exception = assertThrows(GameException.class, () -> isNotNull(null, "Object is null"));
    assertEquals("Object is null", exception.getMessage());
  }

  @Test
  void requiresNotBlank_Test() {
    var exception = assertThrows(GameException.class, () -> isNotBlank(null, "String is null"));
    assertEquals("String is null", exception.getMessage());

    exception = assertThrows(GameException.class, () -> isNotBlank("", "String is empty"));
    assertEquals("String is empty", exception.getMessage());
  }

  @Test
  void requiresPositive_Int_Test() {
    var exception = assertThrows(GameException.class, () -> Validation.isPositive(-1, "Negative value"));
    assertEquals("Negative value", exception.getMessage());
  }

  @Test
  void requiresPositive_Double_Test() {
    var exception = assertThrows(GameException.class, () -> isPositive(-1.0, "Negative value"));
    assertEquals("Negative value", exception.getMessage());
  }

  @Test
  void requiresNonZeroPositive_Test() {
    var exception = assertThrows(GameException.class, () -> isGreaterThanZero(0.0, "Zero value"));
    assertEquals("Zero value", exception.getMessage());

    exception = assertThrows(GameException.class, () -> isGreaterThanZero(-1.0, "Negative value"));
    assertEquals("Negative value", exception.getMessage());
  }

  @Test
  void requireNonEmptyCollection_Test() {
    var exception = assertThrows(GameException.class, () -> isNonEmptyCollection(null, "Collection is null"));
    assertEquals("Collection is null", exception.getMessage());

    exception = assertThrows(GameException.class,
      () -> isNonEmptyCollection(Collections.emptyList(), "Collection is empty"));
    assertEquals("Collection is empty", exception.getMessage());
  }

  @Test
  void requireNonEmptyMap_Test() {
    var exception = assertThrows(GameException.class, () -> isNonEmptyMap(null, "Map is null"));
    assertEquals("Map is null", exception.getMessage());

    exception = assertThrows(GameException.class, () -> isNonEmptyMap(Collections.emptyMap(), "Map is empty"));
    assertEquals("Map is empty", exception.getMessage());
  }

  @Test
  void requireWithinBounds_validCoordinates() {
    assertDoesNotThrow(() -> isWithinBounds(2, 3, 5, 6, "Not within bounds"));
  }

  @Test
  void requireWithinBounds_invalidCoordinates() {
    var exception = assertThrows(GameException.class, () -> isWithinBounds(6, 7, 5, 6, "Not within bounds"));
    assertEquals("Not within bounds", exception.getMessage());
  }

  @Test
  void requireSize_validArray() {
    String[] array = {"A", "B", "C"};
    assertDoesNotThrow(() -> checkLength(array, 3, "Correct size"));
  }

  @Test
  void requireSize_nullArray() {
    String[] array = null;
    var exception = assertThrows(GameException.class, () -> checkLength(array, 3, "Not correct size"));
    assertEquals("Not correct size", exception.getMessage());
  }

  @Test
  void requireSize_invalidSize() {
    String[] array = {"A", "B", "C"};
    GameException exception = assertThrows(GameException.class, () -> checkLength(array, 2, "Not correct size"));
    assertEquals("Not correct size", exception.getMessage());
  }
}

