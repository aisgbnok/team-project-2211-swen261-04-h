package com.webcheckers.model;

public class Space {

  private int cellIdx;
  private boolean isValid;
  private Piece piece;

  protected Space(int cellIdx) {
    this.cellIdx = cellIdx;
    this.isValid = false;
    this.piece = null;
  }

  public int getCellIdx() {
    return cellIdx;
  }

  public boolean isValid() {
    return isValid;
  }

  public Piece getPiece() {
    return null;
  }

  public void setValid(boolean valid) {
    this.isValid = valid;
  }

  public void setPiece(Piece newPiece) {
    this.piece = newPiece;
  }
}
