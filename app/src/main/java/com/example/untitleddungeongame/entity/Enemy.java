package com.example.untitleddungeongame.entity;

import com.example.untitleddungeongame.Assets;
import com.example.untitleddungeongame.animations.AnimatedSprite;
import com.example.untitleddungeongame.animations.Sprite;
import com.example.untitleddungeongame.handlers.DrawInstructions;
import com.example.untitleddungeongame.hotbar.attacks.QuickAttack;

public class Enemy extends Entity {

    public AnimatedSprite animatedSprite;
    public DrawInstructions drawInstructions;
    public int posX;
    public int posY;

    public Enemy(String name, int health, int attack, int defense, int speed) {
        super(name, health, attack, defense, speed);
        attacks[0] = new QuickAttack();
    }

    //Visuals -- Mainly copied from slime to be used for all enemies


    /**
     * Creates a new draw instruction for the given enemy, allowing automatic animation
     * @param posX - position X
     * @param posY - position Y
     * @param scaleX - scale X
     * @param scaleY - scale Y
     */
    public void makeDrawInstructions(int posX, int posY, int scaleX, int scaleY){
        if (animatedSprite == null)
            return;

        drawInstructions = new DrawInstructions(posX, posY, animatedSprite, scaleX, scaleY);
        this.posX = posX;
        this.posY = posY;
    }

    public void makeDrawInstructions(int posX, int posY){
        makeDrawInstructions(posX, posY, Sprite.universalSpriteScale, Sprite.universalSpriteScale);
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


}
