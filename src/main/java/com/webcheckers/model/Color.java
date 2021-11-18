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
   * @return Opposite color; RED if object is WHITE or vice versa.
   */
  public static Color getOppositeColor(Color color) {
    return color.equals(RED) ? RED : WHITE;
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
