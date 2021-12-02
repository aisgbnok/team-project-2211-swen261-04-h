package com.webcheckers.util;

import com.webcheckers.model.*;

public class AI {

    public static Move getNextMove(Game game) {
        Board board = game.getBoard();
        for (Row row : board.getRows()) {
            for (Space space : row.getSpaces()) {
                for (Row row2 : board.getRows()) {
                    for (Space space2 : row.getSpaces()) {
                        Move newMove = new Move(new Position(space.getCellIdx(), row.getIndex()), new Position(space2.getCellIdx(), row2.getIndex()));
                        if (board.isMoveValid(newMove)) {
                            return newMove;
                        }
                    }
                }
            }
        }
        //Probably a bad idea
        return null;
    }
}
