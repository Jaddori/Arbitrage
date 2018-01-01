package com.spacecat.arbitrage;

import android.content.Context;
import android.opengl.GLES20;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Shader
{
	private int program;

	public Shader()
	{
	}

	public void load( Context context, int vertex, int fragment )
	{
		program = GLES20.glCreateProgram();

		int vertexShader = -1, fragmentShader = -1;

		if( vertex != 0 )
		{
			vertexShader = loadSource( context, vertex, GLES20.GL_VERTEX_SHADER );
			assert ( vertexShader >= 0 );

			GLES20.glAttachShader( program, vertexShader );
		}
		if( fragment != 0 )
		{
			fragmentShader = loadSource( context, fragment, GLES20.GL_FRAGMENT_SHADER );
			assert ( fragmentShader >= 0 );

			GLES20.glAttachShader( program, fragmentShader );
		}

		GLES20.glLinkProgram( program  );

		// @TODO: Check if linking was successful

		if( vertexShader >= 0 )
			GLES20.glDeleteShader( vertexShader );
		if( fragmentShader >= 0 )
			GLES20.glDeleteShader( fragmentShader );
	}

	private int loadSource( Context context, int id, int shaderType )
	{
		int result = -1;

		try
		{
			InputStream stream = context.getResources().openRawResource( id );
			BufferedInputStream bufferedStream = new BufferedInputStream( stream );

			StringBuilder shaderSource = new StringBuilder();
			while( bufferedStream.available() > 0 )
			{
				shaderSource.append( (char)bufferedStream.read() );
			}

			bufferedStream.close();
			stream.close();

			result = GLES20.glCreateShader( shaderType );
			GLES20.glShaderSource( result, shaderSource.toString() );
			GLES20.glCompileShader( result );

			// @TODO: Check compilation status
		}
		catch( IOException e )
		{

		}

		return result;
	}
}
