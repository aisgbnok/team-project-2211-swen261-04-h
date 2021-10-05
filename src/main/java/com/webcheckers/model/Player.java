package com.webcheckers.model;

import java.util.Objects;

public class Player {
    public String getName() {
        return name;
    }

    public Player(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player player = (Player) o;
        return Objects.equals(getName(), player.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
}
