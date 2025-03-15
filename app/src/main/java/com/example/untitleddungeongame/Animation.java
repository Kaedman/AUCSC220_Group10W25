package com.example.untitleddungeongame;
/*
Class to manage sprite animations, ie delays between frames, looping, which frames are included
 */
public class Animation {
    private String animationName;
    private int min;
    private int max;
    private int durr[];

    private int index;
    private long miliOld;
    private long miliDiff;

    private boolean repeat;

    /**
     Animation Class to handle current frame of a animation
     @param name - animation name
     @param frameMin - starting frame
     @param frameMax - ending frame
     @param durations - millisecond durations for each frame
     */
    public Animation(String name, int frameMin, int frameMax, int durations[]){
        animationName = name;
        min = frameMin;
        max = frameMax;
        durr = durations;

        index = min;
        repeat = false;

    }

    public void setRepeat(boolean doesRepeat){
        repeat = doesRepeat;
    }

    public void startAnimation(){
        miliOld = System.currentTimeMillis();
    }

    public int updateFrame(){
        miliDiff = System.currentTimeMillis() - miliOld;

        long miliSum = 0; //This will track how much miliseconds need to pass for which frame
        int frameCalc = 0;
        for (int i = 0; i < durr.length; i++){

            miliSum += durr[i];
            //Add up all milisecond durations, think of it as after x amount of miliseconds, we go to next frame
            if (miliDiff > miliSum){
                frameCalc ++;


            }
            //Reaching this else statment means we are somewhere between the min and the max
            else{
                return frameCalc + min;
            }

        } //Gone through entire loop ==> Reached last frame

        if (repeat){
            startAnimation();
            return min;
        }

        return max;

    }
}
