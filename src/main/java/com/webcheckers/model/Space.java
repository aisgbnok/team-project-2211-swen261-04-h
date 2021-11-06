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

  protected Space(int cellIdx) {
    this(cellIdx, false, null);
  }

  protected Space(int cellIdx, boolean isValid) {
    this(cellIdx, isValid, null);
  }

  protected Space(int cellIdx, boolean isValid, Piece piece) {
    this.cellIdx = cellIdx;
    this.isValid = isValid;
    this.piece = piece;
  }

  public int getCellIdx() {
    return cellIdx;
  }

  public boolean isValid() {
    return isValid;
  }

  public Piece getPiece() {
    return piece;
  }

  public void setValid(boolean valid) {
    this.isValid = valid;
  }

  public void setValid() {
    this.isValid = !this.isValid;
  }

  public void setPiece(Piece newPiece) {
    this.piece = newPiece;
    this.setValid();
  }
}
