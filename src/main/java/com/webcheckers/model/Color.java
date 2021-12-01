package com.webcheckers.model;

/**
 * Color Enumeration for defining piece colors (RED or WHITE) and various helper methods for Color.
 *
 * @author <a href='mailto:ajs2576@rit.edu'>Anthony Swierkosz</a>
 */
public enum Color {
  RED,
  WHITE;

  /**
   * Returns the opposite color of the given color.
   *
   * @param color Color to get opposite color of
   * @return Opposite color; WHITE if given color is RED, or vice versa.
   */
  public static Color getOppositeColor(Color color) {
    return color.equals(RED) ? WHITE : RED;
  }

  /**
   * Returns the opposite color.
   *
   * @return Opposite color; RED if object is WHITE or vice versa.
   */
  public Color opposite() {
    return getOppositeColor(this);
  }

  /**
   * Returns the direction the color should travel in.
   *
   * @param color Color to get direction of
   * @return UP if the color is RED, or DOWN if the color is WHITE.
   */
  public Direction getDirection(Color color) {
    return color.equals(RED) ? Direction.UP : Direction.DOWN;
  }

  /**
   * Returns the direction of the color.
   *
   * @return UP if the color is RED, or DOWN if the color is WHITE.
   */
  public Direction direction() {
    return getDirection(this);
  }

  /**
   * Returns the integer value associated with the COLOR's DIRECTION type. RED (-1) or WHITE (1).
   *
   * @return -1 if the COLOR is RED, or 1 if the COLOR is WHITE.
   */
  public int value() {
    return direction().value();
  }

  /**
   * Returns the friendly name for the enum. RED is Red, and WHITE is White.
   *
   * @return Friendly enum string
   */
  @Override
  public String toString() {
    // Get original toString, and lowercase every character but the first
    String name = super.toString();
    name = name.charAt(0) + name.substring(1).toLowerCase(); // "RED" -> "Red"

    // Return friendly string
    return name;
  }

  /** Provides directionality for COLOR */
  private enum Direction {
    UP,
    DOWN;

    /**
     * Returns the integer value associated with the DIRECTION type. UP (-1) or DOWN (1).
     *
     * @return -1 if the DIRECTION is UP, or 1 if the DIRECTION is DOWN.
     */
    public int value() {
      return this.equals(UP) ? -1 : 1;
    }
  }
}
