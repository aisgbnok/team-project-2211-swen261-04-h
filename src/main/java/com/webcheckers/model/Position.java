package com.webcheckers.model;

import static com.webcheckers.model.Board.COLS;
import static com.webcheckers.model.Board.ROWS;

/**
 * This is the {@link Position} data type. This is used by the {@link Move} data type to track
 * starting position and ending position during a game piece move.
 *
 * @author <a href='mailto:ajs2576@rit.edu'>Anthony Swierkosz</a>
 */
public class Position {

  private final int row; // Position's row index
  private final int cell; // Position's cell index

  /**
   * Constructs a Position. Holds a row index and a cell index.
   *
   * @param row Position's row index, must be integer from zero to ROWS.
   * @param cell Position's cell index, must be integer from zero to COLS.
   */
  public Position(int row, int cell) {
    // Check to ensure row is within 0 and ROWS
    if ((0 <= row) && (row < ROWS)) {
      this.row = row;
    } else {
      throw new IllegalArgumentException(
          "Position: row must be within 0 and row size, row is " + row);
    }

    // Check to ensure cell is within 0 and COLS
    if ((0 <= cell) && (cell < COLS)) {
      this.cell = cell;
    } else {
      throw new IllegalArgumentException(
          "Position: cell must be within 0 and column size, cell is " + cell);
    }
  }

  /**
   * Getter for the row index of this position.
   *
   * @return Position's row index, integer from zero to ROWS.
   */
  public int getRow() {
    return row;
  }

  /**
   * Getter for the cell (column) index of this position.
   *
   * @return Position's cell index, integer from zero to COLS.
   */
  public int getCell() {
    return cell;
  }
}
