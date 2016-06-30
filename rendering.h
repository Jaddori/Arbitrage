#ifndef rendering_h
#define rendering_h

#include "arb_inc.h"

#define WINDOW_WIDTH 640
#define WINDOW_HEIGHT 480
#define FONT_RANGE (127-32)

struct Texture
{
	GLuint id;
	int width, height;
};

struct Font
{
	Texture texture;
	uint8_t widths[FONT_RANGE];
	uint8_t xoffsets[FONT_RANGE], yoffsets[FONT_RANGE];
	union
	{
		uint8_t dimensions[5];
		struct { uint8_t height, paddingx, paddingy, shadowx, shadowy; };
	};
};

GLuint LoadShader( const char *source, GLenum type );
GLuint LoadProgram( const char *vsource, const char *gsource, const char *fsource );
GLuint CreateQuad();
bool LoadTexture( const char *filename, Texture *texture );
bool LoadFont( const char *imgFile, const char *infoFile, Font *font );

void WorldMatrix( float x, float y, float z, float width, float height );
void ViewMatrix( float x, float y );
void Color( float r, float g, float b, float a );
void UVOffset( float x, float y, float width, float height );
void RenderQuad();
void Render( float x, float y, float z, float width, float height );
void RenderText( Font *font, float x, float y, const char *text, int len = 0 );

#endif /* rendering_h */
