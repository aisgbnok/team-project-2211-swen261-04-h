package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Board implements Iterable<Row> {

    public Move proposedMove;
    public Move lastMove;
    private final List<Row> rows;
    private final int length = 8;
    private String turn;
    private ArrayList<Move> validMoves;

    public Board() {
        this.turn = "SELF";
        this.rows = new ArrayList<>();

        for (int i = 0; i < length; i++) {
            rows.add(new Row(i, length));
        }
    }

    public void addValidMove(Move move) {
        validMoves.add(move);
    }

    public String getTurn() {
        return turn;
    }

    public void setTurn(String turn) {
        this.turn = turn;
    }

    public void fillRed() {
        for (Row row : rows) {
            int index = row.getIndex();
            if (index <= 2) {
                row.fillWhite();
            } else if (index <= 4) {

            } else {
                row.fillRed();
            }
        }
    }

    public void fillWhite() {
        for (Row row : rows) {
            int index = row.getIndex();
            if (index <= 2) {
                row.fillRed();
            } else if (index <= 4) {

            } else {
                row.fillWhite();
            }
        }
    }

    @Override
    public Iterator<Row> iterator() {
        return rows.iterator();
    }

    public List<Row> getRows() {
        return rows;
    }

    public Space getPieceAtPosition(int x, int y) {
        for (Row row : rows) {
            for (Space space : row.getSpaces()) {
                if (space.getCell() == x && space.getRow() == y) {
                    return space;
                }
            }
        }
        return null; // space not found
    }

    public ArrayList<Move> getValidMoves() {
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
    }

    public void undoMove(Move proposedMove) {
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
    }

    public void makeMove(Move proposedMove) {
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
    }
}
