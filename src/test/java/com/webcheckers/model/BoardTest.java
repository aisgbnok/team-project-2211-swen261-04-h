package com.webcheckers.model;

import static com.webcheckers.model.Board.INVALID_END_SPACE;
import static com.webcheckers.model.Board.INVALID_SLIDE_AFTER_SLIDE;
import static com.webcheckers.model.Board.VALID_SLIDE;
import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.util.Message;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

/**
 * The unit test suite for the {@link Board} component.
 *
 * @author <a href='mailto:ajs2576@rit.edu'>Anthony Swierkosz</a>
 */
@Tag("Model-tier")
@DisplayName("Board Model Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BoardTest {

  @Test
  @Order(1)
  @DisplayName("Default Board Constructor")
  void ctor_noArgument() {
    new Board();
  }


  @Test
  @Order(2)
  @DisplayName("Duplication Board Constructor")
  void ctor_duplicateBoard() {
    final Board ogBoard = new Board();
    new Board(ogBoard);
  }

  @Test
  @Order(3)
  @DisplayName("Reflection Board Constructor")
  void ctor_reflectBoard() {
    final Board ogBoard = new Board();
    new Board(ogBoard, true);
  }

  @Test
  @Order(4)
  @DisplayName("Validate End Space Not Valid")
  void validateMove_SpaceTaken() {
    // Generate blank board
    Board board = new Board();

    // Create different Positions
    Position R0C1 = new Position(0, 1);
    Position R6C1 = new Position(6, 1);

    Move move = new Move(R0C1, R6C1);

    // Validate Move should give INVALID_END_SPACE message
    String expected = Message.error(INVALID_END_SPACE).toString();
    String actual = board.validateMove(move).toString();

    assertEquals(expected, actual);
  }

  @Test
  @Order(5)
  @DisplayName("Validate Valid Slides Successfully")
  void validateMove_validSlides() {

    // Positions
    Position R2C1 = new Position(2, 1);
    Position R2C5 = new Position(2, 5);
    Position R3C2 = new Position(3, 2);
    Position R3C4 = new Position(3, 4);
    Position R4C1 = new Position(4, 1);
    Position R5C0 = new Position(5, 0);

    // Valid Moves
    Move R2C1toR3C2 = new Move(R2C1, R3C2);    // Move 2,1 to 3,2
    Move R2C5toR3C4 = new Move(R2C5, R3C4);    // Move 2,5 to 3,4
    Move R5C0toR4C1 = new Move(R5C0, R4C1);    // Move 5,0 to 4,1

    // Test R2C1toR3C2
    Board board1 = new Board();

    assertEquals(Message.info(VALID_SLIDE).toString(), board1.validateMove(R2C1toR3C2).toString());
    board1.performMove(R2C1toR3C2);

    // Test R2C5toR3C4
    Board board2 = new Board();

    assertEquals(Message.info(VALID_SLIDE).toString(), board2.validateMove(R2C5toR3C4).toString());
    board2.performMove(R2C5toR3C4);

    // Test R5C0toR4C1
    Board board3 = new Board();

    assertEquals(Message.info(VALID_SLIDE).toString(), board3.validateMove(R5C0toR4C1).toString());
    board3.performMove(R5C0toR4C1);
  }

  @Test
  @Order(6)
  @DisplayName("Validate Multiple Slides Error")
  void validateMove_twoSlidesError() {

    // Positions
    Position R2C1 = new Position(2, 1);
    Position R2C5 = new Position(2, 5);
    Position R3C2 = new Position(3, 2);

    Position R3C6 = new Position(3, 6);
    Position R4C7 = new Position(4, 7);
    Position R5C6 = new Position(5, 6);

    // Multiple Slides in single turn
    Move R5C6toR4C7 = new Move(R5C6, R4C7);    // Move 5,6 to 4,7
    Move R4C7toR3C6 = new Move(R4C7, R3C6);    // Move 4,7 to 3,6

    // Test R5C6toR4C7
    Board board1 = new Board();

    assertEquals(Message.info(VALID_SLIDE).toString(), board1.validateMove(R5C6toR4C7).toString());
    board1.performMove(R5C6toR4C7);

    // Test R4C7toR3C6: Invalid Second Slide
    assertEquals(Message.error(INVALID_SLIDE_AFTER_SLIDE).toString(), board1.validateMove(R4C7toR3C6).toString());
  }

  //TODO: Add test for move backwards

  //TODO: add test for jump

/*  @Test
  @DisplayName("Validate Moves Successfully")
  void validateMove_validMove() {
    Board board = new Board();

    Position R2C1 = new Position(2, 1);
    Position R2C5 = new Position(2, 5);
    Position R3C2 = new Position(3, 2);
    Position R3C4 = new Position(3, 4);
    Position R4C1 = new Position(4, 1);
    Position R5C0 = new Position(5, 0);


    Move R2C1toR3C2 = new Move(R2C1, R3C2);    // Move WHITE 2,1 to 3,2
    Move R2C5toR3C4 = new Move(R2C5, R3C4);    // Move WHITE 2,5 to 3,4

    // Validate and perform moves
    assertEquals(Message.info(VALID_SLIDE).toString(), board.validateMove(R2C1toR3C2).toString());
    board.performMove(R2C1toR3C2);

    // Need
    assertEquals(Message.info(VALID_SLIDE).toString(), board.validateMove(R2C5toR3C4).toString());
    board.performMove(R2C1toR3C2);

    //board.validateMove(R2C5toR3C4);
    //board.performMove(R2C5toR3C4);
  }*/
}
