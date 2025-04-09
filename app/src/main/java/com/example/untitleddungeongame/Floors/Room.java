package com.example.untitleddungeongame.Floors;

import androidx.annotation.NonNull;

public class Room {
    private Room left;
    private Room right;
    private Room up;
    private Room down;
    //Room ids are a 4 digit numbers, the first 2 digits are the row number, the last 2 digits are
    //the column numbers.
    private int roomId;

    public Room(int roomId) {
        this.left = null;
        this.right = null;
        this.up = null;
        this.down = null;
        this.roomId = roomId;
    }

    //Getters
    public Room getLeft() {
        return left;
    }

    public Room getRight() {
        return right;
    }

    public Room getUp() {
        return up;
    }

    public Room getDown() {
        return down;
    }
    public int getRoomId(){
        return roomId;
    }


    //Setters
    public void setLeftRoom(Room adjacentRoom){
        this.left = adjacentRoom;
    }
    public void setRightRoom(Room adjacentRoom){
        this.right = adjacentRoom;
    }
    public void setDownRoom(Room adjacentRoom){
        this.down = adjacentRoom;
    }
    public void setUpRoom(Room adjacentRoom){
        this.up = adjacentRoom;
    }
    public void setRoomId (int id){
        this.roomId = id;
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

    @NonNull
    @Override
    public String toString() {
        return String.valueOf(roomId);
    }
}
