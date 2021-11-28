package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@Tag("Model-tier")
public class PieceTest {
    @Test
    void testNewPiece(){
        Piece piece = new Piece(Piece.Type.SINGLE, Color.WHITE);
        assertNotNull(piece);
        Piece piece2 = new Piece(piece);
        assertNotNull(piece2);
        assertEquals(piece2, piece);
    }

    @Test
    void testgetTypeANDColor(){
        Piece piece = new Piece(Piece.Type.SINGLE, Color.WHITE);
        assertNotEquals(Color.RED, piece.getColor());
        assertEquals(Color.WHITE, piece.getColor());
        Piece piece2 = new Piece(Piece.Type.SINGLE, Color.RED);
        assertNotEquals(Color.WHITE, piece2.getColor());
        assertEquals(Color.RED, piece2.getColor());
        assertEquals(Piece.Type.SINGLE, piece.getType());
        assertEquals(Piece.Type.SINGLE, piece2.getType());
    }

    @Test
    void testKing(){
        Piece piece = new Piece(Piece.Type.SINGLE, Color.WHITE);
        assertEquals(Piece.Type.SINGLE, piece.getType());
        assertNotEquals(Piece.Type.KING, piece.getType());
        piece.king();
        assertEquals(Piece.Type.KING, piece.getType());
        assertNotEquals(Piece.Type.SINGLE, piece.getType());
    }
}
