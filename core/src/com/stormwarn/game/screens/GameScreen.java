package com.stormwarn.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.stormwarn.game.main.StormWarn;
import com.stormwarn.game.radar.Radar;

import java.util.Random;

public class GameScreen implements Screen {

    final StormWarn game;

    private OrthographicCamera camera;

    private Radar radar;

    private int[][] radarCells;

    private PolygonRegion[][] radarPolygons;

    private int cellWidth;

    private int cellHeight;

    private int deadZoneOffset;

    public GameScreen(StormWarn game) {
        this.game = game;

        camera = new OrthographicCamera();
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

        camera.position.set(0, 0, 0);
        camera.zoom = 1f;
        camera.update();

        //game logic here
        populateCellsRandomly();

        //filled in shapes
        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        drawRadar();
        game.shapeRenderer.end();

        //outlines
        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        game.shapeRenderer.setColor(Color.LIGHT_GRAY);
        game.shapeRenderer.circle(Gdx.graphics.getWidth() / 2,
                Gdx.graphics.getHeight() / 2,
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

                vertices[0] = xpos + centerX;
                vertices[1] = ypos + centerY;

                vertices[2] = (float) getXPos(rpos + cellWidth, dpos) + centerX;
                vertices[3] = (float) getYPos(rpos + cellWidth, dpos) + centerY;

                vertices[4] = (float) getXPos(rpos + cellWidth, dpos + cellHeight) + centerX;
                vertices[5] = (float) getYPos(rpos + cellWidth, dpos + cellHeight) + centerY;

                vertices[6] = (float) getXPos(rpos, dpos + cellHeight) + centerX;
                vertices[7] = (float) getYPos(rpos, dpos + cellHeight) + centerY;

                if (r > 0 && r <= 6 && checkVisible((int) xpos, (int) ypos)) {
                    game.shapeRenderer.setColor(setColor(r));
                    game.shapeRenderer.triangle(vertices[0], vertices[1], vertices[4], vertices[5], vertices[2], vertices[3]);
                    game.shapeRenderer.triangle(vertices[0], vertices[1], vertices[4], vertices[5], vertices[6], vertices[7]);
                }
            }
        }
    }

    private void setRadarCells() {

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

    private short[] defaultTriangles() {
        return new short[]{
                0, 1, 2,
                0, 2, 3
        };
    }

    private boolean checkVisible(int x, int y) {
        return camera.frustum.pointInFrustum(x, y, 0);
    }

    private Texture solidTexture(int color) {
        Pixmap pix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        switch(color) {
            case 0: {
                pix.setColor(Color.WHITE);
                break;
            }
            case 1: {
                pix.setColor(Color.RED);
                break;
            }
            case 2: {
                pix.setColor(Color.ORANGE);
                break;
            }
            case 3: {
                pix.setColor(Color.YELLOW);
                break;
            }
            case 4: {
                pix.setColor(Color.GREEN);
                break;
            }
            case 5: {
                pix.setColor(Color.BLUE);
                break;
            }
            case 6: {
                pix.setColor(Color.PURPLE);
                break;
            } default: {
                pix.setColor(Color.BLACK);
                break;
            }
        }
        pix.fill();
        return new Texture(pix);
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
