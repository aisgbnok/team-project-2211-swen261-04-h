package com.webcheckers.model;

import com.webcheckers.model.Piece.Color;

import java.util.Map;
import java.util.UUID;

public class Game {
  private final UUID gameID;
  public boolean active;
  private final Player currentPlayer;
  private final viewModes viewMode;
  private Map<String, Object> modeOptionsAsJSON;
  private final Player redPlayer;
  private final Player whitePlayer;
  private final colors activeColor;
  private final Board board;
  private Message message;

  public enum viewModes {
    PLAY,
    SPECTATOR,
    REPLAY
  }

  public enum colors {
    RED,
    WHITE
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
    this.activeColor = colors.RED;

    // For all the MVP stories this variable must be set to PLAY.
    this.viewMode = viewModes.PLAY;
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
    return board;
  }

  public UUID getGameID() {
    return gameID;
  }

  public colors getActiveColor() {
    return activeColor;
  }

  public Player getOpponent(Player currentPlayer) {
    if (currentPlayer.equals(redPlayer)) return whitePlayer;
    if (currentPlayer.equals(whitePlayer)) return redPlayer;
    return null;
  }
}
