package com.stormwarn.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.stormwarn.game.graphics.MyCamera;
import com.stormwarn.game.main.StormWarn;
import com.stormwarn.game.radar.Radar;

import java.util.Random;

public class GameScreen implements Screen {

    final StormWarn game;

    private MyCamera camera;

    private Radar radar;

    private int[][] radarCells;

    private PolygonRegion[][] radarPolygons;

    private int cellWidth;

    private int cellHeight;

    private int deadZoneOffset;

    public GameScreen(StormWarn game) {
        this.game = game;

        camera = MyCamera.getInstance();
        camera.setToOrtho(false, 1600, 960);

        radar = new Radar();
        cellWidth = 1;
        cellHeight = 2;
        deadZoneOffset = 5 * cellHeight;

        radarCells = new int[radar.getRadialAmount()][radar.getDistance()];
        populateCellsRandomly();
        radarPolygons = new PolygonRegion[radar.getRadialAmount()][radar.getDistance()];
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        camera.setXpos(0);
        camera.setYpos(0);
        camera.setZpos(0);
        camera.setZoom(1f);
        camera.update();

        //game logic here
        populateCellsRandomly();

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


    private void populateCellsRandomly() {
        Random rand = new Random();
        for(int i = 0; i < radarCells.length; i++) {
            for (int j = 0; j < radarCells[i].length; j++) {
                radarCells[i][j] = rand.nextInt(12);
            }
        }
    }


    private void drawRadar() {
        int centerX = Gdx.graphics.getWidth() / 2;
        int centerY = Gdx.graphics.getHeight() / 2;

        for (int i = 0; i < radarCells.length; i++) {
            for (int j = 0; j < radarCells[i].length; j++) {
                int r = radarCells[i][j];
                int rpos = i;
                int dpos = (j * cellHeight) + deadZoneOffset;

                float[] vertices = new float[8];

                float xpos = (float) getXPos(rpos, dpos);
                float ypos = (float) getYPos(rpos, dpos);

                vertices[0] = xpos;
                vertices[1] = ypos;

                vertices[2] = (float) getXPos(rpos + cellWidth, dpos);
                vertices[3] = (float) getYPos(rpos + cellWidth, dpos);

                vertices[4] = (float) getXPos(rpos + cellWidth, dpos + cellHeight);
                vertices[5] = (float) getYPos(rpos + cellWidth, dpos + cellHeight);

                vertices[6] = (float) getXPos(rpos, dpos + cellHeight);
                vertices[7] = (float) getYPos(rpos, dpos + cellHeight);

                if (r > 0 && r <= 6 && checkVisible((int) xpos, (int) ypos)) {
                    game.shapeRenderer.setColor(setColor(r));
                    game.shapeRenderer.triangle(vertices[0], vertices[1], vertices[4], vertices[5], vertices[2], vertices[3]);
                    game.shapeRenderer.triangle(vertices[0], vertices[1], vertices[4], vertices[5], vertices[6], vertices[7]);
                }
            }
        }
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
}
