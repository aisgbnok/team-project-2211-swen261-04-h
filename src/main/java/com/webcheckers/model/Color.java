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
   * @return -1 if the color is RED, as RED moves away from the bottom. 1 if the color is WHITE.
   */
  public int getDirection(Color color) {
    return color.equals(RED) ? -1 : 1;
  }

  /**
   * Returns the direction of the color.
   *
   * @return -1 if the color is RED, as RED moves away from the bottom. 1 if the color is WHITE.
   */
  public int direction() {
    return getDirection(this);
  }
}
