package com.webcheckers.model;

import static com.webcheckers.model.Board.COLS;

import com.webcheckers.model.Game.Color;
import com.webcheckers.model.Piece.Type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * Row of spaces on the game board for the given row index.
 *
 * @author <a href='mailto:ajs2576@rit.edu'>Anthony Swierkosz</a>
 */
public class Row implements Iterable<Space> {

  // Fields
  private final ArrayList<Space> spaces;
  private final int index;

  /**
   * Creates a row of empty spaces for the given row index on the board.
   *
   * @param index Row index on the board.
   */
  public Row(int index) {
    this(index, (Piece) null);
  }

  /**
   * Creates a row of spaces filled with pieces of the given color for the given row index on the
   * board.
   *
   * @param index Row index on the board.
   * @param color Color of pieces being placed on valid spaces.
   */
  public Row(int index, Color color) {
    this(index, new Piece(Type.SINGLE, color));
  }

  private Row(int index, Piece piece) {
    this.index = index;
    spaces = new ArrayList<>();

    // If piece is not null then generate a new copy piece to place in the space.
    // We generate a new piece because we don't want to place the same piece in every space.
    if(piece != null) {
      piece = new Piece(piece);
    }

    // Generates row spaces
    for (int i = 0; i < COLS; i++) {

      // Calculates which spaces are black and sets them as valid spaces
      // (rowIndex + ColIndex) % 2 == 1: If row is odd and col is even or vice versa.
      if ((index + i) % 2 == 1) {
        // Add the next space as a valid space
        spaces.add(new Space(i, true, piece));
      } else {
        // Add the next space
        spaces.add(new Space(i));
      }
    }
  }

  private Row(Row row) {
    this.index = row.index;
    spaces = new ArrayList<>(row.spaces);
  }

/*  protected Row getColorInverted() {
    Row colorInvertedRow = new Row(this);

    for(Space space: colorInvertedRow.spaces) {
      if(space.getPiece() != null) {
        space.setPiece(space.getPiece().getColorInverted());
      }
    }

    return colorInvertedRow;
  }*/

  protected void reverse() {
    Collections.reverse(spaces);
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
