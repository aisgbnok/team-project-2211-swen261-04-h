package com.webcheckers.model;

import com.webcheckers.model.Game.Color;

public class Piece {

  // Enumerations
  public enum Type {
    SINGLE,
    KING
  }

  // Fields
  private final Type type;
  private final Color color;

  protected Piece(Type type, Color color) {
    this.type = type;
    this.color = color;
  }

  public Type getType() {
    return type;
  }

  public Color getColor() {
    return color;
  }
}
