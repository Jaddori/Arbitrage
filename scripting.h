#ifndef SCRIPTING_H
#define SCRIPTING_H

#include "arb_inc.h"
#include "rendering.h"

#define LUAFUNC(name) int name ( lua_State* lua )

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

#endif