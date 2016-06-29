-- Console Script

local Console =
{
	height = 320,
	alpha = 0.3,
	visible = true
}

function UpdateConsole()
end

function RenderConsole()
	if Console.visible then
		Color( 1, 1, 1, Console.alpha )
		Render( 0, 0, 1, 640, Console.height )
	end
end