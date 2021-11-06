package com.webcheckers.model;

/**
 * Space on the board.
 *
 * @author <a href='mailto:ajs2576@rit.edu'>Anthony Swierkosz</a>
 */
public class Space {

  // Fields
  private final int cellIdx;
  private boolean isValid;
  private Piece piece;

  /**
   * Creates a game board space. Sets cell index to given cellIdx, space validity to false, and
   * piece to null.
   *
   * @param cellIdx Space column index
   */
  protected Space(int cellIdx) {
    this(cellIdx, false, null);
  }

  /**
   * Creates a game board space. Sets cell index to given cellIdx, space validity to given isValid,
   * and piece to null.
   *
   * @param cellIdx Space column index
   * @param isValid Space validity
   */
  protected Space(int cellIdx, boolean isValid) {
    this(cellIdx, isValid, null);
  }

  /**
   * Creates a game board space. Sets cell index to given cellIdx, space validity to given isValid,
   * and piece to given piece.
   *
   * @param cellIdx Space column index
   * @param isValid Space validity
   * @param piece Piece on this space or null if space is empty.
   */
  protected Space(int cellIdx, boolean isValid, Piece piece) {
    this.cellIdx = cellIdx;
    this.isValid = isValid;
    this.piece = piece;
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
    this.piece = newPiece;
    this.setValid();

    /*
    TODO we can't have this check right now because we only have one board and we oddly refill it every time we display it with the correct colors depending on the currentPlayer color. Need to look into this.

        if (isValid) {
          this.piece = newPiece;
          this.setValid();
        } else {
          throw new IllegalArgumentException("Space already has a piece.");
        }
    */
  }
}
