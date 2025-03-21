package com.example.untitleddungeongame.Floors;

import java.util.ArrayList;

public class RoomMaster {
    private Room headRoom = null;
    private Room currentRoom = null;
    private int roomCount;
    private int[][] floorMap;
    private int currentFloor;

    // 0 is no room, 1 is origin, 2 is boss, 3 is encounter, 4 is rest
    // This array simply indicates which rooms can be randomly chosen from during room generation
    // (where the origin and boss rooms are reserved)
    private final int[] roomIntList = {3, 4};

    /**
     * Finds and returns all currentLoc adjacent values in the floorMap array that have not been
     * set to a room
     * @param currentLoc - the [row, col] location the program is currently focused on
     * @return emptyPaths - the possible undefined rooms or paths that the can be moved to from
     * currentLoc in [row, col] pairs
     */
    private ArrayList<int[]> getEmptyPaths(int[] currentLoc) {
        ArrayList<int[]> emptyPaths = new ArrayList<>();

        for (int[] i : new int[][] {{0,-1}, {0, 1}, {1, 0}, {-1, 0}}) {
            if (floorMap[currentLoc[0] + i[0]][currentLoc[1] + i[1]] == 0) {
                emptyPaths.add(i);
            }
        }

        return emptyPaths;
    }

    private Room generateRooms(int maxRows, int maxCols, int roomThreshold) {
        generateRoomArray(maxRows, maxCols, roomThreshold);

        //generateLinkedFloor();

        return headRoom;
    }

    /**
     * Contains the logic for backtracking when there are no empty adjacent rooms to the most recent
     * element of path
     * @param path - ArrayList containing the [row, col] indices in the 2D floorMap array that have
     *             been traversed in the current generation process
     * @return either the entire path, signifying an element with possible adjacent empty rooms has
     * been found or a call to itself using the path minus the most recently added index
     */
    private ArrayList<int[]> findEmptyPath(ArrayList<int[]> path) {
        if (floorMap[path.get(0)[0]][path.get(0)[1]] == 1) {
            // About to remove the origin
            throw new RuntimeException("roomThreshold set too high, number of rooms" +
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
     * @param maxRows - Maximum rows for the new floor
     * @param maxCols - Maximum cols for the new floor
     */
    private void generateRoomArray(int maxRows, int maxCols, int roomThreshold) {
        floorMap = new int[maxRows][maxCols];
        ArrayList<int[]> path = new ArrayList<>();

        roomCount = 0;

        // Generate random origin between 0 and the maxRows/Cols
        int randRow = (int)(Math.random() * maxRows);
        int randCol = (int)(Math.random() * maxCols);
        int[] currentLoc = new int[] {randRow, randCol};

        floorMap[randRow][randCol] = 1;
        path.add(0, currentLoc);
        roomCount++;

        while (roomCount < roomThreshold) {
            ArrayList<int[]> emptyPaths = getEmptyPaths(currentLoc);

            if (emptyPaths.isEmpty()) {
                // Start backtracking to find an empty path
                path = findEmptyPath(path);
            } else {
                // Set to a room and move to next (random) location
                currentLoc = emptyPaths.get((int)(Math.random() * emptyPaths.size()));
                path.add(0, currentLoc);
                roomCount++;

                // Set the location in the array to a random room
                floorMap[currentLoc[0]][currentLoc[1]] =
                        roomIntList[(int)(Math.random() * roomIntList.length)];
            }
        }

        // Set last to boss room
        floorMap[currentLoc[0]][currentLoc[1]] = 2;
    }

    /**
     * Sets the currentRoom to a new adjacent room
     * @param destination - the room checked for adjacency and moved to
     */
    public void moveToRoom(Room destination) {
        if (destination.isAdjacent(currentRoom)) {
            currentRoom = destination;
        } else {
            throw new java.lang.RuntimeException("Room destination is not adjacent to current room");
        }
    }
}
