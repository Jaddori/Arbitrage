package com.spacecat.arbitrage;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Display;

public class MainActivity extends Activity
{
	GameView gameView;

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );

		Display display = getWindowManager().getDefaultDisplay();
		android.graphics.Point windowSize = new android.graphics.Point();
		display.getSize( windowSize );
		Utils.windowSize = new Point( windowSize.x, windowSize.y );

		gameView = new GameView( this );
		gameView.initialize();

		setContentView( gameView );
	}
}
