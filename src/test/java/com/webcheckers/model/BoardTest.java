package com.webcheckers.model;

import static com.webcheckers.model.Board.INVALID_END_SPACE;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for the {@link Board} component.
 *
 * @author <a href='mailto:ajs2576@rit.edu'>Anthony Swierkosz</a>
 */
@Tag("Model-tier")
@DisplayName("Board Model Tests")
public class BoardTest {

  @Test
  @DisplayName("Default Board Constructor")
  void ctor_noArgument() {
    new Board();
  }


  @Test
  @DisplayName("Duplication Board Constructor")
  void ctor_duplicateBoard() {
    final Board ogBoard = new Board();
    new Board(ogBoard);
  }

  @Test
  @DisplayName("Reflection Board Constructor")
  void ctor_reflectBoard() {
    final Board ogBoard = new Board();
    new Board(ogBoard, true);
  }

  @BeforeEach
  void init() {
    // Generate blank board
    Board board = new Board();
  }

  @Test
  @DisplayName("Move Invalid")
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
}
