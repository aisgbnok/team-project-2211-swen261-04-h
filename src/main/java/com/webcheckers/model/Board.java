package com.webcheckers.model;

import com.webcheckers.model.Piece.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Logger;

/**
 * Board model for generating a board and handling board related actions.
 *
 * @author <a href='mailto:ajs2576@rit.edu'>Anthony Swierkosz</a>
 */
public class Board implements Iterable<Row> {

  public static final int ROWS = 8; // How many rows in a board
  public static final int COLS = 8; // How many columns in a board

  private final ArrayList<Row> rows; // Contains all rows in a board in order.

  // TODO REMOVE AFTER TESTING
  private static final Logger LOG = Logger.getLogger(Board.class.getName());

  /** Constructs a new board in the default orientation. Red pieces are generated at the bottom. */
  public Board() {
    // Calls private board constructor providing color that will be on bottom of board.
    this(Color.RED);
  }

  /**
   * Constructs a new board with a variable orientation. Color given determines which pieces are
   * generated at the bottom.
   *
   * @param color Color of pieces to place on the bottom of the board.
   */
  private Board(Color color) {
    // Create a new list of rows with default size of ROWS
    this.rows = new ArrayList<>(ROWS);

    // Set the otherColor to WHITE if color is RED, or vice versa
    Color otherColor = color.equals(Color.RED) ? Color.WHITE : Color.RED;

    // Loop from 0 to ROWS, placing rows along the way
    for (int rowIndex = 0; rowIndex < ROWS; rowIndex++) {
      // If the rowIndex is <= 2 then place pieces of the otherColor
      if (rowIndex <= 2) {
        rows.add(new Row(rowIndex, otherColor));
      }
      // If the rowIndex is >= 5 then place pieces of the given color
      else if (rowIndex >= 5) {
        rows.add(new Row(rowIndex, color));
      }
      // Every other row has empty spaces
      else {
        rows.add(new Row(rowIndex));
      }
    }
  }

  /**
   * Constructs a duplicate of a given board.
   *
   * @param board Board to duplicate
   */
  public Board(Board board) {
    this(board, false);
  }

  /**
   * Constructs a duplicate of a given board. It will reflect the board during duplication (180°
   * rotation) if reflection is true.
   *
   * @param board Board to duplicate
   * @param reflection Will generated a reflected board if true.
   */
  public Board(Board board, boolean reflection) {
    // Create a new list of rows with default size of ROWS
    this.rows = new ArrayList<>(ROWS);

    // If the board should be reflected along x-axis and y-axis:
    if (reflection) {
      // Generate new rows, adding them to the board in reverse order
      for (int rowIndex = (ROWS - 1); rowIndex >= 0; rowIndex--) {
        this.rows.add(new Row(board.rows.get(rowIndex), true));
      }
    } else {
      // Generate new rows, adding them to the board in the same order
      for (int rowIndex = 0; rowIndex < ROWS; rowIndex++) {
        this.rows.add(new Row(board.rows.get(rowIndex), false));
      }
    }
  }

  public Message validateMove(Move move) {
    // Get start and end position
    Position startPos = move.getStart();
    Position endPos = move.getEnd();
    int rowDelta = startPos.getRow() - endPos.getRow();

    // Get start and end space
    Space startSpace = this.getSpace(startPos);
    Space endSpace = this.getSpace(endPos);

    // Get start piece
    Piece startPiece = startSpace.getPiece(); // This is the piece we want to move
    Color startColor = startPiece.getColor(); // Piece Color
    Type startType = startPiece.getType(); // Piece Type

    // TODO: I think this is not needed and possibly redundant.
    if (!endSpace.isValid()) {
      return Message.error("End Space is not valid!");
    }

    // Ensure move is not invalid
    if (move.isInvalid()) {
      return Message.error("Move is not valid!");
    }

    // Ensure the piece is moving in the right direction
    // Only matters for SINGLE pieces, KINGs can move UP or DOWN
    if (startType.equals(Type.SINGLE)) {
      // RED row delta should be positive; WHITE row delta should be negative
      if ((startColor.equals(Color.RED) && (rowDelta < 0))
          || (startColor.equals(Color.WHITE) && (rowDelta > 0))) {
        return Message.error(startColor.name() + " pieces can only move " + startColor.direction());
      }
    }

    // Validate the Slide Move
    if (move.isSlide()) {
      // Determine if a jump is possible, and force the player to jump
      if (canJump(startPos)) {
        return Message.error("Must jump!");
      }

      // Valid Slide
      return Message.info("Valid slide!");
    }
    // Validate the Jump Move
    else if (move.isJump()) {
      // Validate the JUMP
      return Message.info("Valid jump!");
    }

    // Return false by default
    return Message.error("Move invalid!");
  }

