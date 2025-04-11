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
    public Slime(String name, int hp){
        super(name, hp);
        setUpAnimation();

    }
    private void setUpAnimation(){
        animatedSprite = new AnimatedSprite(sprite);
        animatedSprite.addAnimation(new Animation("idle", 0, 9, new int[] {150, 94, 74, 94, 300, 94, 74, 94, 150}));
        animatedSprite.setCurrentAnimation("idle");
        animatedSprite.setCurrentRepeat(true);
        animatedSprite.playCurrentAnimation();
    }
    public void makeDrawInstructions(int posX, int posY, int scaleX, int scaleY){
        drawInstructions = new DrawInstructions(posX, posY, animatedSprite, scaleX, scaleY);
    }

    public void makeDrawInstructions(int posX, int posY){
        makeDrawInstructions(posX, posY, Sprite.universalSpriteScale, Sprite.universalSpriteScale);

    }

    public void yeetEnemy(){
        if (drawInstructions != null){
            drawInstructions.setY(200000);
        }
    }

    public void repositionEnemy(int x, int y){
        if (drawInstructions != null){
            drawInstructions.setX(x);
            drawInstructions.setY(y);
        }
    }

}
