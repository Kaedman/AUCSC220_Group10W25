package com.example.untitleddungeongame.entity;

import com.example.untitleddungeongame.Assets;
import com.example.untitleddungeongame.animations.AnimatedSprite;
import com.example.untitleddungeongame.animations.Animation;
import com.example.untitleddungeongame.animations.Sprite;
import com.example.untitleddungeongame.handlers.DrawInstructions;

public class Slime extends Enemy{
    public static Sprite sprite  = new Sprite(Assets.AssetID.ENEMY_SLIME , 32, 32, 9);

    public Slime(String name, int hp){
        super(name, hp, 1, 0, 1);
        setUpAnimation();
    }
    public void setUpAnimation(){
        animatedSprite = new AnimatedSprite(sprite);
        animatedSprite.addAnimation(new Animation("idle", 0, 9, new int[] {150, 94, 74, 94, 300, 94, 74, 94, 150}));
        animatedSprite.setCurrentAnimation("idle");
        animatedSprite.setCurrentRepeat(true);
        animatedSprite.playCurrentAnimation();
    }
}
