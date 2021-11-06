package com.webcheckers.model;

import static com.webcheckers.model.Board.COL_LENGTH;

import com.webcheckers.model.Game.Color;
import com.webcheckers.model.Piece.Type;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Row on the board.
 *
 * @author <a href='mailto:ajs2576@rit.edu'>Anthony Swierkosz</a>
 */
public class Row implements Iterable<Space> {

  // Fields
  private final ArrayList<Space> spaces;
  private final int index;

  /**
   * Creates a board row for the given index.
   *
   * @param index - Row index on the board.
   */
  public Row(int index) {
    this.index = index;
    spaces = new ArrayList<>();

    // Generates row spaces
    for (int i = 0; i < COL_LENGTH; i++) {

      // Calculates which spaces are black and sets them as valid spaces
      // (rowIndex + ColIndex) % 2 == 1: If row is odd and col is even or vice versa.
      if ((index + i) % 2 == 1) {
        // Add the next space as a valid space
        spaces.add(new Space(i, true));
      } else {
        // Add the next space
        spaces.add(new Space(i));
      }
    }
  }

  /**
   * Fills the row with the given color pieces.
   *
   * @param color Pieces color enum, WHITE or RED.
   */
  public void fill(Color color) {
    for (Space space : spaces) {
      // Calculates which spaces are black and places pieces on them
      // (rowIndex + ColIndex) % 2 == 1: If row is odd and col is even or vice versa.
      if ((index + space.getCellIdx()) % 2 == 1) {
        space.setPiece(new Piece(Type.SINGLE, color));
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
   * Getter for the row spaces.
   *
   * @return ArrayList of all the spaces in the row.
   */
  public ArrayList<Space> getSpaces() {
    return spaces;
  }

  @Override
  public Iterator<Space> iterator() {
    return spaces.iterator();
  }
}
