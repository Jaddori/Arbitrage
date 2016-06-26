#include "scripting.h"

lua_State* CreateLua()
{
	lua_State* result = luaL_newstate();
	luaL_openlibs( result );

	lua_register( result, "WorldMatrix", LUASIG(WorldMatrix) );
	lua_register( result, "ViewMatrix", LUASIG(ViewMatrix) );
	lua_register( result, "RenderQuad", LUASIG(RenderQuad) );

	return result;
}

void DestroyLua( lua_State* lua )
{
	lua_close( lua );
}

void RunScript( lua_State* lua, const char *filename )
{
	if( luaL_dofile( lua, filename ) != 0 )
		std::cout << "Script error: " << lua_tostring( lua, -1 ) << std::endl;
}

LUAFUNC(WorldMatrix)
{
	int result = 0;

	if( lua_gettop( lua ) >= 5 )
	{
		float x = (float)lua_tonumber( lua, 1 );
		float y = (float)lua_tonumber( lua, 2 );
		float w = (float)lua_tonumber( lua, 3 );
		float h = (float)lua_tonumber( lua, 4 );
		float rotation = (float)lua_tonumber( lua, 5 );

		WorldMatrix( x, y, w, h, rotation );
	}

	return result;
}

LUAFUNC(ViewMatrix)
{
	int result = 0;

	if( lua_gettop( lua ) >= 2 )
	{
		float x = (float)lua_tonumber( lua, 1 );
		float y = (float)lua_tonumber( lua, 2 );

		ViewMatrix( x, y );
	}

	return result;
}

LUAFUNC(RenderQuad)
{
	int result = 0;

	RenderQuad();

	return result;
}