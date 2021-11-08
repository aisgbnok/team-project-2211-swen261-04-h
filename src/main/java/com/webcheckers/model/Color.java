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
   * Returns the opposite color.
   *
   * @return Opposite color; RED if object is WHITE or vice versa.
   */
  public Color opposite() {
    return getOppositeColor(this);
  }

  /**
   * Returns the opposite color of the given color.
   *
   * @param color Color to get opposite color of
   * @return Opposite color; RED if object is WHITE or vice versa.
   */
  public static Color getOppositeColor(Color color) {
    return color.equals(RED) ? RED : WHITE;
  }
}
