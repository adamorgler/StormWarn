package com.stormwarn.game.storm.partitions;

import java.util.ArrayList;

public class StormCell {

    private final int id;

    private int strength;

    private float xpos;

    private float ypos;

    private float vx;

    private float vy;

    private ArrayList<StormPartition> parts;

    public StormCell(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public float getXpos() {
        return xpos;
    }

    public void setXpos(float xpos) {
        this.xpos = xpos;
    }

    public float getYpos() {
        return ypos;
    }

    public void setYpos(float ypos) {
        this.ypos = ypos;
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

    public ArrayList<StormPartition> getParts() {
        return parts;
    }

    public void setParts(ArrayList<StormPartition> parts) {
        this.parts = parts;
    }
}
