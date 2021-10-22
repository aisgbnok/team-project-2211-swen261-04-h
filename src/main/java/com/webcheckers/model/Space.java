package com.webcheckers.model;

public class Space {

  private int cellIdx;
  private boolean isValid;
  private Piece piece;
  private int row;
  private int cell;

  public int getRow() {
    return row;
  }

  public void setRow(int row) {
    this.row = row;
  }

  public int getCell() {
    return cell;
  }

  public void setCell(int cell) {
    this.cell = cell;
  }

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
    return piece;
  }

  public void setValid(boolean valid) {
    this.isValid = valid;
  }

  public void setPiece(Piece newPiece) {
    this.piece = newPiece;
  }
}
