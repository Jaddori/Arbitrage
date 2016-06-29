#include "scripting.h"

LUAFUNC(lua_WorldMatrix)
{
	int result = 0;
	
	if( lua_gettop( lua ) >= 4 )
	{
		float x = (float)lua_tonumber( lua, 1 );
		float y = (float)lua_tonumber( lua, 2 );
		float z = (float)lua_tonumber( lua, 3 );
		float w = (float)lua_tonumber( lua, 4 );
		float h = (float)lua_tonumber( lua, 5 );
		
		WorldMatrix( x, y, z, w, h );
	}
	
	return result;
}

LUAFUNC(lua_ViewMatrix)
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

LUAFUNC(lua_Color)
{
	int result = 0;
	
	if( lua_gettop( lua ) >= 4 )
	{
		float r = (float)lua_tonumber( lua, 1 );
		float g = (float)lua_tonumber( lua, 2 );
		float b = (float)lua_tonumber( lua, 3 );
		float a = (float)lua_tonumber( lua, 4 );
		
		Color( r, g, b, a );
	}
	
	return result;
}

LUAFUNC(lua_RenderQuad)
{
	int result = 0;
	
	RenderQuad();
	
	return result;
}

LUAFUNC(lua_Render)
{
	int result = 0;
	
	if( lua_gettop( lua ) >= 4 )
	{
		float x = (float)lua_tonumber( lua, 1 );
		float y = (float)lua_tonumber( lua, 2 );
		float z = (float)lua_tonumber( lua, 3 );
		float w = (float)lua_tonumber( lua, 4 );
		float h = (float)lua_tonumber( lua, 5 );
		
		Render( x, y, z, w, h );
	}
	
	return result;
}

LUAFUNC(lua_KeyDown)
{
	int result = 0;
	
	if( lua_gettop( lua ) >= 1 )
	{
		int key = (int)lua_tonumber( lua, 1 );
		
		lua_pushboolean( lua, KeyDown( key ) );
		result = 1;
	}
	
	return result;
}

LUAFUNC(lua_KeyUp)
{
	int result = 0;
	
	if( lua_gettop( lua ) >= 1 )
	{
		int key = (int)lua_tonumber( lua, 1 );
		
		lua_pushboolean( lua, KeyUp( key ) );
		result = 1;
	}
	
	return result;
}

LUAFUNC(lua_KeyPressed)
{
	int result = 0;
	
	if( lua_gettop( lua ) >= 1 )
	{
		int key = (int)lua_tonumber( lua, 1 );
		
		lua_pushboolean( lua, KeyPressed( key ) );
		result = 1;
	}
	
	return result;
}

LUAFUNC(lua_KeyReleased)
{
	int result = 0;
	
	if( lua_gettop( lua ) >= 1 )
	{
		int key = (int)lua_tonumber( lua, 1 );
		
		lua_pushboolean( lua, KeyReleased( key ) );
		result = 1;
	}
	
	return result;
}

LUAFUNC(lua_ButtonDown)
{
	int result = 0;
	
	if( lua_gettop( lua ) >= 1 )
	{
		int button = (int)lua_tonumber( lua, 1 );
		
		lua_pushboolean( lua, ButtonDown( button ) );
		result = 1;
	}
	
	return result;
}

LUAFUNC(lua_ButtonUp)
{
	int result = 0;
	
	if( lua_gettop( lua ) >= 1 )
	{
		int button = (int)lua_tonumber( lua, 1 );
		
		lua_pushboolean( lua, ButtonUp( button ) );
		result = 1;
	}
	
	return result;
}

LUAFUNC(lua_ButtonPressed)
{
	int result = 0;
	
	if( lua_gettop( lua ) >= 1 )
	{
		int button = (int)lua_tonumber( lua, 1 );
		
		lua_pushboolean( lua, ButtonPressed( button ) );
		result = 1;
	}
	
	return result;
}

LUAFUNC(lua_ButtonReleased)
{
	int result = 0;
	
	if( lua_gettop( lua ) >= 1 )
	{
		int button = (int)lua_tonumber( lua, 1 );
		
		lua_pushboolean( lua, ButtonReleased( button ) );
		result = 1;
	}
	
	return result;
}

LUAFUNC(lua_MousePosition)
{
	int result = 2;
	
	int x, y;
	MousePosition( &x, &y );
	
	lua_pushnumber( lua, x );
	lua_pushnumber( lua, y );
	
	return result;
}

LUAFUNC(lua_MouseDeltaPosition)
{
	int result = 2;
	
	int x, y;
	MouseDeltaPosition( &x, &y );
	
	lua_pushnumber( lua, x );
	lua_pushnumber( lua, y );
	
	return result;
}

LUAFUNC(lua_MouseDeltaWheel)
{
	int result = 1;
	
	lua_pushnumber( lua, MouseDeltaWheel() );
	
	return result;
}

lua_State* CreateLua()
{
	lua_State* result = luaL_newstate();
	luaL_openlibs( result );

	// rendering
	lua_register( result, "WorldMatrix", lua_WorldMatrix );
	lua_register( result, "ViewMatrix", lua_ViewMatrix );
	lua_register( result, "Color", lua_Color );
	lua_register( result, "RenderQuad", lua_RenderQuad );
	lua_register( result, "Render", lua_Render );
	
	// input
	lua_register( result, "KeyDown", lua_KeyDown );
	lua_register( result, "KeyUp", lua_KeyUp );
	lua_register( result, "KeyPressed", lua_KeyPressed );
	lua_register( result, "KeyReleased", lua_KeyReleased );
	lua_register( result, "ButtonDown", lua_ButtonDown );
	lua_register( result, "ButtonUp", lua_ButtonUp );
	lua_register( result, "ButtonPressed", lua_ButtonPressed );
	lua_register( result, "ButtonReleased", lua_ButtonReleased );
	lua_register( result, "MousePosition", lua_MousePosition );
	lua_register( result, "MouseDeltaPosition", lua_MouseDeltaPosition );
	lua_register( result, "MouseDeltaWheel", lua_MouseDeltaWheel );

	return result;
}

void DestroyLua( lua_State* lua )
{
	lua_close( lua );
}

void RunScript( lua_State* lua, const char *filename, ScriptHandle *handle )
{
	if( luaL_dofile( lua, filename ) != 0 )
	{
		std::cout << "Script error: " << lua_tostring( lua, -1 ) << std::endl;
		handle->valid = false;
	}
	else
	{
		strncpy( handle->filename, filename, 55 );
		handle->filename[54] = 0;
		handle->valid = true;
		
#ifdef WIN32
#else
		struct stat buf;
		if( stat( filename, &buf ) >= 0 )
		{
			handle->lastWriteTime = buf.st_mtimespec.tv_sec;
		}
		else
			std::cout << "Failed to get file information about script file \"" << filename << "\"." << std::endl;
#endif
	}
}

void HotloadScripts( lua_State* lua, ScriptHandle *handles, int nhandles )
{
	for( int i=0; i<nhandles; i++ )
	{
		struct stat buf;
		if( stat( handles[i].filename, &buf ) >= 0 )
		{
			uint64_t lastWriteTime = buf.st_mtimespec.tv_sec;
			if( lastWriteTime != handles[i].lastWriteTime )
			{
				if( luaL_dofile( lua, handles[i].filename ) != 0 )
				{
					std::cout << "Script error: " << lua_tostring( lua, -1 ) << std::endl;
					handles[i].valid = false;
				}
				else
					handles[i].valid = true;
				
				handles[i].lastWriteTime = lastWriteTime;
				std::cout << "Hotloading!" << std::endl;
			}
		}
	}
}