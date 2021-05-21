package com.stormwarn.game.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.utils.ScreenUtils;
import com.stormwarn.game.main.StormWarn;
import com.stormwarn.game.radar.Radar;

import java.util.Random;

public class GameScreen implements Screen {

    final StormWarn game;

    private OrthographicCamera camera;

    private Radar radar;

    private int[][] radarCells;

    private Polygon[][] radarPolygons;

    private int cellWidth;

    private int cellHeight;

    public GameScreen(StormWarn game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        radar = new Radar();
        cellWidth = 1;
        cellHeight = 2;

        radarCells = new int[radar.getRadialAmount()][radar.getDistance()];
        populateCellsRandomly();
        radarPolygons = new Polygon[radar.getRadialAmount()][radar.getDistance()];
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 0, 0, 1);

        camera.update();

        game.batch.begin();
        drawRadar();
        game.batch.end();
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
    }

    private int rand(int bound) {
        Random rand = new Random();
        return rand.nextInt(bound);
    }

    private void populateCellsRandomly() {
        for(int i = 0; i < radarCells.length; i++) {

        }
    }

    private void drawRadar() {
        int centerX = 800 / 2;
        int centerY = 480 / 2;
        for (int i = 0; i < radarCells.length; i++) {
            for (int j = 0; j < radarCells[i].length; j++) {
                int r = radarCells[i][j];
                int rpos = i * cellWidth;
                int dpos = j * cellHeight;

                float[] vertices = new float[8];
                vertices[0] = (float) getXPos(rpos, dpos);
                vertices[1] = (float) getYPos(rpos, dpos);

                vertices[2] = (float) getXPos(rpos + cellWidth, dpos);
                vertices[3] = (float) getYPos(rpos + cellWidth, dpos);

                vertices[4] = (float) getXPos(rpos + cellWidth, dpos + cellHeight);
                vertices[5] = (float) getYPos(rpos + cellWidth, dpos + cellHeight);

                vertices[6] = (float) getXPos(rpos, dpos + cellHeight);
                vertices[7] = (float) getYPos(rpos, dpos + cellHeight);


                float xpos = (float) (getXPos(rpos, dpos) + centerX);
                float ypos = (float) (getYPos(rpos, dpos) + centerY);
            }
        }
    }

    private double getXPos(int r, int d) {
        double rad = r * Math.PI / 180;
        double xpos = d * Math.cos(rad);
        return xpos;
    }

    private double getYPos(int r, int d) {
        double rad = r * Math.PI / 180;
        double ypos = d * Math.sin(rad);
        return ypos;
    }



}
