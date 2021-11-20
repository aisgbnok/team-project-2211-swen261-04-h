package com.webcheckers.model;

import com.webcheckers.model.Piece.Type;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Board model for generating a board and handling board related actions.
 *
 * @author <a href='mailto:ajs2576@rit.edu'>Anthony Swierkosz</a>
 */
public class Board implements Iterable<Row> {

  public static final int ROWS = 8; // How many rows in a board
  public static final int COLS = 8; // How many columns in a board

  private final ArrayList<Row> rows; // Contains all rows in a board in order.

  private boolean hasSlid; // A slide has occurred previously
  private boolean hasJumped; // A jump has occurred previously

  /*
   * Validation Messages // TODO: Possibly Move into utility tier
   */
  private static final String INVALID_DIRECTION = "%s pieces can only move %s!";
  private static final String INVALID_END_SPACE = "End space is not valid!";
  private static final String INVALID_MOVE = "Invalid Move";
  private static final String INVALID_JUMP = "Invalid Jump";
  private static final String INVALID_JUMP_AFTER_SLIDE = "You can't jump after a slide!";
  private static final String INVALID_SLIDE_AFTER_JUMP = "You can't slide after a jump!";
  private static final String INVALID_SLIDE_AFTER_SLIDE = "You can't slide twice!";
  private static final String INVALID_SLIDE_WHEN_JUMP = "A jump is possible, you must jump!";

  private static final String VALID_SLIDE = "Valid Slide";
  private static final String VALID_JUMP = "Valid Jump";

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
    // Create a new list of rows with initial capacity of ROWS
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
    // Create a new list of rows with initial capacity of ROWS
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

  // TODO think about validation flow to ensure no exceptions
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
      return Message.error(INVALID_END_SPACE);
    }

    // Ensure move is not invalid
    if (move.isInvalid()) {
      return Message.error(INVALID_MOVE);
    }

    // Ensure the piece is moving in the right direction
    if ((startType == Type.SINGLE) // Only matters for SINGLE pieces, KINGs can move UP or DOWN
        && ((startColor == Color.RED && rowDelta < 0) // RED row delta should be positive
            || (startColor == Color.WHITE && rowDelta > 0))) // WHITE row delta should be negative
    {
      return Message.error(
          String.format(INVALID_DIRECTION, startColor.name(), startColor.direction()));
    }

    // Validate the Slide Move
    if (move.isSlide()) {

      // If a slide has already occurred return an error message
      if (hasSlid) {
        return Message.error(INVALID_SLIDE_AFTER_SLIDE);
      }
      // If a jump has already occurred return an error message
      else if (hasJumped) {
        return Message.error(INVALID_SLIDE_AFTER_JUMP);
      }

      // Determine if a jump is possible, and tell the player to jump
      if (canJump(startPos)) {
        return Message.error(INVALID_SLIDE_WHEN_JUMP);
      }

      // Valid Slide
      return Message.info(VALID_SLIDE);
    }

    // Validate the Jump Move
    else if (move.isJump()) {

      // If a slide has already occurred return an error message
      if (hasSlid) {
        return Message.error(INVALID_JUMP_AFTER_SLIDE);
      }

      // Validate the JUMP
      // TODO improve error reporting
      return jumpValidation(move) ? Message.info(VALID_JUMP) : Message.error(INVALID_JUMP);
    }

    // Return false by default
    // TODO this should never happen, added (Edge Case) for testing.
    return Message.error("Invalid Move (Edge Case)");
  }

  private Space getSpace(Position position) {
    return rows.get(position.getRow()).getSpace(position.getCell());
  }

  // TODO think about validation flow to ensure no exceptions
  private boolean canJump(Position startPos) {
    // TODO: If it is a multiple jump move do we have to do that over a single jump?

    Space space = getSpace(startPos); // Starting Space
    Piece piece = space.getPiece(); // Starting (Moving) Piece

    // Generate correct jump increment, dependant on color direction value
    int jumpIncrement = 2 * piece.getColor().value(); // RED (Negative), WHITE (Positive)

    // Create possible jump positions
    int[][] possiblePositions = {
      // SINGLES
      {startPos.getRow() + jumpIncrement, startPos.getCell() - jumpIncrement}, // +, -
      {startPos.getRow() + jumpIncrement, startPos.getCell() + jumpIncrement}, // +, +
      // KINGS
      {startPos.getRow() - jumpIncrement, startPos.getCell() - jumpIncrement}, // -, -
      {startPos.getRow() - jumpIncrement, startPos.getCell() + jumpIncrement}, // - , +
    };

    // Go through all positions if it is a KING; only first two if it is a SINGLE
    int maxLoop = piece.getType() == Type.KING ? 4 : 2;

    // Generate an empty ArrayList to store possible jump moves
    ArrayList<Move> possibleJumps = new ArrayList<>(maxLoop);

    // For maxLoop possible positions generate possible jumps, if the position is valid
    for (int i = 0; i < maxLoop; i++) {
      int row = possiblePositions[i][0];
      int col = possiblePositions[i][1];

      if (Position.isInBounds(row, col))
        possibleJumps.add(new Move(startPos, new Position(row, col)));
    }

    // Check if possible jumps are valid
    for (Move jump : possibleJumps) {
      // If a jump is valid, return true. The piece can jump.
      if (jumpValidation(jump)) return true;
    }

    // Return false by default
    return false;
  }

  // TODO think about validation flow to ensure no exceptions
  private boolean jumpValidation(Move move) {
    Position midPos = move.getMiddle(); // Position between start and end position
    Piece midPiece = getSpace(midPos).getPiece(); // Piece at middle position
    Piece startPiece = getSpace(move.getStart()).getPiece(); // Piece at start position

    // Make sure the middle position is not null
    if (midPos != null) {
      // Make sure the middle piece is not null
      // TODO this is redundant?
      if (midPiece != null) {
        // Make sure the midPiece and the jumping piece is not the same color
        if (midPiece.getColor() != startPiece.getColor()) {
          // Then they can jump
          return true;
        }
      }
    }

    // Return false by default
    return false;
  }

  protected void performMove(Move move) {
    // Get objects needed for slide
    Space startSpace = getSpace(move.getStart());
    Space endSpace = getSpace(move.getEnd());
    Piece startPiece = startSpace.getPiece(); // This is the piece we want to move

    // Technically perform move should only be called on validatedMoves in Game.pendingMoves
    // We will do very simple error checking just to be sure anyway
    if (move.isInvalid()) return;

    // If the move is a slide
    if (move.isSlide()) {
      // Move the piece (perform slide)
      endSpace.setPiece(startPiece); // Set endSpace piece to startPiece object reference
      startSpace.removePiece(); // Remove startPiece object reference from startSpace
      hasSlid = true; // A slide has occurred
      return; // Finished
    }

    // Get objects needed for jump
    Space midSpace = getSpace(move.getMiddle());

    // If the move is a jump && is legal/validated
    if (move.isJump() && jumpValidation(move)) {
      // Move the piece (perform jump)
      endSpace.setPiece(startPiece); // Set endSpace piece to startPiece object reference
      startSpace.removePiece(); // Remove startPiece object reference from startSpace

      // Capture (remove) the middle piece
      midSpace.removePiece();

      hasJumped = true; // A jump has occurred
    }
  }

  @Override
  public Iterator<Row> iterator() {
    return new Board(this).rows.iterator();
  }
}
