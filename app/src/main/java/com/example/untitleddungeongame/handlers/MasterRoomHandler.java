package com.example.untitleddungeongame.handlers;

import com.example.untitleddungeongame.ui.RoomVisual;

public class MasterRoomHandler {

    private int xBound, yBound, currentX, currentY;

    private RoomVisual[][] roomVisuals;

    private int[][] roomMap;

    /**
     * Visual Handler for drawing all rooms
     * @param numberOfRoomsX - width of floor in room size (index max)
     * @param numberOfRoomsY - height of floor in room size (index max)
     * @param floorMap - int[][] floor data
     * @param startX - player start position x
     * @param startY - player start position y
     */
    public MasterRoomHandler(int numberOfRoomsX, int numberOfRoomsY, int[][] floorMap, int startX, int startY){
        xBound = numberOfRoomsX;
        yBound = numberOfRoomsY;
        roomMap = floorMap; //Used in generatiing rooms
        setCurrentRoom(startX, startY);
    }
    public void setCurrentRoom(int x, int y){
        currentX = x;
        currentY = y;
    }

    public void generate_EVERY_RoomVisual(){
        int[][] tracker = new int[yBound][xBound]; //Used to avoid reGenerating a visual for a room
        roomVisuals = new RoomVisual[yBound][xBound];


    }

    public void roomVisualWorker(){

    }

}
