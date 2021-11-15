package com.webcheckers.model;

/**
 * Move action that happens to a piece on a game board.
 *
 * @author <a href='mailto:ajs2576@rit.edu'>Anthony Swierkosz</a>
 */
public class Move {

  private final Position start; // Move's starting position
  private final Position end; // Move's ending position

  private final Type type; // Move type enum; SLIDE, JUMP, or INVALID.

  /**
   * Constructs a Move. Holds a start position and an end position for the move.
   *
   * @param start Move's starting position
   * @param end Move's ending position
   */
  public Move(Position start, Position end) {
    this.start = start;
    this.end = end;

    // Determine Move Type
    int rowDelta = Math.abs(start.getRow() - end.getRow()); // Delta between start and end row
    int colDelta = Math.abs(start.getCell() - end.getCell()); // Delta between start and end col

    if (rowDelta == 1 && colDelta == 1) {
      type = Type.SLIDE; // If both row and col moved by 1 then it is a slide
    } else if (rowDelta == 2 && colDelta == 2) {
      type = Type.JUMP; // If both row and col moved by 2 then it is a jump
    } else {
      type = Type.INVALID; // If it isn't a slide or jump, it is invalid
    }
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

  // Enumerations
  public enum Type {
    SLIDE,
    JUMP,
    INVALID
  }
}
