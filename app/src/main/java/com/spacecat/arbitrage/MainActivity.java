package com.spacecat.arbitrage;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class MainActivity extends Activity
{
	GameView gameView;

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );

		gameView = new GameView( this );
		gameView.initialize();

		setContentView( gameView );
	}
}
