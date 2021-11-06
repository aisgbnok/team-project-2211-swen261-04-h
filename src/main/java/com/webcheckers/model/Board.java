package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Board model for handling board related actions.
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
    public Move lastMove;
    //private ArrayList<Move> validMoves;

    public Board() {
        this.rows = new ArrayList<>();

        for (int i = 0; i < ROW_LENGTH; i++) {
            rows.add(new Row(i));
        }
    }

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
