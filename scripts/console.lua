-- Console Script

local Console =
{
	x = 0,
	y = 0,
	width = 640,
	height = 320,
	alpha = 0.5,
	visible = true,
	font = LoadFont( "./fonts/Verdana24.png", "./fonts/Verdana24.txt" ),
	messages = {}
}

local ConsoleTextbox =
{
	x = Console.x,
	y = Console.y + Console.height + 1,
	width = 640,
	height = 24,
	alpha = Console.alpha,
	visible = Console.visible
}

local timeElapsed = 0.0
local number = 0

function AddConsoleMessage( message )
	for i=10,1,-1 do
		Console.messages[i] = Console.messages[i-1]
	end

	Console.messages[1] = message
end

function UpdateConsole()
	timeElapsed = timeElapsed + 0.01

	if timeElapsed > 1 then
		AddConsoleMessage( "Test" .. number )
		timeElapsed = 0.0
		number = number + 1
	end
end

function RenderConsole()
	if Console.visible then
		-- Render console
		Color( 1, 1, 1, Console.alpha )
		UVOffset( 0, 0, 1, 1 )
		Render( Console.x, Console.y, Console.width, Console.height )

		-- Render textbox
		Render( ConsoleTextbox.x, ConsoleTextbox.y, ConsoleTextbox.width, ConsoleTextbox.height )

		-- Render console messages
		Color( 1, 1, 1, 1 )
		local y = 24.0
		for _,v in pairs(Console.messages) do
			RenderText( Console.font, Console.x+1, Console.height - y, v )
			y = y + 24
		end

		-- Render input text
		RenderText( Console.font, ConsoleTextbox.x+1, ConsoleTextbox.y, "Testing..." )
	end
end