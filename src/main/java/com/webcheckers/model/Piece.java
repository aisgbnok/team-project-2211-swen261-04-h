package com.webcheckers.model;

public class Piece {

  // Enumerations
  public enum Type {
    SINGLE,
    KING
  }

  public enum Color {
    RED,
    WHITE
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
