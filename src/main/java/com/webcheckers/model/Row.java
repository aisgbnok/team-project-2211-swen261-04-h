package com.webcheckers.model;

import static com.webcheckers.model.Board.COLS;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Row of spaces on the game board for the given row index.
 *
 * @author <a href='mailto:ajs2576@rit.edu'>Anthony Swierkosz</a>
 */
public class Row implements Iterable<Space> {

  private final int index; // Row's index on the board.
  private final ArrayList<Space> spaces; // Contains all spaces in a row in order.

  /**
   * Constructs a row of empty spaces.
   *
   * @param index Row's index on the board.
   */
  public Row(int index) {
    this(index, null);
  }

  /**
   * Constructs a row of spaces filled with pieces of the given color.
   *
   * @param index Row's index on the board.
   * @param color Color of pieces being placed on valid spaces.
   */
  public Row(int index, Color color) {
    this.index = index; // Row's index on the board
    this.spaces = new ArrayList<>(COLS); // Create a new list of spaces with default size of COLS

    // Loop from 0 to COLS, placing spaces and pieces along the way
    for (int colIndex = 0; colIndex < COLS; colIndex++) {

      // Calculates which spaces are black and sets them as valid spaces
      // (rowIndex + ColIndex) % 2 == 1: If row is odd and col is even or vice versa.
      if ((index + colIndex) % 2 == 1) {
        // Add the next space as a valid space and pass possible piece color to space
        spaces.add(new Space(colIndex, true, color));
      } else {
        // Add the next space as not valid
        spaces.add(new Space(colIndex));
      }
    }
  }

  /**
   * Constructs a duplicate of a given row.
   *
   * @param row Row to duplicate
   */
  public Row(Row row) {
    this(row, false);
  }

  /**
   * Constructs a duplicate of a given row. It will reflect the row during duplication (180°
   * rotation) if reflection is true.
   *
   * @param row Row to duplicate
   * @param reflection Will generated a reflected Row if true.
   */
  public Row(Row row, boolean reflection) {
    this.index = row.index; // Row's index on the board
    this.spaces = new ArrayList<>(COLS); // Create a new list of spaces with default size of COLS

    // If the row should be reflected (reversed):
    if (reflection) {
      // Generate new spaces, adding them to the row in reverse order
      for (int colIndex = (COLS - 1); colIndex >= 0; colIndex--) {
        spaces.add(new Space(row.spaces.get(colIndex)));
      }
    } else {
      // Generate new spaces, adding them to the row in the same order
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

  /**
   * Getter for the space at the given column index.
   *
   * @param colIndex Column index of space to return.
   * @return Space object at given column index.
   */
  protected Space getSpace(int colIndex) {
    return spaces.get(colIndex);
  }

  /**
   * Gets all positions that contain piece of given color on the row. Find positions based on piece
   * color.
   *
   * @param color Color of pieces to get positions for.
   * @return All positions that contain a piece of the given color.
   */
  protected ArrayList<Position> getPiecePositions(Color color) {
    // Create new array for storing positions
    ArrayList<Position> positions = new ArrayList<>();

    // TODO: Only check black spaces
    // Find positions of spaces that contain piece of given color
    for (Space space : spaces) {
      // Get the piece on the space
      Piece piece = space.getPiece();

      // If piece exists, and it's color matches,then generate position
      if (piece != null && piece.getColor() == color) {
        positions.add(new Position(this.index, space.getCellIdx()));
      }
    }

    // Return all matching positions
    return positions;
  }

  @Override
  public Iterator<Space> iterator() {
    return new Row(this).spaces.iterator();
  }
}
