package com.spacecat.arbitrage;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * Created by Nix on 2018-01-01.
 */

public class MainGLSurfaceView extends GLSurfaceView
{
	private final MainGLRenderer glRenderer;

	public MainGLSurfaceView( Context context )
	{
		super( context );

		setEGLContextClientVersion( 2 );

		glRenderer = new MainGLRenderer();

		setRenderer( glRenderer );
	}
}
