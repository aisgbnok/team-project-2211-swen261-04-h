package com.webcheckers.model;

import com.webcheckers.model.Game.Color;
import com.webcheckers.model.Piece.Type;

/**
 * Space on the board.
 *
 * @author <a href='mailto:ajs2576@rit.edu'>Anthony Swierkosz</a>
 */
public class Space {

  private final int cellIdx; // Space column index
  private boolean isValid; // Space is valid or not
  private Piece piece; // Piece on the space, or null if empty

  /**
   * Constructs a game board space. Sets cell index to given cellIdx, space validity to false, and
   * piece to null.
   *
   * @param cellIdx Space column index
   */
  protected Space(int cellIdx) {
    this(cellIdx, false, null);
  }

  /**
   * Constructs a game board space. Sets cell index to given cellIdx, space validity to given
   * isValid, and piece to given piece color.
   *
   * @param cellIdx Space column index
   * @param isValid Space validity
   * @param color Color of piece on this space, or null if space is empty.
   */
  protected Space(int cellIdx, boolean isValid, Color color) {
    this.cellIdx = cellIdx;
    this.isValid = isValid;
    this.piece = (color == null) ? null : new Piece(Type.SINGLE, color);
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
   * @return True if space is valid, or false if space is not valid or is occupied.
   */
  public boolean isValid() {
    return isValid;
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
   * Places a piece on the valid and empty space.
   *
   * @param newPiece Piece to place on the empty space.
   * @throws IllegalArgumentException if space is already taken or not valid.
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
}
