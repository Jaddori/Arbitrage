//
//  input.cpp
//  Arbitrage
//
//  Created by Niclas Olsson on 2016-06-29.
//  Copyright © 2016 SpaceCat. All rights reserved.
//

#include "input.h"

Input g_input = {};

void SyncInput()
{
	memcpy( g_input.prevKeys, g_input.keys, INPUT_MAX_KEYS );
	memcpy( g_input.prevButtons, g_input.buttons, INPUT_MAX_BUTTONS );
	
	g_input.prevMouseX = g_input.mouseX;
	g_input.prevMouseY = g_input.mouseY;
	g_input.prevMouseWheel = g_input.mouseWheel;
	
	for( int i=0; i<g_input.ntext; i++ )
		g_input.text[i] = 0;
	g_input.ntext = 0;
}

bool GetInput( SDL_Event *e )
{
	bool result = true;
	
	switch( e->type )
	{
		case SDL_KEYDOWN:
			// TODO: Fix index for modifier keys
			if( e->key.keysym.sym < INPUT_MAX_KEYS )
			{
				g_input.keys[e->key.keysym.sym] = 1;
			
				/*if( g_input.ntext < INPUT_MAX_TEXT )
				{
					int i = e->key.keysym.sym;
					if( i >= 32 && i < 127 )
						g_input.text[g_input.ntext++] = (char)i;
				}*/
			}
			break;
			
		case SDL_KEYUP:
			if( e->key.keysym.sym < INPUT_MAX_KEYS )
				g_input.keys[e->key.keysym.sym] = 0;
			break;
			
		case SDL_TEXTINPUT:
			{
				int len = (int)strlen( e->text.text );
				if( g_input.ntext < INPUT_MAX_TEXT - len )
				{
					strcat( g_input.text, e->text.text);
					g_input.ntext += len;
				}
			} break;
			
		case SDL_MOUSEMOTION:
			g_input.mouseX = e->motion.x;
			g_input.mouseY = e->motion.y;
			break;
			
		case SDL_MOUSEBUTTONDOWN:
			g_input.buttons[e->button.button] = 1;
			break;
			
		case SDL_MOUSEBUTTONUP:
			g_input.buttons[e->button.button] = 0;
			break;
			
		case SDL_MOUSEWHEEL:
			g_input.mouseWheel = e->wheel.y;
			break;
			
		default:
			result = false;
			break;
	}
	
	return result;
}

bool KeyDown( int key )
{
	return ( g_input.keys[key] != 0 );
}

bool KeyUp( int key )
{
	return ( g_input.keys[key] == 0 );
}

bool KeyPressed( int key )
{
	if( g_input.keys[key] == 0 )
		return false;
	return ( g_input.prevKeys[key] == 0 );
}

bool KeyReleased( int key )
{
	if( g_input.prevKeys[key] == 0 )
		return false;
	return ( g_input.keys[key] == 0 );
}

bool ButtonDown( int button )
{
	return ( g_input.buttons[button] != 0 );
}

bool ButtonUp( int button )
{
	return ( g_input.buttons[button] == 0 );
}

bool ButtonPressed( int button )
{
	if( g_input.buttons[button] == 0 )
		return false;
	return ( g_input.prevButtons[button] == 0 );
}

bool ButtonReleased( int button )
{
	if( g_input.prevButtons[button] == 0 )
		return false;
	return ( g_input.buttons[button] == 0 );
}

void MousePosition( int *x, int *y )
{
	*x = g_input.mouseX;
	*y = g_input.mouseY;
}

void MouseDeltaPosition( int *x, int *y )
{
	*x = g_input.mouseX - g_input.prevMouseX;
	*y = g_input.mouseY - g_input.prevMouseY;
}

int MouseDeltaWheel()
{
	return ( g_input.mouseWheel - g_input.prevMouseWheel );
}