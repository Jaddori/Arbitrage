#ifndef ARB_INC_H
#define ARB_INC_H

#include <iostream>

#ifdef WIN32
#include "GL/glew.h"
#include "SDL.h"
#include "SDL_image.h"
#else
#include <OpenGL/gl3.h>
#include <SDL2_image/SDL_image.h>
#endif

#include "lua.hpp"
#include "glm.hpp"
#include "matrix_transform.hpp"

#endif