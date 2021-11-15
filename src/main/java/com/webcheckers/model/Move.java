package com.webcheckers.model;

/**
 * Move action that happens to a piece on a game board.
 *
 * @author <a href='mailto:ajs2576@rit.edu'>Anthony Swierkosz</a>
 */
public class Move {

  private final Position start; // Move's starting position
  private final Position end; // Move's ending position

  private final transient Type type; // Move type enum; SLIDE, JUMP, or INVALID.

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

    // TODO should we do validation here as well?  Or not care whether end space is valid, etc?

    if (rowDelta == 1 && colDelta == 1) {
      type = Type.SLIDE; // If both row and col moved by 1 then it is a slide
    } else if (rowDelta == 2 && colDelta == 2) {
      type = Type.JUMP; // If both row and col moved by 2 then it is a jump
    } else {
      type = Type.INVALID; // If it isn't a slide or jump, it is invalid
    }
  }

  /**
   * Constructs a duplicate of a given move.
   *
   * @param move Move to duplicate
   */
  public Move(Move move) {
    this(move.getStart(), move.getEnd());
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

  /**
   * Getter for the move type. SLIDE, JUMP, or INVALID.
   *
   * @return Type enum SLIDE, JUMP, or INVALID.
   */
  public Type getType() {
    return type;
  }

  /**
   * Getter for if the move type is a slide.
   *
   * @return True if the move type is a slide, or false if it isn't.
   */
  public boolean isSlide() {
    return type == Type.SLIDE;
  }

  /**
   * Getter for if the move type is a jump.
   *
   * @return True if the move type is a jump, or false if it isn't.
   */
  public boolean isJump() {
    return type == Type.JUMP;
  }

  /**
   * Getter for the middle position between a jump move.
   *
   * @return Position between start and end if the move is a jump, and null if it isn't.
   */
  public Position middle() {
    if (isJump()) {
      int midRow = (start.getRow() + end.getRow()) / 2;
      int midCol = (start.getCell() + end.getCell()) / 2;

      return new Position(midRow, midCol);
    }
    return null;
  }

  // Enumerations
  public enum Type {
    SLIDE,
    JUMP,
    INVALID
  }
}
