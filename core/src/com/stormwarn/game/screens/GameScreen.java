package com.stormwarn.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.stormwarn.game.controllers.EnvController;
import com.stormwarn.game.graphics.MyCamera;
import com.stormwarn.game.main.StormWarn;
import com.stormwarn.game.radar.Radar;
import com.stormwarn.game.radar.RadarCell;

import java.util.Random;

public class GameScreen implements Screen {

    final StormWarn game;

    final EnvController envController;

    private MyCamera camera;

    private Radar radar;

    private RadarCell[][] radarCells;

    private PolygonRegion[][] radarPolygons;

    private int cellWidth;

    private int cellHeight;

    private int deadZoneOffset;

    public GameScreen(StormWarn game) {
        this.game = game;
        this.envController = EnvController.getInstance();

        camera = MyCamera.getInstance();
        camera.setToOrtho(false, 1600, 960);
        camera.setxBound();
        camera.setyBound();
        camera.setxPos(0);
        camera.setyPos(0);
        camera.setzPos(0);
        camera.setZoom(1f);

        radar = new Radar();
        cellWidth = 1;
        cellHeight = 2;
        deadZoneOffset = 5 * cellHeight;

        radarCells = radar.getRadarCells();
        radarPolygons = new PolygonRegion[radar.getRadialAmount()][radar.getDistance()];
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        camera.update();

        //GAME LOGIC START
        radar.setRadar(envController.getParticles());
        //GAME LOGIC END


        // GRAPHICS START
        game.shapeRenderer.setProjectionMatrix(camera.combined);
        //filled in shapes
        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        drawRadar();
        game.shapeRenderer.end();

        //outlines
        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        game.shapeRenderer.setColor(Color.LIGHT_GRAY);
        game.shapeRenderer.circle(0,
                0,
                (radar.getDistance() * cellHeight) + deadZoneOffset);
        game.shapeRenderer.end();
        // GRAPHICS END

        // KEY BINDS START
        // ---------------
        // LEFT
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            camera.moveXPos(-20);
        }
        // RIGHT
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            camera.moveXPos(20);
        }
        // UP
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            camera.moveYPos(20);
        }
        // DOWN
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            camera.moveYPos(-20);
        }
        // =
        if (Gdx.input.isKeyPressed(Input.Keys.EQUALS)) {
            camera.moveZoom(-0.1f);
        }
        // -
        if (Gdx.input.isKeyPressed(Input.Keys.MINUS)) {
            camera.moveZoom(0.1f);
        }
        // KEY BINDS END

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        game.batch.dispose();
        game.shapeRenderer.dispose();
        game.font.dispose();
    }


    private void drawRadar() {
        for (int i = 0; i < radarCells.length; i++) {
            for (int j = 0; j < radarCells[i].length; j++) {
                int r = radarCells[i][j].getReflectivity();
                int rpos = i;
                int dpos = (j * cellHeight) + deadZoneOffset;

                float xpos = (float) getXPos(rpos, dpos);
                float ypos = (float) getYPos(rpos, dpos);

                float[] vertices = genPolyVertices(rpos, dpos);

                if (r > 0 && r <= 6 && checkVisible((int) xpos, (int) ypos)) {
                    game.shapeRenderer.setColor(setColor(r));
                    game.shapeRenderer.triangle(vertices[0], vertices[1],
                            vertices[4], vertices[5],
                            vertices[2], vertices[3]);
                    game.shapeRenderer.triangle(vertices[0], vertices[1],
                            vertices[4], vertices[5],
                            vertices[6], vertices[7]);
                }
            }
        }
    }

    private float[] genPolyVertices(int rpos, int dpos) {
        float[] vertices = new float[8];

        vertices[0] = (float) getXPos(rpos, dpos);
        vertices[1] = (float) getYPos(rpos, dpos);

        vertices[2] = (float) getXPos(rpos + cellWidth, dpos);
        vertices[3] = (float) getYPos(rpos + cellWidth, dpos);

        vertices[4] = (float) getXPos(rpos + cellWidth, dpos + cellHeight);
        vertices[5] = (float) getYPos(rpos + cellWidth, dpos + cellHeight);

        vertices[6] = (float) getXPos(rpos, dpos + cellHeight);
        vertices[7] = (float) getYPos(rpos, dpos + cellHeight);

        return vertices;
    }

    /**
     * Gets the cartesian X coord from the polar coords r and d
     * @param r radial coord (degrees clockwise from north)
     * @param d distance coord (distance from center of circle)
     * @return cartesian x coord
     */
    private double getXPos(int r, int d) {
        double rad = r * Math.PI / 180;
        double xpos = d * Math.cos(rad);
        return xpos;
    }

    /**
     * Gets the cartesian Y coord from the polar coords r and d
     * @param r radial coord (degrees clockwise from north)
     * @param d distance coord (distance from center of circle)
     * @return cartesian Y coord
     */
    private double getYPos(int r, int d) {
        double rad = r * Math.PI / 180;
        double ypos = d * Math.sin(rad);
        return ypos;
    }

    private boolean checkVisible(int x, int y) {
        return camera.frustum.pointInFrustum(x, y, 0);
    }

    private Color setColor(int color) {
        switch(color) {
            case 0: {
                return Color.WHITE;
            }
            case 1: {
                return Color.RED;
            }
            case 2: {
                return Color.ORANGE;
            }
            case 3: {
                return Color.YELLOW;
            }
            case 4: {
                return Color.GREEN;
            }
            case 5: {
                return Color.BLUE;
            }
            case 6: {
                return Color.PURPLE;
            }
            default: {
                return Color.BLACK;
            }
        }
    }

    private void populateCellsRandomly() {
        Random rand = new Random();
        for(int i = 0; i < radarCells.length; i++) {
            for (int j = 0; j < radarCells[i].length; j++) {
                radarCells[i][j].setReflectivity(rand.nextInt(12));
            }
        }
    }
}
