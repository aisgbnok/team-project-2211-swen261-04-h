package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@Tag("Model-tier")
public class SpaceTest {
    @Test
    void testMakeNewSpace(){
        Space space = new Space(0);
        assertNotNull(space);
        Space space2 = new Space(1, false);
        assertNotNull(space2);
        Space space3 = new Space(2, true, Color.RED);
        assertNotNull(space3);
    }

    @Test
    void testGetCellID(){
        Space space = new Space(0);
        assertEquals(0, space.getCellIdx());
        Space space1 = new Space(5);
        assertEquals(5, space1.getCellIdx());
    }

    @Test
    void testGetPiece(){
        Space space = new Space(0);
        assertNull(space.getPiece());
        Space space2 = new Space(1, false);
        assertNull(space2.getPiece());
        Space space3 = new Space(2, true, Color.RED);
        assertNotNull(space3.getPiece());
        assertEquals(Piece.Type.SINGLE, space3.getPiece().getType());
        assertEquals(Color.RED, space3.getPiece().getColor());
    }

    @Test
    void testValidity(){
        Space space = new Space(0);
        assertFalse(space.isValid());
        Space space1 = new Space(1, true);
        assertTrue(space1.isValid());

        space.setValid();
        assertTrue(space.isValid());
        space.setValid(false);
        assertFalse(space.isValid());
    }

    @Test
    void testSetANDremovePiece(){
        Space space = new Space(2, true);
        assertNull(space.getPiece());
        Piece piece = new Piece(Piece.Type.SINGLE, Color.RED);
        space.setPiece(piece);
        assertNotNull(space.getPiece());
        assertEquals(Piece.Type.SINGLE, space.getPiece().getType());
        assertEquals(Color.RED, space.getPiece().getColor());
        space.removePiece();
        assertNull(space.getPiece());
    }

    @Test
    void testIsFull(){
        Space space = new Space(0);
        assertFalse(space.isFull());
        Space space1 = new Space(2, true, Color.RED);
        assertTrue(space1.isFull());
    }
}
