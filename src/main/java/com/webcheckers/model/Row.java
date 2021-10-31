package com.webcheckers.model;

import com.webcheckers.model.Piece.Color;
import com.webcheckers.model.Piece.Type;

import java.util.ArrayList;
import java.util.Iterator;

public class Row implements Iterable<Space> {

    private final ArrayList<Space> spaces;
    private final int index;
    private final int length;

    public Row(int index, int length) {
        this.index = index;
        this.length = length;
        spaces = new ArrayList<>();

        for (int i = 0; i < length; i++) {
            spaces.add(new Space(i));
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
