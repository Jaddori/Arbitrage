#ifndef SCRIPTING_H
#define SCRIPTING_H

#include "arb_inc.h"
#include "rendering.h"

#define LUASIG(name) lua_ ## name
#define LUAFUNC(name) int LUASIG(name) ## ( lua_State* lua )

struct ScriptHandle
{
	char filename[55];
	uint64_t lastWriteTime;
	bool valid;
};

lua_State* CreateLua();
void DestroyLua( lua_State* lua );
void RunScript( lua_State* lua, const char *filename, ScriptHandle *handle );
void HotloadScripts( lua_State* lua, ScriptHandle *handles, int nhandles );

//LUAFUNC(WorldMatrix);
//LUAFUNC(ViewMatrix);
//LUAFUNC(RenderQuad);

int lua_WorldMatrix( lua_State* lua );
int lua_ViewMatrix( lua_State* lua );
int lua_RenderQuad( lua_State* lua );
int lua_Render( lua_State* lua );

#endif