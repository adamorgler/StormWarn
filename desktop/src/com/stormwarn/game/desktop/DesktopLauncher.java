package com.stormwarn.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.stormwarn.game.main.StormWarn;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "StormWarn";
		config.width = 1600;
		config.height = 960;
		new LwjglApplication(new StormWarn(), config);
	}
}
