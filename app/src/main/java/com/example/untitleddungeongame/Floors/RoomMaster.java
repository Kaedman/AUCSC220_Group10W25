package com.example.untitleddungeongame.Floors;

import android.util.Log;

import com.example.untitleddungeongame.Enemy;

import java.util.ArrayList;

public class RoomMaster {
    private Room headRoom = null;
    private Room currentRoom = null;
    private int roomCount = 0;
    private int[][] floorMap;
    private int floorRows;
    private int floorCols;
    private int currentFloor = 1;

    // 0 is no room, 1 is origin, 2 is boss, 3 is encounter, 4 is rest
    // This array simply indicates which rooms can be randomly chosen from during room generation
    // (where the origin and boss rooms are reserved)
    private final int[] roomIntList = {3, 4};

    /**
     * Finds and returns all currentLoc adjacent values in the floorMap array that have not been
     * set to a room
     *
     * @param currentLoc the [row, col] location the program is currently focused on
     * @return emptyPaths - the possible undefined rooms or paths that the can be moved to from
     * currentLoc in [row, col] pairs
     */
    public ArrayList<int[]> getEmptyPaths(int[] currentLoc) {
        ArrayList<int[]> emptyPaths = new ArrayList<>();

        for (int[] i : new int[][]{{0, -1}, {0, 1}, {-1, 0}, {1, 0}}) {
            if (currentLoc[0] + i[0] < floorRows && currentLoc[1] + i[1] < floorCols &&
                    currentLoc[0] + i[0] >= 0 && currentLoc[1] + i[1] >= 0 &&
                    floorMap[currentLoc[0] + i[0]][currentLoc[1] + i[1]] == 0) {
                emptyPaths.add(i);
            }
        }

        return emptyPaths;
    }

    private void generateRooms(int maxRows, int maxCols, int roomThreshold) {
        generateRoomArray(maxRows, maxCols, roomThreshold);
        headRoom = createOrigin();

        //return headRoom;
    }

    /**
     * Contains the logic for backtracking when there are no empty adjacent rooms to the most recent
     * element of path
     *
     * @param path ArrayList containing the [row, col] indices in the 2D floorMap array that have
     *             been traversed in the current generation process
     * @return either the entire path, signifying an element with possible adjacent empty rooms has
     * been found or a call to itself using the path minus the most recently added index
     */
    public ArrayList<int[]> findEmptyPath(ArrayList<int[]> path) {
        if (floorMap[path.get(0)[0]][path.get(0)[1]] == 1) {
            // About to remove the origin
            throw new RuntimeException("roomThreshold set too high, number of rooms " +
                    "required impossible for floor size");
        }

        path.remove(0);

        if (getEmptyPaths(path.get(0)).isEmpty()) {
            return findEmptyPath(path);
        } else {
            return path;
        }
    }

    /**
     * Generates the floorMap global 2D array with rooms, up to a specified row and col max
     *
     * @param maxRows Maximum rows for the new floor
     * @param maxCols Maximum cols for the new floor
     */
    public void generateRoomArray(int maxRows, int maxCols, int roomThreshold) {
        floorMap = new int[maxRows][maxCols];
        floorRows = maxRows;
        floorCols = maxCols;
        ArrayList<int[]> path = new ArrayList<>();

        roomCount = 0;

        // Generate random origin between 0 and the maxRows/Cols
        int randRow = (int) (Math.random() * maxRows);
        int randCol = (int) (Math.random() * maxCols);
        int[] currentLoc = new int[]{randRow, randCol};

        floorMap[randRow][randCol] = 1;
        path.add(0, currentLoc);
        roomCount++;

        while (roomCount < roomThreshold) {
            ArrayList<int[]> emptyPaths = getEmptyPaths(currentLoc);

            if (emptyPaths.isEmpty()) {
                // Start backtracking to find an empty path
                currentLoc = findEmptyPath(path).get(0);
                emptyPaths = getEmptyPaths(currentLoc);
            }

            // Set to a room and move to next (random) location
            int[] randomPath = emptyPaths.get((int) (Math.random() * emptyPaths.size()));
            currentLoc = new int[]{currentLoc[0] + randomPath[0], currentLoc[1] + randomPath[1]};
            path.add(0, currentLoc);


            // Set the location in the array to a random room
            floorMap[currentLoc[0]][currentLoc[1]] =
                    roomIntList[(int) (Math.random() * roomIntList.length)];
            roomCount++;
        }

        // Set last to boss room
        floorMap[currentLoc[0]][currentLoc[1]] = 2;
    }

    /**
     * Sets the currentRoom to a new adjacent room
     *
     * @param destination the room checked for adjacency and moved to
     */
    public void moveToRoom(Room destination) {
        if (destination.isAdjacent(currentRoom)) {
            currentRoom = destination;
        } else {
            throw new java.lang.RuntimeException("Room destination is not adjacent to current room");
        }
    }

    /**
     * Finds the origin on the floor map and creates the room to start making other rooms
     *
     * @return The origin room to be used as the head room.
     */
    private Room createOrigin() {
        Room origin = null;
        for (int row = 0; row < floorMap.length; row++) {
            for (int col = 0; col < floorMap[0].length; col++) {
                if (floorMap[row][col] == 1) {
                    origin = createRoom(row, col);

                }
            }
        }
        return origin;
    }//createOrigin

