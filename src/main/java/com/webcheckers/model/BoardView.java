package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BoardView implements Iterable<Row> {

  private List<Row> rows;
  private int length = 8;
  private String turn;

  private ArrayList<Move> validMoves;

  public void addValidMove(Move move) {
    validMoves.add(move);
  }


  public String getTurn() {
    return turn;
  }

  public void setTurn(String turn) {
    this.turn = turn;
  }


  public BoardView() {
    this.turn = "SELF";
    this.rows = new ArrayList<>();

    for (int i = 0; i < length; i++) {
      rows.add(new Row(i, length));
    }
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
    
    return validMoves;
  }
}
