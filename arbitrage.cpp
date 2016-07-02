//
//  arbitrage.cpp
//  Arbitrage
//
//  Created by Niclas Olsson on 2016-06-14.
//  Copyright © 2016 SpaceCat. All rights reserved.
//

#include "arbitrage.h"

extern const char *g_vertexShaderSource;
extern const char *g_fragmentShaderSource;
extern GLuint g_modelUniformLocation;
extern GLuint g_viewUniformLocation;
extern GLuint g_projectionUniformLocation;
extern GLuint g_colorUniformLocation;
extern GLuint g_uvOffsetUniformLocation;

int main( int argc, char *argv[] )
{
	if( SDL_Init( SDL_INIT_EVERYTHING ) < 0 )
		return -1;
	
	int a = SDLK_a;
	int b = 'a';
	
	a = SDLK_f;
	b = 'f';
	
	a = SDLK_ESCAPE;
	
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
			
			glEnable( GL_BLEND );
			glBlendFunc( GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA );
			
			/*const char *vsource = "#version 330\n"
			"layout(location=0) in vec2 PositionIn;"
			"out vec2 UV0;"
			"uniform mat4 Model;"
			"uniform mat4 View;"
			"uniform mat4 Projection;"
			"uniform vec4 UVOffset;"
			"void main() { gl_Position = Projection * View * Model * vec4( PositionIn, 0.0, 1.0 );"
			"UV0 = ( PositionIn * UVOffset.zw ) + UVOffset.xy; }";
			
			const char *fsource = "#version 330\n"
			"in vec2 UV0;"
			"out vec4 FragColor;"
			"uniform sampler2D DiffuseMap;"
			"uniform vec4 Color;"
			"void main() { FragColor = texture( DiffuseMap, UV0 ) * Color; }";*/
			
			GLuint shaderProgram = LoadProgram( g_vertexShaderSource, 0, g_fragmentShaderSource );
			glUseProgram( shaderProgram );
			
			g_modelUniformLocation = glGetUniformLocation( shaderProgram, "Model" );
			g_viewUniformLocation = glGetUniformLocation( shaderProgram, "View" );
			g_projectionUniformLocation = glGetUniformLocation( shaderProgram, "Projection" );
			g_colorUniformLocation = glGetUniformLocation( shaderProgram, "Color" );
			g_uvOffsetUniformLocation = glGetUniformLocation( shaderProgram, "UVOffset" );
			
			/*std::cout << "Model Location: " << g_modelUniformLocation << std::endl;
			std::cout << "View location: " << g_viewUniformLocation << std::endl;
			std::cout << "Projection Location: " << g_projectionUniformLocation << std::endl;*/
			
			GLuint quad = CreateQuad();
			glBindVertexArray( quad );
			
			Texture texture;
			LoadTexture( "./textures/face_norm.png", &texture );
			glBindTexture( GL_TEXTURE_2D, texture.id );

			glm::mat4 projectionMatrix = glm::ortho( 0.0f, 640.0f, 480.0f, 0.0f, -1.0f, 1.0f );
			glUniformMatrix4fv( g_projectionUniformLocation, 1, GL_FALSE, &projectionMatrix[0][0] );
			WorldMatrix( 0.0f, 0.0f, 0.0f, 0.0f );
			ViewMatrix( 0.0f, 0.0f );
			Color( 1.0f, 1.0f, 1.0f, 1.0f );
			UVOffset( 0.0f, 0.0f, 1.0f, 1.0f );

			ScriptHandle mainHandle;
			
			lua_State* lua = CreateLua();
			RunScript( lua, "./scripts/main.lua", &mainHandle );
			
			bool running = true, canUpdate = true, canRender = true;
			while( running )
			{
				SyncInput();
				
				SDL_Event e;
				while( SDL_PollEvent( &e ) )
				{
					if( e.type == SDL_QUIT )
						running = false;
					else
						GetInput( &e );
				}
				
				if( KeyDown( SDLK_ESCAPE ) )
					running = false;
				
				// update
				HotloadScripts( lua, &mainHandle, 1 );
				
				if( canUpdate && mainHandle.valid )
				{
					lua_getglobal( lua, "MainUpdate" );
					if( lua_pcall( lua, 0, 0, 0 ) != 0 )
					{
						std::cout << "Lua error: " << lua_tostring( lua, -1 ) << std::endl;
						canUpdate = false;
					}
				}
				
				// render
				glClearColor( 1.0f, 0.0f, 0.0f, 1.0f );
				glClear( GL_COLOR_BUFFER_BIT );
				
				glBindTexture( GL_TEXTURE_2D, texture.id );

				if( canRender && mainHandle.valid )
				{
					lua_getglobal( lua, "MainRender" );
					if( lua_pcall( lua, 0, 0, 0 ) != 0 )
					{
						std::cout << "Lua error: " << lua_tostring( lua, -1 ) << std::endl;
						canRender = false;
					}
				}
				
				SDL_GL_SwapWindow( window );
			}

			DestroyLua( lua );

			SDL_GL_DeleteContext( renderContext );
		}

		SDL_DestroyWindow( window );
	}

	SDL_Quit();
	
	return 0;
}