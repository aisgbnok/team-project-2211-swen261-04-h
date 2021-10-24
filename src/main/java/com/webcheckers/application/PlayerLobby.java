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

    public static void initPlayers(){
        players = new ArrayList<>();
    }
    public static boolean addPlayer(Player player){
        if (players.contains(player) || player.getName().contains("\"")|| player.getName().contains("'")){
            return false;
        } else {
            players.add(player);
            return true;
        }
    }
    public static void removePlayer(Player player){
        players.remove(player);
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

    public static int size(){
        return players.size();
    }

}
