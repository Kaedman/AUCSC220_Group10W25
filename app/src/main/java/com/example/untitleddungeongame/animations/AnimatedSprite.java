package com.example.untitleddungeongame.animations;

import android.graphics.Canvas;

import java.util.ArrayList;

public class AnimatedSprite {
    Sprite sprite;
    ArrayList<Animation> animations;

    Animation current;

    int currentFrame;
    public boolean doDraw;

    /**
     * Animation Handler for multiple animations
     * @param source - Pointer to Sprite source
     */
    public AnimatedSprite(Sprite source){
        sprite = source;
        animations = new ArrayList<Animation>();
        currentFrame = 0;

        doDraw = true;

    }

    /**
     * Adds a playable animation to the set of animations
     */
    public void addAnimation(Animation animation){
        //Check for existing animation name
        animations.add(animation);
    }

    /**
     * Sets the current animation to the provided name. NOTE: Can set to null if no valid animation name provided
     * @param name - String name of animation
     */
    public void setCurrentAnimation(String name){
        current = searchAnimation(name);
    }
    public void playCurrentAnimation(){
        if (isCurNull()) return;
        current.startAnimation();
    }

    public void updateCurrentAnimation(){
        if (isCurNull()) return;
        currentFrame = current.updateFrame();
        sprite.setCurrentSprite(currentFrame);
    }

    public void drawAnimation(Canvas canvas, int positionX, int positionY, int scaleMultiplierX, int scaleMultiplierY){
        if (isCurNull()) return;
        sprite.drawScaled(canvas, positionX, positionY, scaleMultiplierX, scaleMultiplierY);

    }

    public void setCurrentRepeat(boolean doesRepeat){
        if (isCurNull()) return;
        current.setRepeat(doesRepeat);
    }
    //Method helpers
    private boolean isCurNull(){
        if (current == null)
            return true;
        return false;
    }

    private Animation searchAnimation(String name){
        Animation curr;
        for (int i = 0; i < animations.size(); i++){
            curr = animations.get(i);
            if (curr.animationName.equals(name)){
                return curr;

            }
        }
        return null;

    }

    public Sprite getSprite() {
        return sprite;
    }

    public Animation getCurrentAnimation() {
        if (isCurNull())
            return null;
        return current;
    }
}
