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
    int playerX, playerY;

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

        int gapY = bitmapHeight / fullMap.length; //Displacers
        int gapX = bitmapWidth / fullMap[0].length;

        Rect drawRect = new Rect(gapX, gapY, gapX, gapY);

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


                    outline.setColor(Color.GREEN);
                    if (currentRoom == 1)
                        outline.setColor(Color.BLUE);
                    drawer.drawRect(drawRect, outline);
                }
                drawRect.left += gapX;
            }
            drawRect.left = gapX;
            drawRect.top += gapY;
        }
    }
    public void drawToCanvas(Canvas c, int positionX, int positionY){
        c.drawBitmap(mapLooks, positionX, positionY, null);
    }

    public void updateExploredMap(int y, int x){
        exploredMap[y][x] = fullMap[y][x];

    }

}
