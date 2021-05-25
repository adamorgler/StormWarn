package com.stormwarn.game.graphics;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class MyCamera extends OrthographicCamera {

    private static MyCamera instance;

    private float xpos;

    private float ypos;

    private float zpos;

    public MyCamera() {
        this.zoom = 1f;
        this.xpos = 0f;
        this.ypos = 0f;
        this.zpos = 0f;

        this.position.set(xpos, ypos, zpos);
    }

    public static MyCamera getInstance() {
        if (instance == null) {
            instance = new MyCamera();
        }
        return instance;
    }

    public float getXpos() {
        return xpos;
    }

    public void setXpos(float xpos) {
        this.xpos = xpos;
        this.position.set(xpos, ypos, zpos);
    }

    public float getYpos() {
        return ypos;
    }

    public void setYpos(float ypos) {
        this.ypos = ypos;
        this.position.set(xpos, ypos, zpos);
    }

    public float getZpos() {
        return zpos;
    }

    public void setZpos(float zpos) {
        this.zpos = zpos;
        this.position.set(xpos, ypos, zpos);
    }

    public float getZoom() {
        return zoom;
    }

    public void setZoom(float zoom) {
        if (zoom <= 2 && zoom >= 0.1) {
            this.zoom = zoom;
        }
    }
}
