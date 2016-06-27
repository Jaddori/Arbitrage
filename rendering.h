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

void WorldMatrix( float x, float y, float width, float height );
void ViewMatrix( float x, float y );
void RenderQuad();

#endif /* rendering_h */
