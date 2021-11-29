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

  @Test
  @Order(4)
  @DisplayName("Similar Pieces are Equal")
  void similarPiecesShouldBeEqual() {
    // These pieces have the same properties and fields, but their references are not the same.
    final Piece whiteSinglePiece1 = new Piece(Type.SINGLE, Color.WHITE);
    final Piece whiteSinglePiece2 = new Piece(Type.SINGLE, Color.WHITE);

    // Two "new" pieces with the same. They have the same fields but are not the "same"
    assertEquals(whiteSinglePiece2, whiteSinglePiece1);

    // Duplicating the pieces using Piece duplication constructor
    final Piece duplicatePiece1 = new Piece(whiteSinglePiece1);
    final Piece duplicatePiece2 = new Piece(whiteSinglePiece2);

    // whiteSinglePiece1 and duplicatePiece1 are not the same object reference
    assertEquals(whiteSinglePiece1, duplicatePiece1);
    // whiteSinglePiece2 and duplicatePiece2 are not the same object reference
    assertEquals(whiteSinglePiece2, duplicatePiece2);
  }

  @Test
  @Order(5)
  @DisplayName("Different type Pieces are not Equal")
  void piecesOfDifferentTypeShouldNotBeEqual() {
    // Create white single piece, duplicate it, and king duplicate
    final Piece whiteSinglePiece = new Piece(Type.SINGLE, Color.WHITE);
    final Piece whiteKingPiece = new Piece(whiteSinglePiece);
    whiteKingPiece.king();

    // Should not be equals
    assertNotEquals(whiteSinglePiece, whiteKingPiece);

    // Create red single piece and red king piece
    final Piece redSinglePiece = new Piece(Type.SINGLE, Color.RED);
    final Piece redKingPiece = new Piece(Type.KING, Color.RED);

    // Should not be equals
    assertNotEquals(redSinglePiece, redKingPiece);
  }
}
