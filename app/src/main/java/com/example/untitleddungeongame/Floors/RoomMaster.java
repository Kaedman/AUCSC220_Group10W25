package com.example.untitleddungeongame.Floors;

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
    private final int DEFAULT_ROOM = 3;
    private final int REST_COUNT = 3;

    /**
     * Finds and returns all currentLoc adjacent values in the floorMap array that have not been
     * set to a room
     * @param currentLoc the [row, col] location the program is currently focused on
     * @return emptyPaths - the possible undefined rooms or paths that the can be moved to from
     * currentLoc in [row, col] pairs
     */
    public ArrayList<int[]> getEmptyPaths(int[] currentLoc) {
        ArrayList<int[]> emptyPaths = new ArrayList<>();

        for (int[] i : new int[][] {{0,-1}, {0, 1}, {-1, 0}, {1, 0}}) {
            if (currentLoc[0] + i[0] < floorRows && currentLoc[1] + i[1] < floorCols &&
                    currentLoc[0] + i[0] >= 0 && currentLoc[1] + i[1] >= 0 &&
                        floorMap[currentLoc[0] + i[0]][currentLoc[1] + i[1]] == 0) {
                emptyPaths.add(i);
            }
        }

        return emptyPaths;
    }

    public Room generateRooms(int maxRows, int maxCols, int roomThreshold) {
        generateRoomArray(maxRows, maxCols, roomThreshold);
        headRoom = createOrigin();
        currentRoom = headRoom;
        createLinkedFloor(headRoom);
        placeRoom(4, REST_COUNT);
        generateLinkedFloor();

        return headRoom;
    }

    /**
     * Contains the logic for backtracking when there are no empty adjacent rooms to the most recent
     * element of path
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
                currentLoc = findEmptyPath(path).get(0);
                emptyPaths = getEmptyPaths(currentLoc);
            }

            // Set to a room and move to next (random) location
            int[] randomPath = emptyPaths.get((int)(Math.random() * emptyPaths.size()));
            currentLoc = new int[] {currentLoc[0] + randomPath[0], currentLoc[1] + randomPath[1]};
            path.add(0, currentLoc);


            // Set the location in the array to a random room
            floorMap[currentLoc[0]][currentLoc[1]] = DEFAULT_ROOM;
            roomCount++;
        }

        // Set last to boss room
        floorMap[currentLoc[0]][currentLoc[1]] = 2;
    }

    /**
     * Places a specified room that is not the DEFAULT_ROOM count times randomly in the floorMap
     * @param room the room value to place
     * @param count the number of the room to place
     */
    private void placeRoom(int room, int count) {
        int row;
        int col;

        do {
            row = (int)(Math.random() * floorRows);
            col = (int)(Math.random() * floorRows);
        } while (floorMap[row][col] != DEFAULT_ROOM);

        floorMap[row][col] = room;

        if (count > 0) {
            placeRoom(room, count - 1);
        }
    }

    /**
     * Sets the currentRoom to a new adjacent room
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
     * Creates the Actual rooms and links them together according to the Floor map.
     */
    public void generateLinkedFloor(){
        Room headRoom;
        for (int row = 0; row < floorMap.length; row++){
            for (int col = 0; col < floorMap[0].length; col++){
                if (floorMap[row][col] == 0){
                    continue;
                }

                //Create room and give id
                switch (floorMap[row][col]){
                    //Origin and sets head room
                    case 1:
                        headRoom = new Room(((row*1000)+(col*10)));
                        break;
                    //Boss
                    case 2:
                        Enemy boss = new Enemy("Boss", 10, 10, 5);
                        Boss bossRoom = new Boss(((row*1000)+(col*10)), boss);
                        break;
                    //Encounter
                    case 3:
                        Enemy enemy = new Enemy("enemy", 5, 5, 1);
                        Encounter encounterRoom = new Encounter(((row*1000)+(col*10)), enemy);
                        break;
                    //Rest
                    case 4:
                        Rest rest = new Rest(((row*1000)+(col*10)));
                        break;
                }

                //create links

                //Check Up
                if (row > 0){
                    if (floorMap[row-1][col] != 0){

                    }
                }
            }
        }
    }

    /**
     * Forces the floorMap to store a passed int[][] value
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
