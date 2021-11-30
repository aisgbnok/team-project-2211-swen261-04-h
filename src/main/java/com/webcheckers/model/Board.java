package com.webcheckers.model;

import com.webcheckers.model.Piece.Type;
import com.webcheckers.util.Message;
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

  // FIXME: Think about implications when submitting a turn? How do we reset them?
  private boolean hasSlid; // A slide has occurred previously
  private boolean hasJumped; // A jump has occurred previously

  /*
   * Validation Messages // TODO: Possibly Move into utility tier
   */
  private static final String INVALID_MOVE = "Invalid Move";
  private static final String INVALID_SLIDE = "Invalid Slide";
  private static final String INVALID_JUMP = "Invalid Jump";
  private static final String INVALID_DIRECTION = "%s pieces can only move %s!";
  private static final String INVALID_END_SPACE = "End space is not valid!";
  private static final String INVALID_JUMP_AFTER_SLIDE = "You can't jump after a slide!";
  private static final String INVALID_JUMP_SAME_PIECE = "You can't jump over your own pieces!";
  private static final String INVALID_JUMP_EMPTY_MIDDLE = "You can't jump over an empty space!";
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


  /**
   * Private getter for a space on the board.
   *
   * @param position Position of the space to return.
   * @return Space on the board based on given position.
   */
  private Space getSpace(Position position) {
    return rows.get(position.getRow()).getSpace(position.getCell());
  }

  /**
   * Performs a move on the board. Only pass an already validated move!
   *
   * @param move Valid move to perform on the board.
   */
  protected void performMove(Move move) {
    Space startSpace = getSpace(move.getStart());
    Space endSpace = getSpace(move.getEnd());
    Piece movePiece = startSpace.getPiece(); // This is the piece we want to move

    // performMove should only be called on validated moves added to Game.pendingMoves
    // We will do very simple error checking just to be sure anyway
    if (move.isInvalid()) return;

    // Perform Slide
    if (move.isSlide()) {
      endSpace.setPiece(movePiece); // Set endSpace piece to movePiece
      startSpace.removePiece(); // Remove movePiece from startSpace

      hasSlid = true; // A slide has occurred
      return; // Finished
    }

    // Get midSpace needed for jump
    Space midSpace = getSpace(move.getMiddle());

    // Perform Jump
    if (move.isJump()) {
      endSpace.setPiece(movePiece); // Set endSpace piece to movePiece
      startSpace.removePiece(); // Remove movePiece from startSpace

      // Capture (remove) the middle piece
      midSpace.removePiece();

      hasJumped = true; // A jump has occurred
    }
  }

  /**
   * Checks if a given move is valid on the board.
   *
   * @param move Move that needs to be validated.
   * @return Message of type INFO if move is valid, or type ERROR if invalid.
   */
  public Message validateMove(Move move) {
    // Positions
    Position startPos = move.getStart();
    Position endPos = move.getEnd();
    int rowDelta = startPos.getRow() - endPos.getRow();

    // Spaces
    Space startSpace = this.getSpace(startPos);
    Space endSpace = this.getSpace(endPos);

    // Piece
    Piece movePiece = startSpace.getPiece(); // This is the piece we want to move
    Color moveColor = movePiece.getColor();
    Type moveType = movePiece.getType();

    /*
     * Basic Validation Checks
     */

    // CSS/JavaScript shouldn't let pieces be moved to invalid spaces, but keeping this as a simple
    // check to filter out basic exceptions.
    if (!endSpace.isValid()) {
      return Message.error(INVALID_END_SPACE);
    }

    // Move should not be invalid
    if (move.isInvalid()) {
      return Message.error(INVALID_MOVE);
    }

    // Ensure SINGLE piece is moving in the right direction
    if ((moveType == Type.SINGLE)
        && ((moveColor == Color.RED && rowDelta < 0) // RED row delta should be positive
            || (moveColor == Color.WHITE && rowDelta > 0))) // WHITE row delta should be negative
    {
      return Message.error(
          String.format(INVALID_DIRECTION, moveColor.name(), moveColor.direction()));
    }

    /*
     * Slide Validation
     */

    if (move.isSlide()) {
      // If a slide or jump has already occurred return appropriate error message
      if (hasSlid) {
        return Message.error(INVALID_SLIDE_AFTER_SLIDE);
      } else if (hasJumped) {
        return Message.error(INVALID_SLIDE_AFTER_JUMP);
      }

      // If a jump is possible, tell the player
      if (canJump(startPos)) {
        return Message.error(INVALID_SLIDE_WHEN_JUMP);
      }

      // Valid Slide, above checks passed
      return Message.info(VALID_SLIDE);
    }

    /*
     * Jump Validation
     */

    if (move.isJump()) {
      // If a slide has already occurred return appropriate error message
      if (hasSlid) {
        return Message.error(INVALID_JUMP_AFTER_SLIDE);
      }

      // Validate the Jump, and return the result
      return validateJump(move);
    }

    // Invalid Move, above checks failed. This should never happen, if you see this look into it.
    return Message.error(INVALID_MOVE + " (Edge Case)");
  }

  /**
   * Checks if a given move is a valid slide on the board.
   *
   * @param move Move that needs to be validated as a slide.
   * @return Message of type INFO if move is a valid slide, or type ERROR if invalid.
   */
  private Message validateSlide(Move move) {
    // Move is not a slide
    if (!move.isSlide()) {
      return Message.error(INVALID_SLIDE);
    }

    // Positions
    Position startPos = move.getStart();
    Position endPos = move.getEnd();
    int rowDelta = startPos.getRow() - endPos.getRow();

    // Spaces
    Space startSpace = this.getSpace(startPos);
    Space endSpace = this.getSpace(endPos);

    // Piece
    Piece movePiece = startSpace.getPiece(); // This is the piece we want to move
    Color moveColor = movePiece.getColor();
    Type moveType = movePiece.getType();

    // End space is not valid
    if (!endSpace.isValid()) {
      return Message.error(INVALID_END_SPACE);
    }

    Message directionResult = validateDirection(move);
    if (!directionResult.isSuccessful()) {
      return directionResult;
    }

    if (canJump(startPos)) {
      return Message.error(INVALID_SLIDE_WHEN_JUMP);
    }

    // Invalid Slide, above checks failed
    return Message.error(INVALID_SLIDE);
  }

  /**
   * Checks if a given move is going the right direction. RED goes UP, WHITE goes DOWN.
   *
   * @param move Move that needs its direction to be validated.
   * @return Message of type INFO if move direction is a valid, or type ERROR if invalid.
   */
  private Message validateDirection(Move move) {
    // Position
    Position startPos = move.getStart();
    Position endPos = move.getEnd();
    int rowDelta = startPos.getRow() - endPos.getRow();

    // Piece
    Piece movePiece = this.getSpace(startPos).getPiece();
    Color moveColor = movePiece.getColor();
    Type moveType = movePiece.getType();

    // Only Single pieces need direction to be validated
    if (moveType != Type.SINGLE) {
      return Message.info("It's a king");
    }

    // Check if a color is going the wrong direction
    // RED's row delta should be positive, WHITE's should be negative
    if ((moveColor == Color.RED && rowDelta < 0) || (moveColor == Color.WHITE && rowDelta > 0)) {

      // Color is going wrong direction, return descriptive error message
      return Message.error(String.format(INVALID_DIRECTION, moveColor, moveColor.direction()));
    }

    // Valid Direction, above checks passed
    return Message.info("Valid Direction");
  }

  /**
   * Checks if a given move is a valid jump on the board.
   *
   * @param move Move that needs to be validated as a jump.
   * @return Message of type INFO if move is a valid jump, or type ERROR if invalid.
   */
  private Message validateJump(Move move) {
    // Move is not a jump
    if (!move.isJump()) {
      return Message.error(INVALID_JUMP);
    }

    Space endSpace = this.getSpace(move.getEnd()); // Space where the jump move ends
    Piece startPiece = this.getSpace(move.getStart()).getPiece(); // Piece at start position
    Piece midPiece = this.getSpace(move.getMiddle()).getPiece(); // Piece at middle position

    // End space should not be invalid
    if (!endSpace.isValid()) {
      return Message.error(INVALID_END_SPACE);
    }

    // Middle piece should not be empty
    if (midPiece == null) {
      return Message.error(INVALID_JUMP_EMPTY_MIDDLE);
    }

    // Middle piece should not be the same color as the jumping piece
    if (midPiece.getColor() == startPiece.getColor()) {
      return Message.error(INVALID_JUMP_SAME_PIECE);
    }

    // Valid Jump, above checks passed
    return Message.info(VALID_JUMP);
  }

  /**
   * Checks if there are available valid jumps from the given starting position.
   *
   * @param startPosition Position to test if it can perform any valid jumps.
   * @return True if there are available valid jumps from the starting position, or false if not.
   */
  private boolean canJump(Position startPosition) {
    Space space = getSpace(startPosition); // Starting Space
    Piece piece = space.getPiece(); // Starting (Moving) Piece

    // Generate correct jump increment, dependant on color direction value
    int jumpIncrement = 2 * piece.getColor().value(); // RED (Negative), WHITE (Positive)

    // Create possible jump positions
    int[][] possiblePositions = {
      // SINGLES
      {startPosition.getRow() + jumpIncrement, startPosition.getCell() - jumpIncrement}, // +, -
      {startPosition.getRow() + jumpIncrement, startPosition.getCell() + jumpIncrement}, // +, +
      // KINGS
      {startPosition.getRow() - jumpIncrement, startPosition.getCell() - jumpIncrement}, // -, -
      {startPosition.getRow() - jumpIncrement, startPosition.getCell() + jumpIncrement}, // - , +
    };

    // Traverse first two positions if it is a SINGLE, or all if it is a KING
    int maxTraverse = piece.getType() == Type.SINGLE ? 2 : 4;

    // Generate an empty ArrayList to store possible jump moves
    ArrayList<Move> possibleJumps = new ArrayList<>(maxTraverse);

    // For maxTraverse possible positions generate possible jumps, if the position isInBounds
    for (int i = 0; i < maxTraverse; i++) {
      int row = possiblePositions[i][0];
      int col = possiblePositions[i][1];

      if (Position.isInBounds(row, col))
        possibleJumps.add(new Move(startPosition, new Position(row, col)));
    }

    // Check validity of possible jumps
    for (Move jump : possibleJumps) {
      // If a jump is valid, return true. The piece can jump.
      if (validateJump(jump).isSuccessful()) return true;
    }

    // None of the possible jumps were viable
    return false;
  }

  @Override
  public Iterator<Row> iterator() {
    return new Board(this).rows.iterator();
  }
}
