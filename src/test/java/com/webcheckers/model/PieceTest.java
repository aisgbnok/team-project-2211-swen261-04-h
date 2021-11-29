package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.model.Piece.Type;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;


/**
 * The unit test suite for the {@link Piece} component.
 *
 * @author <a href='mailto:idc7947@rit.edu'>Ian Chasse</a>
 * @author <a href='mailto:ajs2576@rit.edu'>Anthony Swierkosz</a>
 */
@Tag("Model-tier")
@DisplayName("Piece Model Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PieceTest {

    @Test
    @Order(1)
    @DisplayName("Piece Constructors")
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
    @Order(4)
    @DisplayName("Pieces can be Kinged")
    void testKing(){
        Piece piece = new Piece(Piece.Type.SINGLE, Color.WHITE);
        assertEquals(Piece.Type.SINGLE, piece.getType());
        assertNotEquals(Piece.Type.KING, piece.getType());
        piece.king();
        assertEquals(Piece.Type.KING, piece.getType());
        assertNotEquals(Piece.Type.SINGLE, piece.getType());
    }
}
