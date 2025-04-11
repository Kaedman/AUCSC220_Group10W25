package com.example.untitleddungeongame.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.provider.Telephony;

import java.util.Random;

public class ParticleSystem {

    public int particleMaxCount;
    public int particleFrameMin, particleFrameMax;

    private int particleVelMinX, particleVelMaxX;
    private int particleVelMinY, particleVelMaxY;
    private int particlePosMinX, particlePosMaxX;
    private int particlePosMinY, particlePosMaxY;

    private int particleVelChangeMinX, particleVelChangeMaxX;
    private int particleVelChangeMinY, particleVelChangeMaxY;

    private Particle[] particles;
    private Bitmap particleLooks;
    private static Random r = new Random();
    public ParticleSystem(int maxParticles, int particleFrameMin, int particleFrameMax){
        particleMaxCount = maxParticles;
        this.particleFrameMin = particleFrameMin;
        this.particleFrameMax = particleFrameMax;

    }

    public void createParticles(){
        particles = new Particle[particleMaxCount];

    }

    public void createRectBaseParticle(int color, int rectX, int rectY){
        particleLooks = Bitmap.createBitmap(rectX, rectY, Bitmap.Config.ARGB_8888);

        Canvas drawer = new Canvas(particleLooks);
        drawer.setBitmap(particleLooks);

        Paint p = new Paint(); p .setColor(color);

        drawer.drawRect(0,0, rectX, rectY, p);

    }

    /**
     * This function has violated the principles of clean code
     * Sets the random particle settings ranges for particles generated under this particle system
     * @param posXMin - startPositionMin of particles
     * @param posXMax - startPositionMax of particles
     * @param posYMin - same but y
     * @param posYMax - same but y
     * @param velXMin - position change min for particles
     * @param velXMax - position change max for particles
     * @param velYMin - y
     * @param velYMax - y
     * @param velXChangeMin - velocity change min for particles
     * @param velXChangeMax - velocity change max for particles
     * @param velYChangeMin - y
     * @param velYChangeMax - y
     */
    public void setParticleSettings(int posXMin, int posXMax, int posYMin, int posYMax, int velXMin, int velXMax, int velYMin, int velYMax, int velXChangeMin, int velXChangeMax, int velYChangeMin, int velYChangeMax){
        particlePosMinX = posXMin; particlePosMaxX = posXMax;
        particlePosMinY = posYMin; particlePosMaxY = posYMax;

        particleVelMinX = velXMin; particleVelMaxX = velXMax;
        particleVelMinY = velYMin; particleVelMaxY = velYMax;

        particleVelChangeMinX = velXChangeMin; particleVelChangeMaxX = velXChangeMax;
        particleVelChangeMinY = velYChangeMin; particleVelChangeMaxY = velYChangeMax;
    }

    private Particle getNewParticle(){
        Particle p = new Particle(0, 0, 0);
        if (particleLooks != null)
            p.setVisualBitmap(particleLooks);

        resetParticle(p);

        return p;
    }
    private void resetParticle(Particle p){
        //https://stackoverflow.com/questions/363681/how-do-i-generate-random-integers-within-a-specific-range-in-java
        //Random Ranges
        int minX = particlePosMinX + r.nextInt(particlePosMaxX - particlePosMinX + 1);
        int minY = particlePosMinX + r.nextInt(particlePosMaxY - particlePosMinY + 1);

        int velX = particleVelMinX + r.nextInt(particleVelMaxX - particleVelMinX + 1);
        int velY = particleVelMinY + r.nextInt(particleVelMaxY - particleVelMinY + 1);

        int velChangeX = particleVelChangeMinX + r.nextInt(particleVelChangeMaxX - particleVelChangeMinX + 1);
        int velChangeY = particleVelChangeMinY + r.nextInt(particleVelChangeMaxY - particleVelChangeMinY + 1);

        int lifeTime = particleFrameMin + r.nextInt(particleFrameMax - particleFrameMin + 1);

        p.setPosX(minX); p.setPosY(minY);
        p.setVelocityX(velX); p.setVelocityY(velY);

        p.setVelocityChangeX(velChangeX); p.setVelocityChangeY(velChangeY);

        p.setMaxLifeTime(lifeTime); p.setCurrentLifeTime(0);


    }

    public void createAllParticles(){
        int randomLifeTimeStart;
        for (int p = 0; p < particles.length; p++){
            particles[p] = getNewParticle();
            randomLifeTimeStart = particleFrameMin + r.nextInt(particleFrameMax - particleFrameMin + 1);
            particles[p].setCurrentLifeTime(randomLifeTimeStart);
        }
    }

    public void updateParticles(){
        Particle current;
        for (int p = 0; p < particles.length; p++){

            current = particles[p];

            if (current == null){
                particles[p] = getNewParticle();
                current = particles[p];
            }

            if (current.alive)
                resetParticle(current);
            current.update();

        }

    }


}
