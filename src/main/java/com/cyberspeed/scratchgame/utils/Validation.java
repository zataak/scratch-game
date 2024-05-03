package com.cyberspeed.scratchgame.utils;

import com.cyberspeed.scratchgame.exceptions.GameException;
import java.util.Collection;
import java.util.Map;

/**
 * Utility class that provides method to validate data. These method are designed primarily for doing parameter
 * validation in methods and constructors.
 */
public class Validation {

  /**
   * Checks that the specified object reference is not null
   *
   * @param obj          the object reference to check for nullity
   * @param errorMessage message to be used if exception is thrown
   * @throws GameException if obj is null
   */
  public static void isNotNull(Object obj, String errorMessage) {
    if (obj == null) {
      throw new GameException(errorMessage);
    }
  }

  /**
   * Checks that the specified object reference is not null
   *
   * @param str          the String reference to check for null and empty
   * @param errorMessage message to be used if exception is thrown
   * @throws GameException if str is null or empty
   */
  public static void isNotBlank(String str, String errorMessage) {
    if (str == null || str.isEmpty()) {
      throw new GameException(errorMessage);
    }
  }

  /**
   * Checks that the specified integer is positive
   *
   * @param value        the integer value
   * @param errorMessage message to be used if exception is thrown
   * @throws GameException if value less than 0
   */
  public static void isPositive(int value, String errorMessage) {
    if (value < 0) {
      throw new GameException(errorMessage);
    }
  }

  /**
   * Checks that the specified double is positive
   *
   * @param value        the double value
   * @param errorMessage message to be used if exception is thrown
   * @throws GameException if value is less than 0.0
   */
  public static void isPositive(double value, String errorMessage) {
    if (value < 0.0) {
      throw new GameException(errorMessage);
    }
  }

  /**
   * Checks that the specified int is greater than 0
   *
   * @param value        the int value
   * @param errorMessage message to be used if exception is thrown
   * @throws GameException if value is less than or equals to 0.0
   */
  public static void isGreaterThanZero(int value, String errorMessage) {
    if (value <= 0) {
      throw new GameException(errorMessage);
    }
  }

  /**
   * Checks that the specified double is greater than 0
   *
   * @param value        the double value
   * @param errorMessage message to be used if exception is thrown
   * @throws GameException if value is less than or equals to 0.0
   */
  public static void isGreaterThanZero(double value, String errorMessage) {
    if (value <= 0.0) {
      throw new GameException(errorMessage);
    }
  }

  /**
   * Checks that the specified collection is not null or empty
   *
   * @param collection   the collection reference to check for null or empty
   * @param errorMessage message to be used if exception is thrown
   * @throws GameException if collection is null or empty
   */
  public static void isNonEmptyCollection(Collection<?> collection, String errorMessage) {
    if (collection == null || collection.isEmpty()) {
      throw new GameException(errorMessage);
    }
  }

  /**
   * Checks that the specified map is not null or empty
   *
   * @param map          the map reference to check for null or empty
   * @param errorMessage message to be used if exception is thrown
   * @throws GameException if map is null or empty
   */
  public static void isNonEmptyMap(Map<?, ?> map, String errorMessage) {
    if (map == null || map.isEmpty()) {
      throw new GameException(errorMessage);
    }
  }

  /**
   * Check that x and y are within their bounds i.e. x is between 0 and xBound and y is between 0 and yBound
   *
   * @param x            the x value to check within bound
   * @param y            the y value to check within bound
   * @param xBound       the xBound for x
   * @param yBound       the yBound for y
   * @param errorMessage message to be used if exception is thrown
   * @throws GameException if either x or y are not within their bound
   */
  public static void isWithinBounds(int x, int y, int xBound, int yBound, String errorMessage) {
    if (!(x >= 0 && x < xBound && y >= 0 && y < yBound)) {
      throw new GameException(errorMessage);
    }
  }

  /**
   * Check that given String array length is equal to expectedLength
   *
   * @param array          the string array to check its length
   * @param expectedLength the expected length of the array
   * @param errorMessage   message to be used if exception is thrown
   * @throws GameException if length of the array is less that expectedLength
   */
  public static void checkLength(String[] array, int expectedLength, String errorMessage) {
    if (array == null || array.length != expectedLength) {
      throw new GameException(errorMessage);
    }
  }
}
