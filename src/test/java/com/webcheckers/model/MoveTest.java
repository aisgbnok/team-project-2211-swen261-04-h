package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@Tag("Model-tier")
public class MoveTest {
    @Test
    void testmakeNewMove(){
        Move move = new Move(new Position(1, 1), new Position(2, 2));
        assertNotNull(move);
        Move move1 = new Move(new Position(5,4), new Position(4,3 ));
        assertEquals(Move.Type.SLIDE, move1.getType());
        Move move2 = new Move(new Position(5,4), new Position(3,2 ));
        assertEquals(Move.Type.JUMP, move2.getType());
        Move move3 = new Move(new Position(0,0), new Position(7,7 ));
        assertEquals(Move.Type.INVALID, move3.getType());
    }

    @Test
    void testgetStart(){
        Position position = new Position(7,1);
        Position position2 = new Position(0,7);
        Move move = new Move(position,position2);
        assertEquals(move.getStart(), position, "Start positions aren't the same");
    }

    @Test
    void testgetEnd(){
        Position position = new Position(7,1);
        Position position2 = new Position(0,7);
        Move move = new Move(position,position2);
        assertEquals(move.getEnd(), position2, "End positions aren't the same");
    }

    @Test
    void testgetMiddle(){
        Position position = new Position(5,4);
        Position position2 = new Position(3,2);
        Move move = new Move(position,position2);
        Position between = move.getMiddle();
        assertEquals(between.getRow(), 4, "Between doesn't work");
        assertEquals(between.getCell(), 3, "Between doesn't work");

        Position position3 = new Position(7,1);
        Position position4 = new Position(0,7);
        Move move2 = new Move(position3,position4);
        Position middle = move2.getMiddle();
        assertNull(middle);
    }

    @Test
    void testTypes(){
        Move move1 = new Move(new Position(5,4), new Position(4,3 ));
        assertTrue(move1.isSlide());
        assertFalse(move1.isJump());
        assertFalse(move1.isInvalid());
        Move move2 = new Move(new Position(5,4), new Position(3,2 ));
        assertFalse(move2.isSlide());
        assertTrue(move2.isJump());
        assertFalse(move2.isInvalid());
        Move move3 = new Move(new Position(0,0), new Position(7,7 ));
        assertEquals(Move.Type.INVALID, move3.getType());
        assertFalse(move3.isSlide());
        assertFalse(move3.isJump());
        assertTrue(move3.isInvalid());
    }

}
