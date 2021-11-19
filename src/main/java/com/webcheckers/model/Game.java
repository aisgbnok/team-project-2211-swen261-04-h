package com.webcheckers.model;

import com.webcheckers.model.Message.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * A game of checkers against a REd and a White player.
 *
 * @author <a href='mailto:ajs2576@rit.edu'>Anthony Swierkosz</a>
 */
public class Game {

  private final UUID gameID; // Game Identifier
  private final Board board; // Game Board
  private final ArrayList<Move> pendingMoves; // Pending game piece moves

  private final Color activeColor; // Active Player/Piece Color
  private final Player redPlayer; // Player with red pieces
  private final Player whitePlayer; // Player with white pieces

  private boolean isGameOver;
  private String gameOverMessage;

  /**
   * Constructs a new game. Generates a gameID and a new board. Sets the activeColor to RED, and
   * sets the redPlayer and whitePlayer based on the given players.
   *
   * @param redPlayer Player with red pieces, and the starting player.
   * @param whitePlayer Player with white pieces.
   */
  public Game(Player redPlayer, Player whitePlayer) {
    // Game
    this.gameID = UUID.randomUUID(); // Set gameID
    this.board = new Board(); // Create a new board
    this.pendingMoves = new ArrayList<>(); // Create an empty list to store pending moves

    // Players
    this.activeColor = Color.RED; // RED is always the starting color
    this.redPlayer = redPlayer; // Set Red Player
    this.whitePlayer = whitePlayer; // Set White Player

    // Game Status
    this.isGameOver = false; // Game is just starting
    this.gameOverMessage = null; // Game is not over
  }

  /*
   * Game Methods
   */

  /**
   * Getter for the gameID.
   *
   * @return The game's ID in UUIDv4.
   */
  public UUID getGameID() {
    return gameID;
  }

  /**
   * Getter for the game board. Generates a new duplicate game board and returns it.
   *
   * @return Game board
   */
  public Board getBoard() {
    return new Board(board);
  }

  /**
   * Getter for the game board. Generates a new duplicated and reflected game board and returns it.
   *
   * @param color Color of the pieces that start on the bottom of the board.
   * @return Reflected Game board
   */
  public Board getBoard(Color color) {
    return new Board(board, color.equals(Color.WHITE));
  }

  public Message validateMove(Move move) {
    // Create a new board, so we aren't modifying this board
    Board testBoard = new Board(board);

    // Apply each pending move to the test board. (These should have already been validated)
    for (Move pendingMove : pendingMoves) {
      // Apply each pendingMove to the test board.
      testBoard.performMove(pendingMove);
    }

    // TODO add other pending moves, and some other stuff

    // If the result of the validation is INFO (success) then add it to pendingMoves
    // TODO this is temporary for testing validate move
    Message validResult = testBoard.validateMove(move);
    if (validResult.getType() == Type.INFO) {
      pendingMoves.add(move);
    }

    return validResult;
  }

  /*
   * Player Methods
   */

  /**
   * Getter for the current active piece color in the game.
   *
   * @return The active color. RED if it is red's turn, or WHITE if it is white's turn.
   */
  public Color getActiveColor() {
    return activeColor;
  }

  /**
   * Getter for the playerName corresponding to the player matching the given color.
   *
   * @param color Color of the player whose name will be returned.
   * @return Player name of the player corresponding to given color. Passing RED will return
   *     redPlayer name, WHITE will return whitePlayer name.
   */
  public String getPlayerName(Color color) {
    return (color.equals(Color.RED)) ? redPlayer.getName() : whitePlayer.getName();
  }

  /*
   * Game Status Methods
   */

  /**
   * Getter for game over status.
   *
   * @return True if game is over, or false if game is in progress.
   */
  public boolean isGameOver() {
    return isGameOver;
  }

  /**
   * Getter for game over message. This describes why/how the game ended.
   *
   * @return Game over message if game is over, or null if game is in progress.
   */
  public String getGameOverMessage() {
    return gameOverMessage;
  }

  /**
   * Getter for the game modeOptions.
   *
   * @return HashMap containing isGameOver and gameOverMessage.
   */
  public Map<String, Object> getModeOptions() {
    final Map<String, Object> modeOptions = new HashMap<>(2);

    modeOptions.put("isGameOver", isGameOver);
    modeOptions.put("gameOverMessage", gameOverMessage);

    return modeOptions;
  }

  /*
   * Other Methods
   */

  /**
   * Checks if two games are the same game.
   *
   * @param o Object to check equality
   * @return True if the games are equal, false if different.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Game game = (Game) o;
    return this.gameID.equals(game.gameID)
        && this.redPlayer.equals(game.redPlayer)
        && this.whitePlayer.equals(game.whitePlayer);
  }

  // TODO: viewModes don't belong to a game, but a session. Where should we put these?
  public enum viewModes {
    PLAY,
    SPECTATOR,
    REPLAY
  }

  /*
   * The methods below here should maybe be phased out? Debating.
   *
   * They are kinda weird. Not sure what to think of them.
   * I mean I wrote them, but I'm not sure if they are smelly. Investigating.
   * Try not to use them.
   * - ajs2576
   */

  /**
   * Determines if the given player is in this game.
   *
   * @param player Player to check if in this game.
   * @return Returns true if given player is a player in this game.
   */
  public boolean hasPlayer(Player player) {
    return redPlayer.equals(player) || whitePlayer.equals(player);
  }

  /**
   * Determines the given player's color and returns it.
   *
   * @param player Player to get color of
   * @return Given player's color, or null if player isn't in this game.
   */
  public Color getPlayerColor(Player player) {
    if (player.equals(redPlayer)) return Color.RED;
    if (player.equals(whitePlayer)) return Color.WHITE;
    return null;
  }

  /**
   * Determines the opposing player and returns them.
   *
   * @param currentPlayer Player to get opponent of
   * @return Opposite player to given player, or null if given player is not in this game.
   */
  public Player getOpponent(Player currentPlayer) {
    if (currentPlayer.equals(redPlayer)) return whitePlayer;
    if (currentPlayer.equals(whitePlayer)) return redPlayer;
    return null;
  }
}
