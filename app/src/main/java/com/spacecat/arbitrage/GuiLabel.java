package com.spacecat.arbitrage;

import android.graphics.Rect;

/**
 * Created by Nix on 2018-03-17.
 */

public class GuiLabel extends GuiElement
{
	private GuiAlignedText _text;

	@Override
	public void setBounds( Rect bounds )
	{
		super.setBounds( bounds );
		_text.setBounds( bounds );
	}

	public void setText( String text )
	{
		_text.setText( text );
	}

	public GuiAlignedText getText() { return _text; }

	public GuiLabel()
	{
		super();
	}

	public GuiLabel( Rect bounds )
	{
		super( bounds );
		_text.setBounds( bounds );
	}

	@Override
	protected void initialize()
	{
		super.initialize();

		_text = new GuiAlignedText();
	}

	@Override
	public void draw()
	{
		if( _visible )
		{
			drawText();
		}
	}

	protected void drawText()
	{
		_text.draw();
	}
}
