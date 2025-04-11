package com.example.untitleddungeongame.entity;

import com.example.untitleddungeongame.Assets;
import com.example.untitleddungeongame.animations.AnimatedSprite;
import com.example.untitleddungeongame.animations.Animation;
import com.example.untitleddungeongame.animations.Sprite;
import com.example.untitleddungeongame.handlers.DrawInstructions;

public class Slime extends Enemy{
    public static Sprite sprite  = new Sprite(Assets.AssetID.ENEMY_SLIME , 32, 32, 9);

    private AnimatedSprite animatedSprite;
    public DrawInstructions drawInstructions;
    private int posX;
    private int posY;
    public Slime(String name, int hp){
        super(name, hp, 1, 0, 1);
        setUpAnimation();
    }
    private void setUpAnimation(){
        animatedSprite = new AnimatedSprite(sprite);
        animatedSprite.addAnimation(new Animation("idle", 0, 9, new int[] {150, 94, 74, 94, 300, 94, 74, 94, 150}));
        animatedSprite.setCurrentAnimation("idle");
        animatedSprite.setCurrentRepeat(true);
        animatedSprite.playCurrentAnimation();
    }

    /**
     * Creates a new draw instruction for the given enemy, allowing automatic animation
     * @param posX - position X
     * @param posY - position Y
     * @param scaleX - scale X
     * @param scaleY - scale Y
     */
    public void makeDrawInstructions(int posX, int posY, int scaleX, int scaleY){
        drawInstructions = new DrawInstructions(posX, posY, animatedSprite, scaleX, scaleY);
        this.posX = posX;
        this.posY = posY;
    }

    public void makeDrawInstructions(int posX, int posY){
        makeDrawInstructions(posX, posY, Sprite.universalSpriteScale, Sprite.universalSpriteScale);
    }

    /**
     * Get that enemy out of my sight!
     */
    public void yeetEnemy(boolean isDead){
        if (drawInstructions != null){
            if (isDead) {
                drawInstructions.setY(200000);
            } else {
                drawInstructions.setY(posY);
            }
        }
    }

    /**
     * Allows repositioning of the drawInstruction on the enemy if drawInstruction has been made
     * @param x - new position X
     * @param y - new position Y
     */
    public void repositionEnemy(int x, int y){
        if (drawInstructions != null){
            drawInstructions.setX(x);
            drawInstructions.setY(y);
        }
    }

    public AnimatedSprite getAnimatedSprite() {
        return animatedSprite;
    }
}
