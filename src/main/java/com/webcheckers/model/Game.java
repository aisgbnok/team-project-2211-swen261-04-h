package com.webcheckers.model;

import java.util.Map;

public class Game {
  public enum viewModes {
    PLAY,
    SPECTATOR,
    REPLAY
  }

  public enum colors {
    RED,
    WHITE
  }

  private Player currentUser;
  private viewModes viewMode;
  private Map<String, Object> modeOptionsAsJSON;
  private Player redPlayer;
  private Player whitePlayer;
  private colors activeColor;
  private BoardView board;
  private Message message;

  public Game(Player redPlayer, Player whitePlayer) {
    // Create Board
    this.board = new BoardView();

    // Set Players
    this.redPlayer = redPlayer;
    this.whitePlayer = whitePlayer;

    // RED is always the starting Player and color
    this.currentUser = redPlayer;
    this.activeColor = colors.RED;

    // For all the MVP stories this variable must be set to PLAY.
    this.viewMode = viewModes.PLAY;
  }
}
