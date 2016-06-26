//
//  rendering.cpp
//  Arbitrage
//
//  Created by Niclas Olsson on 2016-06-23.
//  Copyright © 2016 SpaceCat. All rights reserved.
//

#include "rendering.h"

GLuint LoadShader( const char *source, GLenum type )
{
	GLuint shader = glCreateShader( type );
	if( shader )
	{
		glShaderSource( shader, 1, &source, 0 );
		glCompileShader( shader );
		
		GLint success;
		glGetShaderiv( shader, GL_COMPILE_STATUS, &success );
		if( success == GL_FALSE )
		{
			int size = 1024;
			char buf[1024] = {};
			glGetShaderInfoLog( shader, 1024, &size, buf );
			
			std::cout << "Shader error:" << std::endl << buf << std::endl;
		}
	}
			
	return shader;
}

GLuint LoadProgram( const char *vsource, const char *gsource, const char *fsource )
{
	GLuint program = glCreateProgram();
	
	if( program )
	{
		GLuint vshader = 0, gshader = 0, fshader = 0;
		if( vsource )
		{
			vshader = LoadShader( vsource, GL_VERTEX_SHADER );
			if( vshader )
				glAttachShader( program, vshader );
		}
		if( gsource )
		{
			gshader = LoadShader( gsource, GL_GEOMETRY_SHADER );
			if( gshader )
				glAttachShader( program, gshader );
		}
		if( fsource )
		{
			fshader = LoadShader( fsource, GL_FRAGMENT_SHADER );
			if( fshader )
				glAttachShader( program, fshader );
		}
		
		glLinkProgram( program );
		
		GLint success;
		glGetProgramiv( program, GL_LINK_STATUS, &success );
		if( success == GL_FALSE )
		{
			int size = 1024;
			char buf[1024] = {};
			glGetProgramInfoLog( program, 1024, &size, buf );
			
			std::cout << "Shader program error:" << std::endl << buf << std::endl;
		}
	}
	
	return program;
}

GLuint CreateQuad()
{
	GLuint result = 0;
	
	GLfloat vdata[] =
	{
		0, 0,
		0, 1,
		1, 1,
		
		0, 0,
		1, 1,
		1, 0
	};
	
	glGenVertexArrays( 1, &result );
	glBindVertexArray( result );
	
	glEnableVertexAttribArray( 0 );
	
	GLuint buffer;
	glGenBuffers( 1, &buffer );
	glBindBuffer( GL_ARRAY_BUFFER, buffer );
	glBufferData( GL_ARRAY_BUFFER, sizeof(GLfloat)*12, vdata, GL_STATIC_DRAW );
	
	glVertexAttribPointer( 0, 2, GL_FLOAT, GL_TRUE, 0, 0 );
	
	glBindVertexArray( 0 );
	
	return result;
}

bool LoadTexture( const char *filename, Texture *texture )
{
	bool result = false;
	
	SDL_Surface *img = IMG_Load( filename );
	if( img )
	{
#ifdef _WIN32
		GLenum format = GL_RGBA;
		if( img->format->BytesPerPixel == 3 )
			format = GL_RGB;
#else
		GLenum format = GL_BGRA;
		if( img->format->BytesPerPixel == 3 )
			format = GL_BGR;
#endif
		
		glGenTextures( 1, &texture->id );
		glBindTexture( GL_TEXTURE_2D, texture->id );
		
		glTexParameteri( GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST );
		glTexParameteri( GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST );
		glTexImage2D( GL_TEXTURE_2D, 0, GL_RGBA, img->w, img->h, 0, format, GL_UNSIGNED_BYTE, img->pixels );
		
		texture->width = img->w;
		texture->height = img->h;
		
		glBindTexture( GL_TEXTURE_2D, 0 );
		SDL_FreeSurface( img );
		result = true;
	}
	
	return result;
}

void WorldMatrix( float x, float y, float width, float height, float rotation )
{
	glm::mat4 world = glm::rotate( glm::scale( glm::translate( glm::mat4(), glm::vec3( x, y, 0.0f ) ), glm::vec3( width, height, 1.0f ) ), rotation, glm::vec3( 0.0f, 0.0f, 1.0f ) );
	glUniformMatrix4fv( 0, 1, GL_FALSE, &world[0][0] );
}

void ViewMatrix( float x, float y )
{
	glm::mat4 view = glm::translate( glm::mat4(), glm::vec3( -x, -y, 0.0f ) );
	glUniformMatrix4fv( 1, 1, GL_FALSE, &view[0][0] );
}

void RenderQuad()
{
	glDrawArrays( GL_TRIANGLES, 0, 12 );
}