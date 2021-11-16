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

  // TODO determine that piece is moving forwards
  public Message validateMove(Move move) {
    // Get start and end position
    Position startPos = move.getStart();
    Position endPos = move.getEnd();

    // Get start and end space
    Space startSpace = this.getSpace(startPos);
    Space endSpace = this.getSpace(endPos);

    // Get start and end piece
    Piece startPiece = startSpace.getPiece(); // This should be the piece we want to move
    Piece endPiece = endSpace.getPiece(); // This needs to be null (empty space)

    // 1. Ensure that the endSpace is valid
    if (!endSpace.isValid()) {
      return Message.error("End Space is not valid!");
    }

    // 2. Determine if it's a move or a jump
    if (move.isSlide()) {
      // 3. Determine if a jump is possible, and force the player to jump
      // If a player has more than one piece that can jump, they must choose one of them to jump.
      if (canJump(startPos)) {
        return Message.error("Must jump!");
      }

      // 4. Validate the move
      return Message.info("Valid slide!");
    } else if (move.isJump()) {
      // 2. Validate the jump
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

    // Get space
    Space space = getSpace(startPos);
    Piece piece = space.getPiece();

    // 1. Create a list of possible Jumps
    int jumpIncrement = 2;
    ArrayList<Move> possibleJumps = new ArrayList<>(2);

    // TODO: Rewrite from here down to be just better, because this is bad. Use ENUM directions.
    // 2. SINGLE pieces only have two possible jumps
    int possibleRow = startPos.getRow() - jumpIncrement;
    int possibleCol = startPos.getCell() + jumpIncrement;
    if (Position.isInBounds(possibleRow, possibleCol)) // -, +
    possibleJumps.add(new Move(startPos, new Position(possibleRow, possibleCol)));

    possibleCol = startPos.getCell() - jumpIncrement;
    if (Position.isInBounds(possibleRow, possibleCol)) // -, -
    possibleJumps.add(new Move(startPos, new Position(possibleRow, possibleCol)));

    // 2. KING pieces have an additional two possible jumps (4 total)
    if (piece.getType() == Type.KING) {

      possibleRow = startPos.getRow() + jumpIncrement;

      if (Position.isInBounds(possibleRow, possibleCol)) // +, -
      possibleJumps.add(new Move(startPos, new Position(possibleRow, possibleCol)));

      possibleCol = startPos.getCell() + jumpIncrement;
      if (Position.isInBounds(possibleRow, possibleCol)) // +, +
      possibleJumps.add(new Move(startPos, new Position(possibleRow, possibleCol)));
    }

    // 3. Check to see if there is a piece in between
    for (Move jump : possibleJumps) {
      if (jump.middle() != null) {
        if (getSpace(jump.middle()).getPiece() != null) {
          // 4. Determine if it is the opposite color
          if (piece.getColor().opposite().equals(getSpace(jump.middle()).getPiece().getColor())) {
            // 5. Make sure it is not a KING // TODO can a KING jump a KING?
            if (getSpace(jump.middle()).getPiece().getType() != Type.KING) {
              return true;
            }
          }
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
