package com.stormwarn.game.storm.partitions.particles;

public class Particle {

    private float xPos;

    private float yPos;

    private float vx;

    private float vy;

    public Particle(float xpos, float ypos, float vx, float vy) {
        this.xPos = xpos;
        this.yPos = ypos;
        this.vx = vx;
        this.vy = vy;
    }

    public float getxPos() {
        return xPos;
    }

    public void setxPos(float xPos) {
        this.xPos = xPos;
    }

    public float getyPos() {
        return yPos;
    }

    public void setyPos(float yPos) {
        this.yPos = yPos;
    }

    public float getVx() {
        return vx;
    }

    public void setVx(float vx) {
        this.vx = vx;
    }

    public float getVy() {
        return vy;
    }

    public void setVy(float vy) {
        this.vy = vy;
    }
}
