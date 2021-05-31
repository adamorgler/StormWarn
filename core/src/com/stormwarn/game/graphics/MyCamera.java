package com.stormwarn.game.graphics;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class MyCamera extends OrthographicCamera {

    private static MyCamera instance;

    private float xPos;

    private float yPos;

    private float zPos;

    private float xBound;

    private float yBound;

    private float zoomLowerBound;

    private float zoomUpperBound;

    public MyCamera() {
        this.zoom = 1f;
        this.xPos = 0f;
        this.yPos = 0f;
        this.zPos = 0f;
        this.zoomLowerBound = 0.1f;
        this.zoomUpperBound = 1.2f;

        this.position.set(xPos, yPos, zPos);
    }

    public static MyCamera getInstance() {
        if (instance == null) {
            instance = new MyCamera();
        }
        return instance;
    }

    public float getxPos() {
        return xPos;
    }

    public void setxPos(float xPos) {
        if (xPos < -xBound) {
            xPos = -xBound;
        } else if (xPos > xBound) {
            xPos = xBound;
        }
        this.xPos = xPos;
        this.position.set(xPos, yPos, zPos);
    }

    public void moveXPos(float a) {
        setxPos(getxPos() + a);
    }

    public float getyPos() {
        return yPos;
    }

    public void setyPos(float yPos) {
        if (yPos < -yBound) {
            yPos = -yBound;
        } else if (yPos > yBound) {
            yPos = yBound;
        }
        this.yPos = yPos;
        this.position.set(xPos, yPos, zPos);
    }

    public void moveYPos(float a) {
        setyPos(getyPos() + a);
    }


    public void setzPos(float zPos) {
        //nothing
    }

    public float getzPos() {
        return zPos;
    }

    public float getZoom() {
        return zoom;
    }

    public void setZoom(float zoom) {
        if (zoom <= zoomUpperBound && zoom >= zoomLowerBound) {
            this.zoom = zoom;
        }
    }

    public void moveZoom(float a) {
        setZoom(getZoom() + a);
    }

    public void setxBound() {
        this.xBound = viewportWidth / 2;
    }

    public void setyBound() {
        this.yBound = viewportHeight / 2;
    }
}
