package com.webcheckers.model;

/**
 * Move action that happens to a piece on a game board.
 *
 * @author <a href='mailto:ajs2576@rit.edu'>Anthony Swierkosz</a>
 */
public class Move {

  private final Position start; // Move's starting position
  private final Position end; // Move's ending position

  /**
   * Constructs a Move. Holds a start position and an end position for the move.
   *
   * @param start Move's starting position
   * @param end Move's ending position
   */
  public Move(Position start, Position end) {
    this.start = start;
    this.end = end;
  }

  /**
   * Getter for the starting position of the move.
   *
   * @return Move's starting position
   */
  public Position getStart() {
    return start;
  }

  /**
   * Getter for the ending position of the move.
   *
   * @return Move's ending position
   */
  public Position getEnd() {
    return end;
  }
}
