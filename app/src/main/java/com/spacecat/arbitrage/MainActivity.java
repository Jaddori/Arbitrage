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

		Shader shader = new Shader();
		shader.load( this, R.raw.basic_vertex, R.raw.basic_fragment );
	}
}
