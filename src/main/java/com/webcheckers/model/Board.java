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
  public static final int ROW_LENGTH = 8;
  public static final int COL_LENGTH = 8;

  private final ArrayList<Row> rows;

  // TODO Review
  public Move proposedMove;
  // public Move lastMove;
  // private ArrayList<Move> validMoves;

  /** Creates a board. */
  public Board() {
    this.rows = new ArrayList<>();

    for (int i = 0; i < ROW_LENGTH; i++) {
      rows.add(new Row(i));
    }

    /*
    TODO Implement after rewriting board iteration.
    this.rows = (ArrayList<Row>) Stream.generate(Row::new)
    .limit(ROW_LENGTH)
    .collect(Collectors.toList());
    */
  }

  /**
   * Sets up the board layout by generating and placing starter pieces. Sets the orientation of
   * piece placement based on provided currentColor parameter. If currentColor is RED then red
   * pieces will be on the bottom and white on the top, and flipped if currentColor is WHITE.
   *
   * @param currentColor Sets which color pieces to place on the bottom of the board.
   */
  public void fill(Color currentColor) {
    Color otherColor = currentColor.equals(Color.RED) ? Color.WHITE : Color.RED;

    for (Row row : rows) {
      int index = row.getIndex();

      if (index <= 2) {
        row.fill(otherColor);
      } else if (index >= 5) {
        row.fill(currentColor);
      }
    }
  }

  /*    public void addValidMove(Move move) {
      validMoves.add(move);
  }*/

  /*TODO rewrite board iteration to output iterable list but not be iterable. Right now we use a single board but oddly and somehow refill the board to display the correct user pieces. I'm confused.*/

  @Override
  public Iterator<Row> iterator() {
    return rows.iterator();
  }

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
