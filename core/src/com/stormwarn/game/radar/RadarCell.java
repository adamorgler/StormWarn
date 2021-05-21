package com.stormwarn.game.radar;

public class RadarCell {

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

    public RadarCell() {
        this.isActive = false;
        this.reflectivity = 0;
        this.velocity = 0;
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
}
