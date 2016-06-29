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

lua_State* CreateLua()
{
	lua_State* result = luaL_newstate();
	luaL_openlibs( result );

	lua_register( result, "WorldMatrix", lua_WorldMatrix );
	lua_register( result, "ViewMatrix", lua_ViewMatrix );
	lua_register( result, "Color", lua_Color );
	lua_register( result, "RenderQuad", lua_RenderQuad );
	lua_register( result, "Render", lua_Render );

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