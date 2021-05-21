package com.stormwarn.game.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.stormwarn.game.screens.MainMenuScreen;

public class StormWarn extends Game {

    public SpriteBatch batch;
    public PolygonSpriteBatch polyBatch;
    public BitmapFont font;

    @Override
    public void create() {
        batch = new SpriteBatch();
        polyBatch = new PolygonSpriteBatch();
        font = new BitmapFont();
        this.setScreen(new MainMenuScreen(this));
    }

    public void render() {
        super.render();
    }

    public void dispose() {
        batch.dispose();
        polyBatch.dispose();
        font.dispose();
    }

}
