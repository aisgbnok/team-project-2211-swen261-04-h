package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 * The unit test suite for the {@link Position} component.
 *
 * @author <a href='mailto:ajs2576@rit.edu'>Anthony Swierkosz</a>
 */
@Tag("Model-tier")
@DisplayName("Position Model Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PositionTest {

  @Test
  @Order(1)
  @DisplayName("Can generate valid Positions")
  void constructor_validPositions() {
    Position pos1 = new Position(1, 2);
    Position pos2 = new Position(4, 7);
    Position pos3 = new Position(5, 4);
    Position pos4 = new Position(7, 0);

    assertTrue(pos1.getRow() == 1 && pos1.getCell() == 2);
    assertTrue(pos2.getRow() == 4 && pos2.getCell() == 7);
    assertTrue(pos3.getRow() == 5 && pos3.getCell() == 4);
    assertTrue(pos4.getRow() == 7 && pos4.getCell() == 0);
  }

  @Test
  @Order(2)
  @DisplayName("Can't generate invalid Positions")
  void constructor_invalidPositions() {
    // Row Out of Bounds
    assertThrows(RuntimeException.class, () -> new Position(-1, 1)); // Row Negative
    assertThrows(RuntimeException.class, () -> new Position(-3, 1)); // Row Negative
    assertThrows(RuntimeException.class, () -> new Position(8, 1)); // Row Too High
    assertThrows(RuntimeException.class, () -> new Position(12, 1)); // Row Too High

    // Column Out of Bounds
    assertThrows(RuntimeException.class, () -> new Position(0, -1)); // Column Negative
    assertThrows(RuntimeException.class, () -> new Position(0, -7)); // Column Negative
    assertThrows(RuntimeException.class, () -> new Position(0, 8)); // Column Too High
    assertThrows(RuntimeException.class, () -> new Position(0, 24)); // Column Too High

    // Row and Column Out of Bounds
    assertThrows(RuntimeException.class, () -> new Position(-1, -1)); // Negative
    assertThrows(RuntimeException.class, () -> new Position(-7, 8)); // Negative, Too High
    assertThrows(RuntimeException.class, () -> new Position(8, -7)); // Too High, Negative
    assertThrows(RuntimeException.class, () -> new Position(8, 8)); // Too High
  }

  // TODO: Add methods for testing static xInBounds methods, even though indirectly tested above
}
