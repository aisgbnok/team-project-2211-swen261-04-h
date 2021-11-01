package com.webcheckers.model;

import com.webcheckers.model.Piece.Color;

import java.util.ArrayList;
import java.util.Map;

public class Game {
    private static int gameIDCounter = 0;
    private final int gameID;
    public boolean active;
    private final Player currentUser;
    private final viewModes viewMode;
    private Map<String, Object> modeOptionsAsJSON;
    private final Player redPlayer;
    private final Player whitePlayer;
    private final colors activeColor;
    private final Board board;
    private Message message;
    public Game(Player redPlayer, Player whitePlayer, Board board) {
        // Set gameID
        this.gameID = gameIDCounter++;

        // Set Board
        this.board = board;

        // Set Players
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;

        // RED is always the starting Player and color
        this.currentUser = redPlayer;
        this.activeColor = colors.RED;

        // For all the MVP stories this variable must be set to PLAY.
        this.viewMode = viewModes.PLAY;
    }

    public ArrayList<Player> getPlayers() {
        ArrayList<Player> players = new ArrayList<>();
        players.add(redPlayer);
        players.add(whitePlayer);
        return players;
    }

    public Player getOppositePlayer(Player player) {
        if (player.equals(redPlayer)) {
            return whitePlayer;
        } else if (player.equals(whitePlayer)) {
            return redPlayer;
        }
        return null;
    }

    public Color getPlayerColor(Player player) {
        if (player.equals(redPlayer)) {
            return Color.RED;
        } else if (player.equals(whitePlayer)) {
            return Color.WHITE;
        }
        return null;
    }

    public Board getBoard() {
        return board;
    }

    public int getGameID() {
        return gameID;
    }

    public enum viewModes {
        PLAY,
        SPECTATOR,
        REPLAY
    }

    public enum colors {
        RED,
        WHITE
    }
}
