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

  private final ArrayList<Space> spaces;
  private final int index;

  public Row(int index) {
    this.index = index;
    spaces = new ArrayList<>();

    for (int i = 0; i < COL_LENGTH; i++) {
      if ((index % 2 == 0 && i % 2 == 0) || (index % 2 == 1 && i % 2 == 1)) {
        spaces.add(new Space(i, true));
      } else {
        spaces.add(new Space(i));
      }
    }
  }

  public void fillRed() {
    for (Space space : spaces) {
      if (index % 2 == 0) {
        if (space.getCellIdx() % 2 == 1) {
          space.setPiece(new Piece(Type.SINGLE, Color.RED));
        }
      } else {
        if (space.getCellIdx() % 2 == 0) {
          space.setPiece(new Piece(Type.SINGLE, Color.RED));
        }
      }
    }
  }

  public void fillWhite() {
    for (Space space : spaces) {
      if (index % 2 == 0) {
        if (space.getCellIdx() % 2 == 1) {
          space.setPiece(new Piece(Type.SINGLE, Color.WHITE));
        }
      } else {
        if (space.getCellIdx() % 2 == 0) {
          space.setPiece(new Piece(Type.SINGLE, Color.WHITE));
        }
      }
    }
  }

  public int getIndex() {
    return index;
  }

  @Override
  public Iterator<Space> iterator() {
    return spaces.iterator();
  }

  public ArrayList<Space> getSpaces() {
    return spaces;
  }
}
