package com.example.untitleddungeongame;

public class Room {
    Room left = null;
    Room right = null;
    Room up = null;
    Room down = null;

    public Room(Room left, Room right, Room up, Room down) {
        this.left = left;
        this.right = left;
        this.up = left;
        this.down = left;
    }

    public Room getLeft() {
        return left;
    }

    public Room getRight() {
        return up;
    }

    public Room getUp() {
        return up;
    }

    public Room getDown() {
        return down;
    }

    public Room[] getAllAdjacent() {
        return new Room[] {this.getLeft(), this.getRight(), this.getUp(), this.getDown()};
    }
}
