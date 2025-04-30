package com.example.untitleddungeongame.floors;

import android.util.Log;

import com.example.untitleddungeongame.entity.Big_Goblin;
import com.example.untitleddungeongame.entity.Enemy;
import com.example.untitleddungeongame.entity.Goblin;
import com.example.untitleddungeongame.entity.Player;
import com.example.untitleddungeongame.entity.Slime;
import com.example.untitleddungeongame.handlers.Game;
import com.example.untitleddungeongame.ui.ConfirmCancelMenu;
import com.example.untitleddungeongame.hotbar.items.Item;
import com.example.untitleddungeongame.ui.shopbar.ShopBar;
import com.example.untitleddungeongame.ui.swapbar.SwapBar;

import java.util.ArrayList;

public class RoomMaster {
    private Room headRoom = null;
    private Room currentRoom = null;
    private int roomCount = 0;
    private int[][] floorMap;
    private int floorRows;
    private int floorCols;
    private int currentFloor = 1;
    private Player player;
    private Game game;

    // 0 is no room, 1 is origin, 2 is boss, 3 is encounter, 4 is rest, 5 is shop
    // This array simply indicates which rooms can be randomly chosen from during room generation
    // (where the origin and boss rooms are reserved)
    private final int[] roomIntList = {3, 4, 5};

    private final int FLOOR1ENEMIES = 2;
    private ShopBar<Item> shopBar;
    private SwapBar<Item> swapBar;

    public RoomMaster(Player player) {
        this.player = player;
    }

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


