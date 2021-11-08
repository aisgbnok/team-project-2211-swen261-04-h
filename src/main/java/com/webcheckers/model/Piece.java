package com.webcheckers.model;

import com.webcheckers.model.Game.Color;

/**
 * Game Piece placed on a Board Space.
 *
 * @author <a href='mailto:ajs2576@rit.edu'>Anthony Swierkosz</a>
 */
public class Piece {

  // Enumerations
  public enum Type {
    SINGLE,
    KING
  }

  private final Type type; // Piece type enum, SINGLE or KING.
  private final Color color; // Piece color enum, WHITE or RED.

  /**
   * Constructs a game piece. Piece type and color is determined by given type and color.
   *
   * @param type Piece type enum, SINGLE or KING.
   * @param color Piece color enum, WHITE or RED.
   */
  public Piece(Type type, Color color) {
    this.type = type;
    this.color = color;
  }

  /**
   * Constructs a duplicate of a game piece.
   *
   * @param piece Piece to duplicate
   */
  public Piece(Piece piece) {
    this.type = piece.type;
    this.color = piece.color;
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
}
