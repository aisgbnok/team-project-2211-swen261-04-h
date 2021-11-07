package com.webcheckers.model;

import com.webcheckers.model.Game.Color;

/**
 * Piece on a space.
 *
 * @author <a href='mailto:ajs2576@rit.edu'>Anthony Swierkosz</a>
 */
public class Piece {

  // Enumerations
  public enum Type {
    SINGLE,
    KING
  }

  // Fields
  private final Type type;
  private final Color color;

  /**
   * Creates a board piece. Sets the type to the given type and the color to the given color.
   *
   * @param type Piece type enum, SINGLE or KING.
   * @param color Piece color enum, WHITE or RED.
   */
  public Piece(Type type, Color color) {
    this.type = type;
    this.color = color;
  }

  /**
   * Getter for the piece type. SINGLE or KING.
   *
   * @return Type enum SINGLE or KING.
   */
  public Type getType() {
    return type;
  }

  /**
   * Getter for the piece color. WHITE or RED.
   *
   * @return Color enum WHITE or RED.
   */
  public Color getColor() {
    return color;
  }

  protected Piece getColorInverted() {
    Color invertedColor = this.color.equals(Color.RED) ? Color.WHITE : Color.RED;

    return new Piece(this.type, invertedColor);
  }
}