  private Space getSpace(Position position) {
    return rows.get(position.getRow()).getSpace(position.getCell());
  }

  private boolean canJump(Position startPos) {
    // TODO: If it is a multiple jump move do we have to do that over a single jump?

    // Get other objects
    Space space = getSpace(startPos); // Starting Space
    Piece piece = space.getPiece(); // Starting (Moving) Piece

    // Generate correct jump increment, dependant on color direction value
    int jumpIncrement = 2 * piece.getColor().value(); // RED (Negative), WHITE (Positive)

    // Generate an empty ArrayList with a starting capacity of 2, to store possible jump moves
    ArrayList<Move> possibleJumps = new ArrayList<>(2); // (2 because always 2 SINGLE)

    // Generate SINGLE jump moves
    int possibleRow = startPos.getRow() - jumpIncrement;
    int possibleCol = startPos.getCell() - jumpIncrement;

    if (Position.isInBounds(possibleRow, possibleCol))
      possibleJumps.add(new Move(startPos, new Position(possibleRow, possibleCol)));

    // Change Column Position to other possible column
    possibleCol = startPos.getCell() + jumpIncrement;

    if (Position.isInBounds(possibleRow, possibleCol))
      possibleJumps.add(new Move(startPos, new Position(possibleRow, possibleCol)));

    if (piece.getType() == Type.KING) {
      possibleCol = startPos.getCell() - jumpIncrement;

      if (Position.isInBounds(possibleRow, possibleCol))
        possibleJumps.add(new Move(startPos, new Position(possibleRow, possibleCol)));

      possibleRow = startPos.getCell() + jumpIncrement;

      if (Position.isInBounds(possibleRow, possibleCol))
        possibleJumps.add(new Move(startPos, new Position(possibleRow, possibleCol)));
    }

    // Ensure Possible Jumps are Valid
    for (Move jump : possibleJumps) {
      // First valid jump, return true
      if (jumpValidation(jump)) return true;
    }

    // Return false by default
    return false;
  }

  private boolean jumpValidation(Move move) {
    // Get Positions
    Position startPos = move.getStart();
    Position midPos = move.getMiddle();
    Position endPos = move.getEnd();

    // Get Pieces
    Piece startPiece = getSpace(startPos).getPiece();
    Piece midPiece = getSpace(midPos).getPiece();

    // Make sure the middle position is not null
    if (midPos != null) {
      // Make sure the middle piece is not null
      // TODO this is redundant
      if (midPiece != null) {
        // Make sure the midPiece and the jumping piece is not the same color
        if (!midPiece.getColor().equals(startPiece.getColor())) {
          // Then they can jump
          return true;
        }
      }
    }

    return false;
  }

  // TODO: This is temporary
  public void performMove(Move move) {
    Space endSpace = getSpace(move.getEnd());
    Space startSpace = getSpace(move.getStart());
    endSpace.setPiece(startSpace.getPiece());
    startSpace.removePiece();
  }

  @Override
  public Iterator<Row> iterator() {
    return new Board(this).rows.iterator();
  }
}
