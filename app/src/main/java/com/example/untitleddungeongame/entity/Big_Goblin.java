package com.example.untitleddungeongame.entity;

import com.example.untitleddungeongame.Assets;
import com.example.untitleddungeongame.animations.AnimatedSprite;
import com.example.untitleddungeongame.animations.Animation;
import com.example.untitleddungeongame.animations.Sprite;
import com.example.untitleddungeongame.handlers.DrawInstructions;
import com.example.untitleddungeongame.hotbar.attacks.BigSlamma;
import com.example.untitleddungeongame.hotbar.attacks.QuickAttack;

public class Big_Goblin extends Enemy{
    private static Sprite sprite = new Sprite(Assets.AssetID.ENEMY_BIG_GOBLIN, 64, 64, 4);
    public Big_Goblin(String name, int hp, int attack, int defense, int speed){
        super(name, hp, attack, defense, speed);
        setUpAnimation();
        attacks[0] = new BigSlamma();
    }
    public Big_Goblin(String name, int hp){
        super(name, hp, 3, 1, 1);
        setUpAnimation();
    }

    private void setUpAnimation(){
        animatedSprite = new AnimatedSprite(sprite);
        animatedSprite.addAnimation(new Animation("idle", 0, 3, new int[] {500, 100, 600, 84}));
        animatedSprite.setCurrentAnimation("idle");
        animatedSprite.setCurrentRepeat(true);
        animatedSprite.playCurrentAnimation();
//        drawInstructions = new DrawInstructions(550, 800, animatedSprite,Sprite.universalSpriteScale, Sprite.universalSpriteScale);
        animatedSprite.doDraw = true;
        makeDrawInstructions(550, 800);
    }
}
