//
//  input.h
//  Arbitrage
//
//  Created by Niclas Olsson on 2016-06-29.
//  Copyright © 2016 SpaceCat. All rights reserved.
//

#ifndef input_h
#define input_h

#include "arb_inc.h"

#define INPUT_MAX_KEYS 128
#define INPUT_MAX_BUTTONS 5
#define INPUT_MAX_TEXT 10

struct Input
{
	uint8_t keys[INPUT_MAX_KEYS], prevKeys[INPUT_MAX_KEYS];
	uint8_t buttons[INPUT_MAX_BUTTONS], prevButtons[INPUT_MAX_BUTTONS];
	int mouseX, mouseY, mouseWheel;
	int prevMouseX, prevMouseY, prevMouseWheel;
	char text[INPUT_MAX_TEXT];
	int ntext;
};

void SyncInput();
bool GetInput( SDL_Event *e );
bool KeyDown( int key );
bool KeyUp( int key );
bool KeyPressed( int key );
bool KeyReleased( int key );
bool ButtonDown( int button );
bool ButtonUp( int button );
bool ButtonPressed( int button );
bool ButtonReleased( int button );
void MousePosition( int *x, int *y );
void MouseDeltaPosition( int *x, int *y );
int MouseDeltaWheel();

#endif /* input_h */
