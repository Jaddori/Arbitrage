//
//  rendering.cpp
//  Arbitrage
//
//  Created by Niclas Olsson on 2016-06-23.
//  Copyright © 2016 SpaceCat. All rights reserved.
//

#include "rendering.h"

const char *g_vertexShaderSource = "#version 330\n"
"layout(location=0) in vec2 PositionIn;"
"out vec2 UV0;"
"uniform mat4 Model;"
"uniform mat4 View;"
"uniform mat4 Projection;"
"uniform vec4 UVOffset;"
"void main() { gl_Position = Projection * View * Model * vec4( PositionIn, 0.0, 1.0 );"
"UV0 = ( PositionIn * UVOffset.zw ) + UVOffset.xy; }";

const char *g_fragmentShaderSource = "#version 330\n"
"in vec2 UV0;"
"out vec4 FragColor;"
"uniform sampler2D DiffuseMap;"
"uniform vec4 Color;"
"void main() { FragColor = texture( DiffuseMap, UV0 ) * Color; }";

GLuint g_modelUniformLocation = 0;
GLuint g_viewUniformLocation = 0;
GLuint g_projectionUniformLocation = 0;
GLuint g_colorUniformLocation = 0;
GLuint g_uvOffsetUniformLocation = 0;

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

bool LoadFont( const char *imgName, const char *infoName, Font *font )
{
	bool result = false;
	
	if( LoadTexture( imgName, &font->texture ) )
	{
		FILE *file = fopen( infoName, "rb" );
		if( file )
		{
			//fread( &font->height, 1, 1, file );
			fread( font->widths, 1, FONT_RANGE, file );
			fclose( file );
			
			font->height = 24;
			
			float x = 0.0f, y = 0.0f;
			for( int i=0; i<FONT_RANGE; i++ )
			{
				if( x + font->widths[i] + 1 > font->texture.width )
				{
					x = 0.0f;
					y += font->height + 1;
				}
				
				font->xoffsets[i] = x;
				font->yoffsets[i] = y;
				x += font->widths[i] + 1;
			}
		}
	}
	
	return result;
}

void WorldMatrix( float x, float y, float z, float width, float height )
{
	//glm::mat4  world = glm::scale( glm::translate( glm::mat4(), glm::vec3( x, y, 0.0f ) ), glm::vec3( width, height, 1.0f ) );
	//glUniformMatrix4fv( g_modelUniformLocation, 1, GL_FALSE, &world[0][0] );
	
	float world[16] =
	{
		width, 0, 0, 0,
		0, height, 0, 0,
		0, 0, 1, 0,
		x, y, z, 1
	};
	
	glUniformMatrix4fv( g_modelUniformLocation, 1, GL_FALSE, world );
}

void ViewMatrix( float x, float y )
{
	//glm::mat4 view = glm::translate( glm::mat4(), glm::vec3( -x, -y, 0.0f ) );
	//glUniformMatrix4fv( g_viewUniformLocation, 1, GL_FALSE, &view[0][0] );
	
	float view[16]
	{
		1, 0, 0, 0,
		0, 1, 0, 0,
		0, 0, 1, 0,
		-x, -y, 0, 1
	};
	
	glUniformMatrix4fv( g_viewUniformLocation, 1, GL_FALSE, view );
}

void Color( float r, float g, float b, float a )
{
	glUniform4f( g_colorUniformLocation, r, g, b, a );
}

void UVOffset( float x, float y, float width, float height )
{
	glUniform4f( g_uvOffsetUniformLocation, x, y, width, height );
}

void RenderQuad()
{
	glDrawArrays( GL_TRIANGLES, 0, 12 );
}

void Render( float x, float y, float z, float width, float height )
{
	WorldMatrix( x, y, z, width, height );
	RenderQuad();
}

void RenderText( Font *font, float x, float y, const char *text, int len )
{
	if( len <= 0 )
		len = strlen( text );
	
	float offset = 0.0f;
	for( int i=0; i<len; i++ )
	{
		char c = text[i];
		if( c == '\n' )
		{
			offset = 0.0f;
			y += font->height;
		}
		else if( c >= 32 && c <= 127 )
		{
			int index = (int)c - 32;
		
			float height = font->height;
			float width = font->widths[index];
			WorldMatrix( x+offset, y, 0.0f, width, height );
		
			offset += width + 1;
		
			float xoffset = (float)font->xoffsets[index] / (float)font->texture.width;
			float yoffset = (float)font->yoffsets[index] / (float)font->texture.height;
		
			width /= font->texture.width;
			height /= font->texture.height;
		
			UVOffset( xoffset, yoffset, width, height );
		
			RenderQuad();
		}
	}
}