package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BoardView implements Iterable<Row> {

  private List<Row> rows;
  private int length = 8;

  public BoardView() {
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
        row.fillWhite();
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
        row.fillRed();
      } else {
        row.fillWhite();
      }
    }
  }

  @Override
  public Iterator<Row> iterator() {
    return rows.iterator();
  }
}
