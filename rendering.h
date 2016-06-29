#ifndef rendering_h
#define rendering_h

#include "arb_inc.h"

#define WINDOW_WIDTH 640
#define WINDOW_HEIGHT 48+

struct Texture
{
	GLuint id;
	int width, height;
};

GLuint LoadShader( const char *source, GLenum type );
GLuint LoadProgram( const char *vsource, const char *gsource, const char *fsource );
GLuint CreateQuad();
bool LoadTexture( const char *filename, Texture *texture );

void WorldMatrix( float x, float y, float z, float width, float height );
void ViewMatrix( float x, float y );
void Color( float r, float g, float b, float a );
void RenderQuad();
void Render( float x, float y, float z, float width, float height );

#endif /* rendering_h */
