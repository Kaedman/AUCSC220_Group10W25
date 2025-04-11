package com.example.untitleddungeongame.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;


public class MiniMap {
    private int[][] fullMap, exploredMap;

    private Bitmap mapLooks;
    private Paint refreshPaint;
    private Paint paint;

    private int bitmapWidth, bitmapHeight;
    public static int playerX, playerY = -1;

    public MiniMap(int[][] actualMap, int visualWidth, int visualHeight){
        fullMap = actualMap;
        exploredMap = new int[actualMap.length][actualMap[0].length];

        refreshPaint = new Paint();
        refreshPaint.setStyle(Paint.Style.FILL);
        refreshPaint.setColor(Color.BLACK);
        paint = new Paint();

        bitmapWidth = visualWidth;
        bitmapHeight = visualHeight;

        mapLooks = Bitmap.createBitmap(visualWidth, visualHeight, Bitmap.Config.ARGB_8888);

    }

    public void makeMapVisual(){
        Canvas drawer = new Canvas(mapLooks);
        drawer.setBitmap(mapLooks);

        drawer.drawPaint(refreshPaint);

        int cellWidth = bitmapWidth / fullMap[0].length;
        int cellHeight = bitmapHeight / fullMap.length;

        Rect drawRect = new Rect(0, 0, cellWidth, cellHeight);

        Paint outline = new Paint();
        outline.setStyle(Paint.Style.STROKE);
        outline.setColor(Color.BLACK);
        outline.setStrokeWidth(10);


        for (int y = 0; y < exploredMap.length; y++){
            for (int x = 0; x < exploredMap[y].length; x++){
                int currentRoom = exploredMap[y][x];
                if (currentRoom != 0) {
                    paint.setColor(Color.GRAY);
                    drawer.drawRect(drawRect, paint);

                    outline.setColor(Color.BLACK);
                    drawer.drawRect(drawRect, outline);
                    //Specific room minimap drawings
                    switch (currentRoom) {
                        case 2:
                            outline.setColor(Color.RED);
                            drawer.drawRect(drawRect, outline);
                            break;
                        case 1:
                            outline.setColor(Color.BLUE);
                            drawer.drawRect(drawRect, outline);
                            break;
                        case 3:
                            outline.setColor(Color.GREEN);
                            drawer.drawRect(drawRect, outline);
                            break;
                    }

                }
                drawRect.left += cellWidth; //Shift the drawing area right
                drawRect.right += cellWidth;
            }
            //Shift the drawing area down and to the left side
            drawRect.left = 0;
            drawRect.right = cellWidth;
            drawRect.top += cellHeight;
            drawRect.bottom += cellHeight;
        }
        //Draw player current position
        outline.setColor(Color.GREEN);
        drawer.drawCircle(playerX * cellWidth + cellWidth/2, playerY * cellHeight + cellHeight/2, cellWidth/4, outline);
    }
    public void drawToCanvas(Canvas c, int positionX, int positionY){
        c.drawBitmap(mapLooks, positionX, positionY, null);
    }

    public void updateExploredMap(int y, int x){
        exploredMap[y][x] = fullMap[y][x];
    }



    @Override
    public String toString(){
        StringBuilder construct = new StringBuilder();

        for (int y = 0; y < exploredMap.length; y ++){
            for (int x = 0; x < exploredMap[y].length; x++){
                construct.append(" " + exploredMap[y][x] + " ");
            }
            construct.append('\n');

        }
        return construct.toString();
    }

    public void setExploredMap(int[][] exploredMap) {
        this.exploredMap = exploredMap;
    }

    public void updateCurrentMapWithPlayerPosition(){
        exploredMap[playerY][playerX] = fullMap[playerY][playerX];
    }

    public void checkBossAdjacentAndMark(){
        if (playerX != exploredMap[0].length -1)
            if (fullMap[playerY][playerX + 1] == 2) {
                updateExploredMap(playerY, playerX + 1);
                return;
            }
        if (playerX != 0)
            if (fullMap[playerY][playerX - 1] == 2){
                updateExploredMap(playerY, playerX - 1);
                return;
            }
        if (playerY != exploredMap.length -1)
            if (fullMap[playerY + 1][playerX] == 2){
                updateExploredMap(playerY + 1, playerX);
                return;
            }
        if (playerY != 0)
            if (fullMap[playerY - 1][playerX] == 2){
                updateExploredMap(playerY -1, playerX);
                return;
            }


    }
}
