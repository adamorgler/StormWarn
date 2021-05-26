package com.stormwarn.game.radar;

public class RadarCell {

    private int rPos;

    private int dPos;

    /**
     * Active when precipitation is present
     */
    private boolean isActive;

    /**
     * Amount of precipitation
     */
    private int reflectivity;

    /**
     * Velocity relative to radar
     */
    private int velocity;

    public RadarCell(int rPos, int dPos) {
        this.isActive = false;
        this.reflectivity = 0;
        this.velocity = 0;
        this.rPos = rPos;
        this.dPos = dPos;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getReflectivity() {
        return reflectivity;
    }

    public void setReflectivity(int reflectivity) {
        if (reflectivity >= 0) {
            this.reflectivity = reflectivity;
            checkActive();
        }
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    private void checkActive() {
        if (reflectivity > 0) {
            setActive(true);
        } else {
            setActive(false);
        }
    }

    public int getrPos() {
        return rPos;
    }

    public void setrPos(int rPos) {
        this.rPos = rPos;
    }

    public int getdPos() {
        return dPos;
    }

    public void setdPos(int dPos) {
        this.dPos = dPos;
    }
}
