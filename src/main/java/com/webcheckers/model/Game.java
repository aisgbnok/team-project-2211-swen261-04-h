package com.webcheckers.model;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Game {

  // Fields
  private final UUID gameID;
  private final Player currentPlayer;
  private final viewModes viewMode;
  private Map<String, Object> modeOptionsAsJSON;
  private final Player redPlayer;
  private final Player whitePlayer;
  private final Color activeColor;
  private final Board board;
  private Message message;

  // Enumerations
  public enum viewModes {
    PLAY,
    SPECTATOR,
    REPLAY
  }

  public Game(Player redPlayer, Player whitePlayer) {
    // Set gameID
    this.gameID = UUID.randomUUID();

    // Create a new board
    this.board = new Board();

    // Set Players
    this.redPlayer = redPlayer;
    this.whitePlayer = whitePlayer;

    // RED is always the starting Player and color
    this.currentPlayer = redPlayer;
    this.activeColor = Color.RED;

    // For all the MVP stories this variable must be set to PLAY.
    this.viewMode = viewModes.PLAY;

    // Sets up the modeOptionsAsJSON
    this.modeOptionsAsJSON = new HashMap<>(2);
    modeOptionsAsJSON.put("isGameOver", false);
  }

  public boolean hasPlayer(Player player) {
    return redPlayer.equals(player) || whitePlayer.equals(player);
  }

  public Color getPlayerColor(Player player) {
    if (player.equals(redPlayer)) return Color.RED;
    if (player.equals(whitePlayer)) return Color.WHITE;
    return null;
  }

  public Board getBoard() {
    return new Board(board);
  }

  public Board getBoard(Color color) {
    return new Board(board, color.equals(Color.WHITE));
  }

  public UUID getGameID() {
    return gameID;
  }

  public Color getActiveColor() {
    return activeColor;
  }

  public Player getOpponent(Player currentPlayer) {
    if (currentPlayer.equals(redPlayer)) return whitePlayer;
    if (currentPlayer.equals(whitePlayer)) return redPlayer;
    return null;
  }

  /**
   * Checks if two games are the same game
   *
   * @param o Object to check equality
   * @return True if the games are equal, false if different.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Game game = (Game) o;
    return gameID.equals(game.gameID)
        && redPlayer.equals(game.redPlayer)
        && whitePlayer.equals(game.whitePlayer);
  }
}
