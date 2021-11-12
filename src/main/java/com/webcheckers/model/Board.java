package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Board model for generating a board and handling board related actions.
 *
 * @author <a href='mailto:ajs2576@rit.edu'>Anthony Swierkosz</a>
 */
public class Board implements Iterable<Row> {

  public static final int ROWS = 8; // How many rows in a board
  public static final int COLS = 8; // How many columns in a board

  private final ArrayList<Row> rows; // Contains all rows in a board in order.

  /** Constructs a new board in the default orientation. Red pieces are generated at the bottom. */
  public Board() {
    // Calls private board constructor providing color that will be on bottom of board.
    this(Color.RED);
  }

  /**
   * Constructs a new board with a variable orientation. Color given determines which pieces are
   * generated at the bottom.
   *
   * @param color Color of pieces to place on the bottom of the board.
   */
  private Board(Color color) {
    // Create a new list of rows with default size of ROWS
    this.rows = new ArrayList<>(ROWS);

    // Set the otherColor to WHITE if color is RED, or vice versa
    Color otherColor = color.equals(Color.RED) ? Color.WHITE : Color.RED;

    // Loop from 0 to ROWS, placing rows along the way
    for (int rowIndex = 0; rowIndex < ROWS; rowIndex++) {
      // If the rowIndex is <= 2 then place pieces of the otherColor
      if (rowIndex <= 2) {
        rows.add(new Row(rowIndex, otherColor));
      }
      // If the rowIndex is >= 5 then place pieces of the given color
      else if (rowIndex >= 5) {
        rows.add(new Row(rowIndex, color));
      }
      // Every other row has empty spaces
      else {
        rows.add(new Row(rowIndex));
      }
    }
  }

  /**
   * Constructs a duplicate of a given board.
   *
   * @param board Board to duplicate
   */
  public Board(Board board) {
    this(board, false);
  }

  /**
   * Constructs a duplicate of a given board. It will reflect the board during duplication (180°
   * rotation) if reflection is true.
   *
   * @param board Board to duplicate
   * @param reflection Will generated a reflected board if true.
   */
  public Board(Board board, boolean reflection) {
    // Create a new list of rows with default size of ROWS
    this.rows = new ArrayList<>(ROWS);

    // If the board should be reflected along x-axis and y-axis:
    if (reflection) {
      // Generate new rows, adding them to the board in reverse order
      for (int rowIndex = (ROWS - 1); rowIndex >= 0; rowIndex--) {
        this.rows.add(new Row(board.rows.get(rowIndex), true));
      }
    } else {
      // Generate new rows, adding them to the board in the same order
      for (int rowIndex = 0; rowIndex < ROWS; rowIndex++) {
        this.rows.add(new Row(board.rows.get(rowIndex), false));
      }
    }
  }

  @Override
  public Iterator<Row> iterator() {
    return new Board(this).rows.iterator();
  }
}
