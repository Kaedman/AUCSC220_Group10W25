package com.example.untitleddungeongame.ui;

import android.graphics.Bitmap;

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
        //https://stackoverflow.com/questions/363681/how-do-i-generate-random-integers-within-a-specific-range-in-java
        //Random Ranges
        int minX = particlePosMinX + r.nextInt(particlePosMaxX - particlePosMinX + 1);
        int minY = particlePosMinX + r.nextInt(particlePosMaxY - particlePosMinY + 1);

//        Particle p = new Particle(r.nextInt(particlePosMinX, particlePosMaxX), r.nextInt(particlePosMinY, particlePosMaxY),
//                r.nextInt(particleVelMinX, particleVelMaxX), r.nextInt(particleVelMinY, particleVelMaxY),
//                r.nextInt(particleVelChangeMinX, particleVelChangeMaxX), r.nextInt(particleVelChangeMinY, particleVelChangeMaxY),
//                r.nextInt(particleFrameMin, particleFrameMax)
//                );
        return null;
    }
    private void resetParticle(Particle p){

    }

    public void updateParticles(){

    }


}
