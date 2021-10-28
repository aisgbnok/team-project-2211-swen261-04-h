package com.webcheckers.model;

public class Move {
    private Space start;
    private Space end;

    public Move(Space space, Space space2) {
        start = space;
        end = space2;
    }

    public Space getStart() {
        return start;
    }

    public void setStart(Space start) {
        this.start = start;
    }

    public Space getEnd() {
        return end;
    }

    public void setEnd(Space end) {
        this.end = end;
    }
}
