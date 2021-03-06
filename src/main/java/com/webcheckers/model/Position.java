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
    // Check to ensure given row is in bounds
    if (isRowInBounds(row)) {
      this.row = row;
    } else {
      throw new IllegalArgumentException(
          "Position: row must be within 0 and " + ROWS + ". row is " + row);
    }

    // Check to ensure given cell is in bounds
    if (isCellInBounds(cell)) {
      this.cell = cell;
    } else {
      throw new IllegalArgumentException(
          "Position: cell must be within 0 and " + COLS + ". cell is " + cell);
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

  /**
   * Determines if the given row index is on the board or not.
   *
   * @param row The row index to check
   * @return True if row index is in bounds (on the board), or false if it is not.
   */
  public static boolean isRowInBounds(int row) {
    // Check to ensure row is within 0 and ROWS
    return (0 <= row) && (row < ROWS);
  }

  /**
   * Determines if the given cell index is on the board or not.
   *
   * @param cell The cell index to check
   * @return True if cell index is in bounds (on the board), or false if it is not.
   */
  public static boolean isCellInBounds(int cell) {
    return (0 <= cell) && (cell < COLS);
  }

  /**
   * Determines if the given cell index and row index are on the board or not.
   *
   * @param row The row index to check
   * @param cell The cell index to check
   * @return True if indexes are in bounds (on the board), or false if they are not.
   */
  public static boolean isInBounds(int row, int cell) {
    // Check to ensure cell is within 0 and COLS
    return isRowInBounds(row) && isCellInBounds(cell);
  }
}
