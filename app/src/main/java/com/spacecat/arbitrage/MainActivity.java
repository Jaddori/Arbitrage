package com.spacecat.arbitrage;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class MainActivity extends Activity
{
	private GLSurfaceView glView;

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );

		glView = new MainGLSurfaceView( this );
		setContentView( glView );
	}
}
