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
	visible = Console.visible,
	input = ""
}

function AddConsoleMessage( message )
	for i=10,1,-1 do
		Console.messages[i] = Console.messages[i-1]
	end

	Console.messages[1] = message
end

function UpdateConsole()
	local text = TextInput()
	if text then
		local str = text[1]
		for i=2,#text do
			str = str .. text[i]
		end

		ConsoleTextbox.input = ConsoleTextbox.input .. str
	end

	if KeyReleased( Keys.Return ) then
		AddConsoleMessage( ConsoleTextbox.input )
		ConsoleTextbox.input = ""
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
		--RenderText( Console.font, ConsoleTextbox.x+1, ConsoleTextbox.y, "Testing..." )
		RenderText( Console.font, ConsoleTextbox.x+1, ConsoleTextbox.y, ConsoleTextbox.input )
	end
end