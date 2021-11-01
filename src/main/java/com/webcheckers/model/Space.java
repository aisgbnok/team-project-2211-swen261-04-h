package com.webcheckers.model;

public class Space {

    private final int cellIdx;
    private boolean isValid;
    private Piece piece;
    private int row;
    private int cell;

    protected Space(int cellIdx) {
        this.cellIdx = cellIdx;
        this.isValid = false;
        this.piece = null;
    }

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

    public int getCellIdx() {
        return cellIdx;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        this.isValid = valid;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece newPiece) {
        this.piece = newPiece;
        this.isValid = false;
    }
}
