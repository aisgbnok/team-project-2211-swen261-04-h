package com.webcheckers.application;

import com.webcheckers.model.Player;

import java.util.ArrayList;

public class PlayerLobby {
    private static ArrayList<Player> players;

    public static ArrayList<Player> getPlayers() {
        return players;
    }

    public static void setPlayers(ArrayList<Player> players) {
        PlayerLobby.players = players;
    }

    public static boolean addPlayer(Player player){
        if (players.contains(player)){
            return false;
        } else {
            players.add(player);
            return true;
        }
    }

    public static Player getPlayer(String param) {
        for (Player player :
                players) {
            if (player.getName().equals(param)) {
                return player;
            }
        }
        return null;
    }

}
