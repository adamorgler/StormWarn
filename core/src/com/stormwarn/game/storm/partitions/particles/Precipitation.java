package com.stormwarn.game.storm.partitions.particles;

public class Precipitation extends Particle {

    private int reflectivity;

    public Precipitation(float xpos, float ypos, float vx, float vy) {
        super(xpos, ypos, vx, vy);
    }

    public int getReflectivity() {
        return reflectivity;
    }

    public void setReflectivity(int reflectivity) {
        this.reflectivity = reflectivity;
    }
}
