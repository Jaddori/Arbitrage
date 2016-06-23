//
//  arbitrage.cpp
//  Arbitrage
//
//  Created by Niclas Olsson on 2016-06-14.
//  Copyright © 2016 SpaceCat. All rights reserved.
//

#include "arbitrage.h"

int main( int argc, char *argv[] )
{
	if( SDL_Init( SDL_INIT_EVERYTHING ) < 0 )
		return -1;
	
	SDL_GL_SetAttribute( SDL_GL_CONTEXT_MAJOR_VERSION, 3 );
	SDL_GL_SetAttribute( SDL_GL_CONTEXT_MINOR_VERSION, 2 );
	SDL_GL_SetAttribute( SDL_GL_CONTEXT_PROFILE_MASK, SDL_GL_CONTEXT_PROFILE_CORE );
	
	SDL_Window *window = SDL_CreateWindow( "Arbitrage", SDL_WINDOWPOS_CENTERED, SDL_WINDOWPOS_CENTERED, 640, 480, SDL_WINDOW_OPENGL | SDL_WINDOW_SHOWN );
	if( window )
	{
		SDL_GLContext renderContext = SDL_GL_CreateContext( window );
		if( renderContext )
		{
#ifdef WIN32
			glewExperimental = GL_TRUE;
			if( glewInit() != GLEW_OK )
				return -1;
#endif

			SDL_GL_SetSwapInterval( 1 );
			
			const char *vsource = "#version 330\n"
			"layout(location=0) in vec2 PositionIn;"
			"out vec2 UV0;"
			"uniform mat4 Model;"
			"uniform mat4 View;"
			"uniform mat4 Projection;"
			"void main() { gl_Position = Projection * View * Model * vec4( PositionIn, 0.0, 1.0 ); UV0 = PositionIn; }";
			
			const char *fsource = "#version 330\n"
			"in vec2 UV0;"
			"out vec4 FragColor;"
			"uniform sampler2D DiffuseMap;"
			"void main() { FragColor = texture( DiffuseMap, UV0 ); }";
			
			GLuint shaderProgram = LoadProgram( vsource, 0, fsource );
			
			glUseProgram( shaderProgram );
			GLuint modelUniformLocation = glGetUniformLocation( shaderProgram, "Model" );
			GLuint viewUniformLocation = glGetUniformLocation( shaderProgram, "View" );
			GLuint projectionUniformLocation = glGetUniformLocation( shaderProgram, "Projection" );
			
			GLuint quad = CreateQuad();
			
			Texture texture;
			LoadTexture( "./textures/face_norm.png", &texture );
			
			glm::mat4 modelMatrix = glm::scale( glm::mat4(), glm::vec3( 128.0f, 128.0f, 1.0f ) );
			
			glm::mat4 viewMatrix;
			glm::mat4 projectionMatrix = glm::ortho( 0.0f, 640.0f, 480.0f, 0.0f, -1.0f, 1.0f );
			
			bool running = true;
			while( running )
			{
				SDL_Event e;
				while( SDL_PollEvent( &e ) )
				{
					if( e.type == SDL_QUIT )
						running = false;
					else if( e.type == SDL_KEYDOWN )
					{
						if( e.key.keysym.sym == SDLK_ESCAPE )
							running = false;
					}
				}
				
				// update
				
				
				// render
				glClearColor( 1.0f, 0.0f, 0.0f, 1.0f );
				glClear( GL_COLOR_BUFFER_BIT );
				
				glUseProgram( shaderProgram );
				
				glBindTexture( GL_TEXTURE_2D, texture.id );
				
				glUniformMatrix4fv( modelUniformLocation, 1, GL_FALSE, &modelMatrix[0][0] );
				glUniformMatrix4fv( viewUniformLocation, 1, GL_FALSE, &viewMatrix[0][0] );
				glUniformMatrix4fv( projectionUniformLocation, 1, GL_FALSE, &projectionMatrix[0][0] );
				
				glBindVertexArray( quad );
				glDrawArrays( GL_TRIANGLES, 0, 12 );
				
				SDL_GL_SwapWindow( window );
			}
		}
	}
	
	return 0;
}