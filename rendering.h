//
//  rendering.h
//  Arbitrage
//
//  Created by Niclas Olsson on 2016-06-23.
//  Copyright © 2016 SpaceCat. All rights reserved.
//

#ifndef rendering_h
#define rendering_h

#include "arb_inc.h"

struct Texture
{
	GLuint id;
	int width, height;
};

GLuint LoadShader( const char *source, GLenum type );
GLuint LoadProgram( const char *vsource, const char *gsource, const char *fsource );
GLuint CreateQuad();
bool LoadTexture( const char *filename, Texture *texture );

#endif /* rendering_h */
