package com.webcheckers.model;

import static com.webcheckers.model.Board.COLS;

import com.webcheckers.model.Game.Color;
import com.webcheckers.model.Piece.Type;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Row of spaces on the game board for the given row index.
 *
 * @author <a href='mailto:ajs2576@rit.edu'>Anthony Swierkosz</a>
 */
public class Row implements Iterable<Space> {

  private final int index; // Row index on the board.
  private final ArrayList<Space> spaces; // Contains all spaces in a row in order.

  /**
   * Constructs a row of empty spaces for the given row index on the board.
   *
   * @param index Row index on the board.
   */
  public Row(int index) {
    this(index, null);
  }

  /**
   * Constructs a row of spaces filled with pieces of the given color for the given row index on the
   * board.
   *
   * @param index Row index on the board.
   * @param color Color of pieces being placed on valid spaces.
   */
  public Row(int index, Color color) {
    this.index = index;
    this.spaces = new ArrayList<>(COLS);

    // Generates row spaces
    for (int i = 0; i < COLS; i++) {

      // Calculates which spaces are black and sets them as valid spaces
      // (rowIndex + ColIndex) % 2 == 1: If row is odd and col is even or vice versa.
      if ((index + i) % 2 == 1) {
        // Add the next space as a valid space
        spaces.add(new Space(i, true, color));
      } else {
        // Add the next space
        spaces.add(new Space(i));
      }
    }
  }

  public Row(Row row, Boolean reflection) {
    this.index = row.index;
    this.spaces = new ArrayList<>(COLS);

    if (reflection) {
      for (int colIndex = (COLS - 1); colIndex >= 0; colIndex--) {
        spaces.add(new Space(row.spaces.get(colIndex)));
      }
    } else {
      for (int colIndex = 0; colIndex < COLS; colIndex++) {
        spaces.add(new Space(row.spaces.get(colIndex)));
      }
    }
  }

  /**
   * Getter for the row index.
   *
   * @return Row index
   */
  public int getIndex() {
    return index;
  }

  @Override
  public Iterator<Space> iterator() {
    return spaces.iterator();
  }
}
