package com.stormwarn.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.stormwarn.game.controllers.EnvController;
import com.stormwarn.game.graphics.MyCamera;
import com.stormwarn.game.main.StormWarn;
import com.stormwarn.game.radar.Radar;
import com.stormwarn.game.radar.RadarCell;
import com.stormwarn.game.storm.partitions.particles.Precipitation;

import java.util.Random;

public class GameScreen implements Screen {

    final StormWarn game;

    final EnvController envController;

    private MyCamera camera;

    private Radar radar;

    private RadarCell[][] radarCells;

    private int cellWidth;

    private int cellHeight;

    private int deadZoneRadius;

    private int layerView;

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
        deadZoneRadius = 7;
        layerView = 1;


        // GAME LOGIC INIT
        radar.setRadar(envController.getParticles());
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        camera.update();

        //GAME LOGIC START
        radar.clearRadar();
        envController.clearParticles();
        envController.genParticlesInCircle(-50, 50, 20, 10);
        envController.genParticlesInCircle(50, 50, 20, 7);
        envController.genParticlesInCircle(50, -50, 20, 4);
        envController.genParticlesInCircle(-50, -50, 20, 1);
        envController.genParticlesInCircle(0, 0, 40, 5);
        radar.setRadar(envController.getParticles());
        //GAME LOGIC END

        // GRAPHICS START
        game.shapeRenderer.setProjectionMatrix(camera.combined);
        //filled in shapes
        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        drawRadar();
        game.shapeRenderer.setColor(Color.BLACK);
        game.shapeRenderer.circle(0,0, deadZoneRadius * cellHeight);
        game.shapeRenderer.end();

        //outlines
        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        game.shapeRenderer.setColor(Color.LIGHT_GRAY);
        game.shapeRenderer.circle(0,
                0,
                radar.getDistance() * cellHeight);
        game.shapeRenderer.end();

        //text
        game.batch.begin();
        game.font.draw(game.batch, "Layer " + Integer.toString(layerView), 50, 50);
        game.batch.end();
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
        radarCells = radar.getRadarCells();
        for (int i = 0; i < radarCells.length; i++) {
            for (int j = 0; j < radarCells[i].length; j++) {
                int r = radarCells[i][j].getReflectivity();
                int rpos = i + 180;
                int dpos = (j * cellHeight);

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
        for (int i = 0; i < 100000; i++) {
            int x = rand.nextInt(500) - 250;
            int y = rand.nextInt(500) - 250;
            Precipitation p = new Precipitation(x, y, 0, 0);
            p.setReflectivity(rand.nextInt(12));
            envController.addParticle(p);
        }
    }

    private void upLayerView() {
        layerView++;
    }

    private void downLayerView() {
        layerView--;
    }

    private void checkLayerBounds() {
        if (layerView > 15) {
            layerView = 15;
        } else if (layerView < 1) {
            layerView = 1;
        }
    }
}
