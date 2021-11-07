package com.webcheckers.model;

import com.webcheckers.model.Game.Color;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Board model for generating a board and handling board related actions.
 *
 * @author <a href='mailto:ajs2576@rit.edu'>Anthony Swierkosz</a>
 */
public class Board implements Iterable<Row> {

  // Fields
  public static final int ROWS = 8;
  public static final int COLS = 8;

  private final ArrayList<Row> rows;

  /** Creates a board. */
  public Board() {
    this(Color.RED);
  }

  public Board(Board board) {
    this.rows = new ArrayList<>(board.rows);
  }

  /**
   * Sets up the board layout by generating and placing starter pieces. Sets the orientation of *
   * piece placement based on provided currentColor parameter. If currentColor is RED then red *
   * pieces will be on the bottom and white on the top, and flipped if currentColor is WHITE.
   *
   * @param color Sets which color pieces to place on the bottom of the board.
   */
  private Board(Color color) {
    this.rows = new ArrayList<>(ROWS);

    Color otherColor = color.equals(Color.RED) ? Color.WHITE : Color.RED;

    for (int rowIndex = 0; rowIndex < ROWS; rowIndex++) {
      if (rowIndex <= 2) {
        rows.add(new Row(rowIndex, otherColor));
      } else if (rowIndex >= 5) {
        rows.add(new Row(rowIndex, color));
      } else {
        rows.add(new Row(rowIndex));
      }
    }
  }

public  Board getBoard(Color color) {
    Board newBoard = new Board(this);

  if(color.equals(Color.WHITE)) {

    for(Row row: newBoard.rows) {
      row = row.getColorInverted();
    }
  }

  return newBoard;

}

  @Override
  public Iterator<Row> iterator() {
    ArrayList<Row> newArray = new ArrayList<>(rows);

    return newArray.iterator();
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
