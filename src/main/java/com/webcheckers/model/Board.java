package com.webcheckers.model;

import com.webcheckers.model.Game.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * Board model for generating a board and handling board related actions.
 *
 * @author <a href='mailto:ajs2576@rit.edu'>Anthony Swierkosz</a>
 */
public class Board implements Iterable<Row> {

  // Defines Board Size
  public static final int ROWS = 8; // How many rows in a board
  public static final int COLS = 8; // How many columns in a board

  // ArrayList containing all rows in the board in order. (0 to ROWS)
  private final ArrayList<Row> rows;

  /**
   * Constructs a new board.
   *
   * <p>Rows 0-2: White Pieces
   *
   * <p>Rows 3-4: Empty Spaces
   *
   * <p>Rows 5-7: Red Pieces
   */
  public Board() {
    // Calls private board constructor providing color that will be on bottom of board.
    this(Color.RED);
  }

  /**
   * Constructs a board. Board orientation determined by the currentColor parameter. If currentColor
   * is RED then red pieces will be on the bottom and white on the top, and flipped if currentColor
   * is WHITE.
   *
   * @param color Sets which color pieces to place on the bottom of the board.
   */
  private Board(Color color) {
    // Create a new list of rows and preset size to ROWS
    this.rows = new ArrayList<>(ROWS);

    // Set the otherColor to WHITE if color is RED, or vice versa
    Color otherColor = color.equals(Color.RED) ? Color.WHITE : Color.RED;

    // Loop from 0 to ROWS, placing pieces along the way
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
   * Constructs a copy of a given board. Public Copy Constructor.
   *
   * @param board Board to copy
   */
  public Board(Board board) {
    this(board, false);
  }

  public Board(Board board, boolean reflection) {
    this.rows = new ArrayList<>(ROWS);

    if (reflection) {
      for (int rowIndex = (ROWS - 1); rowIndex >= 0; rowIndex--) {
        this.rows.add(new Row(board.rows.get(rowIndex), reflection));
      }
    } else {
      for (int rowIndex = 0; rowIndex < ROWS; rowIndex++) {
        this.rows.add(new Row(board.rows.get(rowIndex), reflection));
      }
    }
  }

  @Override
  public Iterator<Row> iterator() {
    return new Board(this).rows.iterator();
  }

  /*    public void addValidMove(Move move) {
      validMoves.add(move);
  }*/

  /*    public Space getPieceAtPosition(int x, int y) {
      for (Row row : rows) {
          for (Space space : row.getSpaces()) {
              if (space.getCell() == x && space.getRow() == y) {
                  return space;
              }
          }
      }
      return null; // space not found
  }*/

  /*    public ArrayList<Move> getValidMoves() {
      ArrayList<Move> moves = new ArrayList<>();
      for (Row row : rows) {
          for (Space space : row) {
              if (space.getPiece() != null) {
                  if (space.getPiece().getColor() == Piece.Color.RED) {
                      for (Row row2 : rows) {
                          for (Space space2 : row2) {
                              if (space.getPiece() == null) {
                                  moves.add(new Move(space, space2));
                                  space2.setValid(true);
                              }
                          }
                      }
                  }
              }
          }
      }
      return moves;
  }*/

  /*    public void undoMove(Move proposedMove) {
      for (Row row : rows) {
          for (Space space : row) {
              if (proposedMove.getStart() == space) {
                  space.setPiece(proposedMove.getStart().getPiece());
                  for (Row row2 : rows) {
                      for (Space space2 : row2) {
                          if (proposedMove.getEnd() == space2) {
                              space2.setPiece(proposedMove.getEnd().getPiece());
                              lastMove = proposedMove;
                          }
                      }
                  }
              }
          }
      }
  }*/

  /*    public void makeMove(Move proposedMove) {
      for (Row row : rows) {
          for (Space space : row) {
              if (proposedMove.getStart() == space) {
                  Piece temp = space.getPiece();
                  space.setPiece(null);
                  for (Row row2 : rows) {
                      for (Space space2 : row2) {
                          if (proposedMove.getEnd() == space2) {
                              space2.setPiece(temp);
                              lastMove = proposedMove;
                          }
                      }
                  }
              }
          }
      }
  }*/
}
