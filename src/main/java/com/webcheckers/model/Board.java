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

  // Invalid Messages
  public static final String INVALID_MOVE = "Invalid Move";
  public static final String INVALID_SLIDE = "Invalid Slide";
  public static final String INVALID_JUMP = "Invalid Jump";
  public static final String INVALID_NOT_SLIDE = "Not A Slide";
  public static final String INVALID_NOT_JUMP = "Not A Jump";
  public static final String INVALID_DIRECTION = "%s single pieces can't go in that direction!";
  public static final String INVALID_END_SPACE = "End space is not valid!";
  public static final String INVALID_JUMP_AFTER_SLIDE = "You can't jump after a slide!";
  public static final String INVALID_JUMP_SAME_PIECE = "You can't jump over your own pieces!";
  public static final String INVALID_JUMP_EMPTY_MIDDLE = "You can't jump over an empty space!";
  public static final String INVALID_SLIDE_AFTER_JUMP = "You can't slide after a jump!";
  public static final String INVALID_SLIDE_AFTER_SLIDE = "You can't slide twice!";
  public static final String INVALID_SLIDE_WHEN_JUMP = "A jump is possible, you must jump!";

  // Valid Messages
  public static final String VALID_SLIDE = "Valid Slide";
  public static final String VALID_JUMP = "Valid Jump";
  public static final String VALID_DIRECTION = "Valid Direction";
  public static final String VALID_DIRECTION_KING = "Is A King";

  public static final int ROWS = 8; // How many rows in a board
  public static final int COLS = 8; // How many columns in a board

  private final ArrayList<Row> rows; // Contains all rows in a board in order.

  private boolean hasSlid; // A slide has occurred previously
  private boolean hasJumped; // A jump has occurred previously
  private boolean wasKinged; // A piece was kinged

  // Win Tracking
  private int redPieces = 12;
  private int whitePieces = 12;

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

      tryKing(move.getEnd()); // Try to king the piece, after the slide

      hasSlid = true; // A slide has occurred
      return; // Finished
    }

    // Get midSpace needed for jump
    Space midSpace = getSpace(move.getMiddle());

    // Perform Jump
    if (move.isJump()) {
      endSpace.setPiece(movePiece); // Set endSpace piece to movePiece
      startSpace.removePiece(); // Remove movePiece from startSpace

      tryKing(move.getEnd()); // Try to king the piece, after the jump

      // Update piece count for win conditions
      decreasePieceCount(midSpace);

      // Capture (remove) the middle piece
      midSpace.removePiece();

      hasJumped = true; // A jump has occurred
    }
  }

  /**
   * Decreases the piece count of the color of the piece on the given space. Used for tracking wins.
   *
   * @param space Space containing piece of color to decrease.
   */
  private void decreasePieceCount(Space space) {
    // Piece Information
    Piece piece = space.getPiece();
    Color pieceColor = piece.getColor();

    if (pieceColor == Color.RED) {
      redPieces -= 1;
    }

    if (pieceColor == Color.WHITE) {
      whitePieces -= 1;
    }
  }

  /**
   * Attempts to King the piece at the given position. Piece must be at an end row and be a single
   * to be kinged.
   *
   * @param position Position of the piece to King
   */
  private void tryKing(Position position) {
    int posRow = position.getRow();

    // If the position is at an end row (far TOP or far BOTTOM)
    if (posRow == 0 || posRow == (ROWS - 1)) {

      Space space = getSpace(position); // Space containing Piece to KING
      Piece piece = space.getPiece(); // Piece to KING

      // Make sure the piece is a SINGLE
      if (piece.getType() == Type.SINGLE) {
        space.removePiece(); // Remove the single piece from the space
        space.setPiece(new Piece(piece, Type.KING)); // Add same color piece that is a KING

        wasKinged = true; // A piece was kinged
      }
    }
  }

  /**
   * Checks if a given move is valid on the board.
   *
   * @param move Move that needs to be validated.
   * @return Message of type INFO if move is valid, or type ERROR if invalid.
   */
  public Message validateMove(Move move) {
    //  Slide Validation, check for previous moves
    if (move.isSlide()) {
      return this.validateSlide(move, true);
    }

    // Jump Validation, check for previous slides
    if (move.isJump()) {
      return this.validateJump(move, true);
    }

    // Was not a slide or a jump
    return Message.error(INVALID_MOVE);
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
      return Message.error(INVALID_NOT_SLIDE);
    }

    // End space is not valid
    if (!getSpace(move.getEnd()).isValid()) {
      return Message.error(INVALID_END_SPACE);
    }

    // Validate Direction
    Message directionResult = validateDirection(move);

    // If directionResult message is not successful
    if (!directionResult.isSuccessful()) {
      return directionResult;
    }

    // Check for possible jumps
    if (canJump(getSpace(move.getStart()).getPiece().getColor())) {
      return Message.error(INVALID_SLIDE_WHEN_JUMP);
    }

    // Valid Slide, above checks passed
    return Message.info(VALID_SLIDE);
  }

  /**
   * Checks if a given move is a valid slide on the board. Will also check to ensure a slide or jump
   * has not occurred on the board during the current turn if checkPrevious is true.
   *
   * @param move Move that needs to be validated as a slide.
   * @param checkPrevious Whether to check for previous slides or jumps during current turn.
   * @return Message of type INFO if move is a valid slide, or type ERROR if invalid.
   */
  private Message validateSlide(Move move, boolean checkPrevious) {
    // checkPrevious is false, return default validateSlide
    if (!checkPrevious) {
      return validateSlide(move);
    }

    // A slide has occurred during current turn
    if (hasSlid) {
      return Message.error(INVALID_SLIDE_AFTER_SLIDE);
    }

    // A jump has occurred during current turn
    if (hasJumped) {
      return Message.error(INVALID_SLIDE_AFTER_JUMP);
    }

    // Return default validateSlide, above checks passed
    return validateSlide(move);
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
      return Message.error(INVALID_NOT_JUMP);
    }

    // Pieces
    Piece startPiece = this.getSpace(move.getStart()).getPiece();
    Piece midPiece = this.getSpace(move.getMiddle()).getPiece();

    // End space should not be invalid
    if (!getSpace(move.getEnd()).isValid()) {
      return Message.error(INVALID_END_SPACE);
    }

    // Validate Direction
    Message directionResult = validateDirection(move);

    // If directionResult message is not successful
    if (!directionResult.isSuccessful()) {
      return directionResult;
    }

    // Middle piece should not be empty
    if (midPiece == null) {
      return Message.error(INVALID_JUMP_EMPTY_MIDDLE);
    }

    // Middle piece should not be the same color as the startPiece piece
    if (midPiece.getColor() == startPiece.getColor()) {
      return Message.error(INVALID_JUMP_SAME_PIECE);
    }

    // Valid Jump, above checks passed
    return Message.info(VALID_JUMP);
  }

  /**
   * Checks if a given move is a valid jump on the board. Will also check to ensure a slide has not
   * occurred on the board during the current turn if checkPrevious is true.
   *
   * @param move Move that needs to be validated as a jump.
   * @param checkPrevious Whether to check for previous slides during current turn.
   * @return Message of type INFO if move is a valid jump, or type ERROR if invalid.
   */
  private Message validateJump(Move move, boolean checkPrevious) {
    // checkPrevious is false, return default validateJump
    if (!checkPrevious) {
      return validateJump(move);
    }

    // A slide has occurred during current turn
    if (hasSlid) {
      return Message.error(INVALID_JUMP_AFTER_SLIDE);
    }

    // Return default validateJump, above checks passed
    return validateJump(move);
  }

  /**
   * Checks if a given move is going the right direction. RED goes UP, WHITE goes DOWN.
   *
   * @param move Move that needs its direction to be validated.
   * @return Message of type INFO if move direction is valid, or type ERROR if invalid.
   */
  private Message validateDirection(Move move) {
    // Calculate Row Delta
    int rowDelta = move.getStart().getRow() - move.getEnd().getRow();

    // Piece Information
    Piece movePiece = this.getSpace(move.getStart()).getPiece();
    Color moveColor = movePiece.getColor();
    Type moveType = movePiece.getType();

    // Only Single pieces need direction to be validated
    if (moveType != Type.SINGLE) {
      return Message.info(VALID_DIRECTION_KING);
    }

    // Color is going wrong direction, return descriptive error message
    if ((moveColor == Color.RED && rowDelta < 0) || (moveColor == Color.WHITE && rowDelta > 0)) {
      return Message.error(String.format(INVALID_DIRECTION, moveColor));
    }

    // Valid Direction, above checks passed
    return Message.info(VALID_DIRECTION);
  }

  /**
   * Checks if there are one or more valid jumps for pieces of the given color.
   *
   * @param color Color of the pieces to be tested for valid jumps.
   * @return True if there is a valid jump for a piece of the given color, or false if not.
   */
  private boolean canJump(Color color) {
    // Create new array for storing all positions for pieces of given color
    ArrayList<Position> piecePositions = getPiecePositions(color);

    // For each position check if they can jump
    for (Position piecePosition : piecePositions) {

      // Use deprecated canJump for now
      if (canJump(piecePosition)) {
        return true; // Jump was found, return true.
      }
    }

    // None of them can jump, return false
    return false;
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

  /**
   * Gets all positions that contain piece of given color on the board. Find positions based on
   * piece color.
   *
   * @param color Color of pieces to get positions for.
   * @return All positions that contain a piece of the given color.
   */
  private ArrayList<Position> getPiecePositions(Color color) {
    // Create new array for storing positions
    ArrayList<Position> positions = new ArrayList<>();

    // Find positions in each row
    for (Row row : rows) {
      positions.addAll(row.getPiecePositions(color));
    }

    // Return all matching positions
    return positions;
  }

  @Override
  public Iterator<Row> iterator() {
    return new Board(this).rows.iterator();
  }
}