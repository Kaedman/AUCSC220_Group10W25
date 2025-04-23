package com.example.untitleddungeongame.entity;

import com.example.untitleddungeongame.Assets;
import com.example.untitleddungeongame.animations.AnimatedSprite;
import com.example.untitleddungeongame.animations.Animation;
import com.example.untitleddungeongame.animations.Sprite;

public class Goblin extends Enemy{
    private static Sprite sprite = new Sprite(Assets.AssetID.ENEMY_GOBLIN, 32, 32, 4);
    public Goblin(String name, int hp){
        super(name, hp, 1, 0, 1);
        setUpAnimation();
    }

    private void setUpAnimation(){
        animatedSprite = new AnimatedSprite(sprite);
        animatedSprite.addAnimation(new Animation("idle", 0, 3, new int[] {250, 120, 350, 120}));
        animatedSprite.setCurrentAnimation("idle");
        animatedSprite.setCurrentRepeat(true);
        animatedSprite.playCurrentAnimation();
    }
}
