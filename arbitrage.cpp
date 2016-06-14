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
	SDL_Window *window = SDL_CreateWindow( "Arbitrage", 0, 0, 640, 480, SDL_WINDOW_OPENGL | SDL_WINDOW_SHOWN );
	if( window )
	{
		SDL_GLContext renderContext = SDL_GL_CreateContext( window );
		if( renderContext )
		{
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
				SDL_GL_SwapWindow( window );
			}
		}
	}
	
	return 0;
}