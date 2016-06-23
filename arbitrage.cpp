//
//  arbitrage.cpp
//  Arbitrage
//
//  Created by Niclas Olsson on 2016-06-14.
//  Copyright © 2016 SpaceCat. All rights reserved.
//

#include <SDL2/SDL.h>
#include <OpenGL/gl3.h>
#include "arbitrage.h"

int main( int argc, char *argv[] )
{
	if( SDL_Init( SDL_INIT_EVERYTHING ) < 0 )
		return -1;
	
	SDL_GL_SetAttribute( SDL_GL_CONTEXT_MAJOR_VERSION, 3 );
	SDL_GL_SetAttribute( SDL_GL_CONTEXT_MINOR_VERSION, 2 );
	SDL_GL_SetAttribute( SDL_GL_CONTEXT_PROFILE_MASK, SDL_GL_CONTEXT_PROFILE_CORE );
	
	SDL_Window *window = SDL_CreateWindow( "Arbitrage", 0, 0, 640, 480, SDL_WINDOW_OPENGL | SDL_WINDOW_SHOWN );
	if( window )
	{
		SDL_GLContext renderContext = SDL_GL_CreateContext( window );
		if( renderContext )
		{
			SDL_GL_SetSwapInterval( 1 );
			
			const char *vsource = "#version 330\n"
			"layout(location=0) in vec2 PositionIn;"
			"out vec2 UV0;"
			"void main() { gl_Position = vec4( PositionIn, 1.0, 1.0 ); UV0 = PositionIn; }";
			
			const char *fsource = "#version 330\n"
			"in vec2 UV0;"
			"out vec4 FragColor;"
			"uniform sampler2D DiffuseMap;"
			"void main() { FragColor = texture( DiffuseMap, UV0 ); }";
			
			GLuint shaderProgram = LoadProgram( vsource, 0, fsource );
			GLuint quad = CreateQuad();
			
			Texture texture;
			LoadTexture( "./textures/face.png", &texture );
			
			bool running = true;
			while( running )
			{
				SDL_Event e;
				while( SDL_PollEvent( &e ) )
				{
					if( e.type == SDL_QUIT )
						running = false;
				}
				
				// update
				
				
				// render
				glClearColor( 1.0f, 0.0f, 0.0f, 1.0f );
				glClear( GL_COLOR_BUFFER_BIT );
				
				glUseProgram( shaderProgram );
				
				glBindTexture( GL_TEXTURE_2D, texture.id );
				
				glBindVertexArray( quad );
				glDrawArrays( GL_TRIANGLES, 0, 12 );
				
				SDL_GL_SwapWindow( window );
			}
		}
	}
	
	return 0;
}