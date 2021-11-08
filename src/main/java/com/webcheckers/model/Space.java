package com.webcheckers.model;

import com.webcheckers.model.Piece.Type;

/**
 * Space on the board.
 *
 * @author <a href='mailto:ajs2576@rit.edu'>Anthony Swierkosz</a>
 */
public class Space {

  private final int cellIdx; // Space column index
  private boolean isValid; // Space is black and empty
  private Piece piece; // Piece on the space, or null if empty

  /**
   * Constructs a space. Space is not valid and empty.
   *
   * @param cellIdx Space column index
   */
  public Space(int cellIdx) {
    this(cellIdx, false, null);
  }

  /**
   * Constructs a space. Space validity is determined by given validity, and is empty.
   *
   * @param cellIdx Space column index
   * @param isValid Space validity
   */
  public Space(int cellIdx, boolean isValid) {
    this(cellIdx, isValid, null);
  }

  /**
   * Constructs a space. Space generates a piece if color is given, and sets validity appropriately.
   *
   * @param cellIdx Space column index
   * @param isValid Space validity
   * @param color Color of piece on this space, or null if space is empty.
   */
  public Space(int cellIdx, boolean isValid, Color color) {
    this.cellIdx = cellIdx;
    this.isValid = (color == null) && isValid;
    this.piece = (color == null) ? null : new Piece(Type.SINGLE, color);
  }

  /**
   * Constructs a duplicate of a space.
   *
   * @param space Space to duplicate
   */
  public Space(Space space) {
    this.cellIdx = space.cellIdx;
    this.isValid = space.isValid;
    this.piece = (space.piece == null) ? null : new Piece(space.piece);
  }

  /**
   * Getter for the cell (column) index of this position.
   *
   * @return Cell (column) index
   */
  public int getCellIdx() {
    return cellIdx;
  }

  /**
   * Getter for validity of space.
   *
   * @return True if space is valid, or false if space is not valid.
   */
  public boolean isValid() {
    return isValid;
  }

  /**
   * Set the space's validity.
   *
   * @param valid Space validity
   */
  public void setValid(boolean valid) {
    this.isValid = valid;
  }

  /** Toggles space validity. */
  public void setValid() {
    this.isValid = !this.isValid;
  }

  /**
   * Getter for space's piece.
   *
   * @return Piece on this space, or null if space is empty.
   */
  public Piece getPiece() {
    return piece;
  }

  /**
   * Places a piece on the valid and empty space.
   *
   * @param newPiece Piece to place on the empty space.
   * @throws IllegalArgumentException if space is not valid.
   */
  public void setPiece(Piece newPiece) {
    if (isValid) {
      this.piece = newPiece;
      this.setValid();
    } else {
      // TODO is this smelly code?
      throw new IllegalArgumentException("Space already has a piece.");
    }
  }

  /** Removes a piece from the space. */
  public void removePiece() {
    this.piece = null;
    this.setValid();
  }
}