    public void generateRooms(int maxRows, int maxCols, int roomThreshold) {
        generateRoomArray(maxRows, maxCols, roomThreshold);
        headRoom = createFloor();
        currentRoom = headRoom;
        currentRoom.setUpAllLooks();
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
            Log.d("floorMap", toString());
            Log.d("currentId", getCurrentRoom().toString());

            game.onMove();

            if (currentRoom instanceof Rest) {
                if (!((Rest) currentRoom).usedRest()) {
                    hideArrows();
                    game.showConfirmCancel();
                    currentRoom.setRoomCleared(false);
                }
            } else if (!currentRoom.getRoomCleared() && currentRoom.getEnemy() != null) {
                hideArrows();
            } else if (currentRoom instanceof Shop) {
                game.setShopUI((Shop) currentRoom);
            }
        } else {
            throw new java.lang.RuntimeException("Room destination is not adjacent to current room");
        }
    }


    private Room createRoom(int row, int col) {
        Room newRoom;
        Enemy enemy;
        // 0 is no room, 1 is origin, 2 is boss, 3 is encounter, 4 is rest
        switch (floorMap[row][col]) {
            //Origin
            case 1:
                newRoom = new Room(((row * 100) + (col)), null);
                break;

            case 2: //
                enemy = new Big_Goblin("Ooga Booga", 20, 5, 5, 5);

                newRoom = new Boss(((row * 100) + (col)), enemy);
                break;

            case 3:
                enemy = makeRandomEnemy();

                newRoom = new Encounter(((row * 100) + (col)), enemy, player);
                break;

            case 4:
                newRoom = new Rest(((row * 100) + (col)));
                break;
            case 5:
                newRoom = new Shop(((row * 100) + (col)));
                ((Shop) newRoom).generateItems(4);
                break;
            //No room to create.
            default:
                newRoom = null;
                break;
        }

        return newRoom;
    }//createRoom

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

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    @Override
    public String toString(){
        StringBuilder construct = new StringBuilder();

        for (int y = 0; y < floorMap.length; y ++){
            for (int x = 0; x < floorMap[y].length; x++){
                construct.append(" " + floorMap[y][x] + " ");
            }
            construct.append('\n');

        }
        return construct.toString();
    }

    public Room createFloor(){
        Room origin = null;
        Room prevRoom = null;
        Room cursorRowHead = null;
        Room cursorRoom = null;
        Room aboveRowHead = null;
        Room aboveRoomCursor = null;

        for (int row = 0; row < floorMap.length; row++){
            cursorRowHead = null;
            prevRoom = null;
            //Make each room for the current row and links them together ignoring gaps.
            for (int col = 0; col < floorMap[row].length; col++){
                if (floorMap[row][col] != 0){
                    cursorRoom = createRoom(row, col);

                    if (cursorRowHead == null){
                        cursorRowHead = cursorRoom;
                    }

                    if (floorMap[row][col] == 1){
                        origin = cursorRoom;
                    }

                    if (prevRoom != null) {
                        cursorRoom.setLeftRoom(prevRoom);
                        prevRoom.setRightRoom(cursorRoom);
                    }
                    prevRoom = cursorRoom;
                }
            }//col for loop
            //Link the above row verticals with the current row
            if (aboveRowHead != null) {
                cursorRoom = cursorRowHead;
                aboveRoomCursor = aboveRowHead;

                while (cursorRoom != null && aboveRoomCursor != null){
                    if ((cursorRoom.getRoomId() % 100) == (aboveRoomCursor.getRoomId() % 100)){
                        cursorRoom.setUpRoom(aboveRoomCursor);
                        aboveRoomCursor.setDownRoom(cursorRoom);
                        cursorRoom = cursorRoom.getRight();
                        aboveRoomCursor = aboveRoomCursor.getRight();
                    } else if ((cursorRoom.getRoomId() % 100) > (aboveRoomCursor.getRoomId() % 100)){
                        aboveRoomCursor = aboveRoomCursor.getRight();
                    } else {
                        cursorRoom = cursorRoom.getRight();
                    }
                }

                //Remove Side links for gaps in map
                aboveRoomCursor = aboveRowHead.getRight();
                while (aboveRoomCursor != null){
                    if(((aboveRoomCursor.getLeft().getRoomId() % 100) + 1) != (aboveRoomCursor.getRoomId() % 100)){
                        aboveRoomCursor.getLeft().setRightRoom(null);
                        aboveRoomCursor.setLeftRoom(null);
                    }
                    aboveRoomCursor = aboveRoomCursor.getRight();
                }
            }
            //Move aboveRoom down a row

            aboveRowHead = cursorRowHead;
        }//row for loop

        //Remove Side links for gaps in map for final row
        if (aboveRowHead != null) {
            aboveRoomCursor = aboveRowHead.getRight();
            while (aboveRoomCursor != null){
                if(((aboveRoomCursor.getLeft().getRoomId() % 100) + 1) != (aboveRoomCursor.getRoomId() % 100)){
                    aboveRoomCursor.getLeft().setRightRoom(null);
                    aboveRoomCursor.setLeftRoom(null);
                }
                aboveRoomCursor = aboveRoomCursor.getRight();
            }
        }

        return origin;
    }//createFloor

    public Room getHead(){
        return headRoom;
    }

    /**
     * Nice
     * @param newHead
     */
    public void setHead(Room newHead){
        headRoom = newHead;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int[][] getFloorMap() {
        return floorMap;
    }


    public Player getPlayer() {
        return player;
    }

    private Enemy getRandomSet1Enemy() {
        double random = Math.random() * FLOOR1ENEMIES;

        if (random <= 1) {
            return new Slime("Slime", 12 * currentFloor, 2 * currentFloor,
                    6 * currentFloor, 6);
        } else if (random <= 2) {
            return new Goblin("Goblin", 8 * currentFloor, 4 * currentFloor,
                    8 * currentFloor, 12);
        } else {
            return new Slime("King Slime", 20 * currentFloor);
        }
    }

    private Enemy makeRandomEnemy() {
        return getRandomSet1Enemy();
        /*
        if (currentFloor <= 1) {
            return getRandomSet1Enemy();
        } else {
            return new Slime("King Slime", 20);
        }
         */
    }

    public void hideArrows() {
        game.disableArrows(true);
    }

    public void showArrows() {
        game.disableArrows(false);
    }

    public void setCurrentFloor(int floor){
        currentFloor = floor;
    }
}