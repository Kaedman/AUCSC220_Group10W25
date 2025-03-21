package com.example.untitleddungeongame;

public class Room {
    Room left;
    Room right;
    Room up;
    Room down;

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

    /**
     * Gets each of a rooms adjacent rooms (left, right, up, down) and returns them in the form of
     * an array of Rooms
     * @return - the array of adjacent rooms
     */
    public Room[] getAllAdjacent() {
        return new Room[] {this.getLeft(), this.getRight(), this.getUp(), this.getDown()};
    }

    /**
     * Checks if a room is adjacent to another
     * @param checkedRoom - the room checked if it is adjacent to this room
     * @return - true/false, if the checkedRoom is adjacent
     */
    public boolean isAdjacent(Room checkedRoom) {
        for (Room room : this.getAllAdjacent()) {
            if (checkedRoom == room) {
                return true;
            }
        }

        return false;
    }
}
