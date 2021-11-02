package com.webcheckers.model;

/**
 * This is the Move data type.
 *
 * @author <a href='mailto:ajs2576@rit.edu'>Anthony Swierkosz</a>
 */
public class Move {

  // Fields
  private final Position start;
  private final Position end;

  /**
   * Constructor for Move that takes in a start position and an end position.
   *
   * @param start Move's starting position
   * @param end Move's ending position
   */
  public Move(Position start, Position end) {
    this.start = start;
    this.end = end;
  }

  /**
   * Getter for starting position
   *
   * @return Move's starting position
   */
  public Position getStart() {
    return start;
  }

  /**
   * Getter for ending position
   *
   * @return Move's ending position
   */
  public Position getEnd() {
    return end;
  }
}
