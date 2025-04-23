package com.example.untitleddungeongame.misc;

import com.example.untitleddungeongame.Assets;
import com.example.untitleddungeongame.animations.AnimatedSprite;
import com.example.untitleddungeongame.animations.Animation;
import com.example.untitleddungeongame.animations.Sprite;
import com.example.untitleddungeongame.handlers.DrawInstructions;

public class Torch {
    private static Sprite torch = new Sprite(Assets.AssetID.TORCH, 32, 64, 6);

    private AnimatedSprite animatedTorch;
    private Animation torchAnimation;
    private DrawInstructions drawInstructions;

    public int posX, posY;
    public int scaleX, scaleY;

    public Torch(int posX, int posY, int scaleX, int scaleY){
        this.posX = posX; this.posY = posY;
        this.scaleX = scaleX; this.scaleY = scaleY;

        setUpAnimation();
    }
    public Torch(int posX, int posY){
        this(posX, posY, Sprite.universalSpriteScale, Sprite.universalSpriteScale);
    }

    private void setUpAnimation(){
        torchAnimation = new Animation("idle", 0, 5, new int[] {84, 84, 84, 84, 84, 84});

        animatedTorch = new AnimatedSprite(torch);
        animatedTorch.addAnimation(torchAnimation);
        animatedTorch.setCurrentAnimation("idle");
        animatedTorch.setCurrentRepeat(true);
        animatedTorch.playCurrentAnimation();

        drawInstructions = new DrawInstructions(posX, posY, animatedTorch, scaleX, scaleY);
    }

    //TODO: Add handler to hide the animatedSprite if the current room is not the room the torch is in
}