    private void createLinkedFloor(Room currentRoom) {
        Room nextRoom;
        int row = currentRoom.getRoomId() / 100;
        int col = currentRoom.getRoomId() % 100;

        //Check Up
        if (row != 0) {
            if (floorMap[row - 1][col] != 0 && currentRoom.getUp() == null) {
                if (findRoom(headRoom, row - 1, col) == null) {
                    nextRoom = createRoom(row - 1, col);
                    nextRoom.setDownRoom(currentRoom);
                    currentRoom.setUpRoom(nextRoom);
                    createLinkedFloor(nextRoom);
                }
            }
        }
        //Check Left
        if (col != 0) {
            if (floorMap[row][col - 1] != 0 && currentRoom.getLeft() == null) {
                if (findRoom(headRoom, row, col - 1) == null) {
                    nextRoom = createRoom(row, col - 1);
                    nextRoom.setRightRoom(currentRoom);
                    currentRoom.setLeftRoom(nextRoom);
                    createLinkedFloor(nextRoom);
                }
            }
        }

        //Check Right
        if (col != floorMap[row].length) {
            if (floorMap[row][col + 1] != 0 && currentRoom.getLeft() == null) {
                if (findRoom(headRoom, row, col + 1) == null) {
                    nextRoom = createRoom(row, col + 1);
                    nextRoom.setLeftRoom(currentRoom);
                    currentRoom.setRightRoom(nextRoom);
                    createLinkedFloor(nextRoom);
                }
            }
        }

        //Check Down
        if (row != floorMap.length) {
            if (floorMap[row + 1][col] != 0 && currentRoom.getUp() == null) {
                if (findRoom(headRoom, row + 1, col) == null) {
                    nextRoom = createRoom(row + 1, col);
                    nextRoom.setUpRoom(currentRoom);
                    currentRoom.setDownRoom(nextRoom);
                    createLinkedFloor(nextRoom);
                }
            }
        }
    }//CreateLinkedFloor

    private Room createRoom(int row, int col) {
        Room newRoom;
        Enemy enemy;
        // 0 is no room, 1 is origin, 2 is boss, 3 is encounter, 4 is rest
        switch (floorMap[row][col]) {
            //Origin
            case 1:
                newRoom = new Room(((row * 100) + (col)));
                break;

            case 2:
                //REPLACE WITH CREATE NEW ENEMY FUNCTION
                enemy = new Enemy("Boss", 10, 10, 4);

                newRoom = new Boss(((row * 100) + (col)), enemy);
                break;

            case 3:
                //REPLACE WITH CREATE NEW ENEMY FUNCTION
                enemy = new Enemy("Generic", 5, 5, 1);

                newRoom = new Encounter(((row * 100) + (col)), enemy);
                break;

            case 4:
                newRoom = new Rest(((row * 100) + (col)));
                break;

            //No room to create.
            default:
                newRoom = null;
                break;
        }

        return newRoom;
    }//createRoom

    //Wrapper for findRoomRec
    private Room findRoom(Room origin, int targetRow, int targetCol) {
        return findRoomRec(origin, null, ((targetRow * 100) + targetCol), null);
    }

    private Room findRoomRec(Room currentRoom, Room previousRoom, int targetId, Room returnRoom) {
        int row = currentRoom.getRoomId() / 100;
        int col = currentRoom.getRoomId() % 100;
        if (targetId == currentRoom.getRoomId()) {
            returnRoom = currentRoom;
        } else {
            //Match Y first
            if (row < (targetId / 100) && currentRoom.getUp() != null && currentRoom.getUp() != previousRoom) {
                findRoomRec(currentRoom.getUp(), currentRoom, targetId, returnRoom);
            } else if (row > (targetId / 100) && currentRoom.getDown() != null && currentRoom.getDown() != previousRoom) {
                findRoomRec(currentRoom.getDown(), currentRoom, targetId, returnRoom);
            } else {
                //Match X next
                if (col < (targetId % 100) && currentRoom.getRight() != null && currentRoom.getRight() != previousRoom) {
                    findRoomRec(currentRoom.getRight(), currentRoom, targetId, returnRoom);
                } else if (col > (targetId % 100) && currentRoom.getLeft() != null && currentRoom.getLeft() != previousRoom) {
                    findRoomRec(currentRoom.getLeft(), currentRoom, targetId, returnRoom);
                } else {
                    //Can't move in optimal direction so go anywhere possible
                    if (currentRoom.getRight() != null && currentRoom.getRight() != previousRoom) {
                        findRoomRec(currentRoom.getRight(), currentRoom, targetId, returnRoom);
                    } else if (currentRoom.getLeft() != null && currentRoom.getLeft() != previousRoom) {
                        findRoomRec(currentRoom.getLeft(), currentRoom, targetId, returnRoom);
                    } else if (currentRoom.getUp() != null && currentRoom.getUp() != previousRoom) {
                        findRoomRec(currentRoom.getUp(), currentRoom, targetId, returnRoom);
                    } else if (currentRoom.getDown() != null && currentRoom.getDown() != previousRoom) {
                        findRoomRec(currentRoom.getDown(), currentRoom, targetId, returnRoom);
                    }
                }
            }
        }
        return returnRoom;
    }

    /**
     * Forces the floorMap to store a passed int[][] value
     *
     * @param floorMap the new map global floorMap should be set to
     */
    public void setFloorMap(int[][] floorMap) {
        this.floorMap = floorMap;
        this.floorRows = floorMap.length;
        this.floorCols = floorMap[0].length;
    }

    public int getRoomCount() {
        return roomCount;
    }
}