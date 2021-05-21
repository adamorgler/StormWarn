package com.stormwarn.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.stormwarn.game.main.StormWarn;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "StormWarn";
		config.width = 800;
		config.height = 480;
		new LwjglApplication(new StormWarn(), config);
	}
}
