package com.webcheckers.model;

/**
 * This is the {@link Position} data type. This is used by the {@link Move} data type to track
 * starting position and ending position during a move.
 *
 * @author <a href='mailto:ajs2576@rit.edu'>Anthony Swierkosz</a>
 */
public class Position {

  // Fields
  private final int row;
  private final int cell;

  /**
   * Constructor for Position data type. Holds a row index and a cell index.
   *
   * @param row Position's row index, must be integer from zero to seven.
   * @param cell Position's cell index, must be integer from zero to seven.
   */
  public Position(int row, int cell) {
    // Check to ensure row is from 0 to 7
    if ((0 <= row) && (row <= 7)) {
      this.row = row;
    } else {
      throw new IllegalArgumentException("Position: row must be between 0-7, row is " + row);
    }

    // Check to ensure cell is from 0 to 7
    if ((0 <= cell) && (cell <= 7)) {
      this.cell = cell;
    } else {
      throw new IllegalArgumentException("Position: cell must be between 0-7, cell is " + row);
    }
  }

  /**
   * Getter for the row index of this position.
   *
   * @return Position's row index, integer from zero to seven.
   */
  public int getRow() {
    return row;
  }

  /**
   * Getter for the cell (column) index of this position.
   *
   * @return Position's cell index, integer from zero to seven.
   */
  public int getCell() {
    return cell;
  }
}
