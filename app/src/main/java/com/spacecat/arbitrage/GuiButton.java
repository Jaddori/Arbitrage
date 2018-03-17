package com.spacecat.arbitrage;

import android.graphics.*;
import android.view.MotionEvent;

/**
 * Created by Tunder on 2018-03-10.
 */

public class GuiButton extends GuiElement
{
	//private GuiAlignedText _text;
	private GuiLabel _label;

	/*public void setText( String text )
	{
		_text.setText( text );
	}*/

	@Override
	public void setBounds( Rect bounds )
	{
		super.setBounds( bounds );
		_label.setBounds( bounds );
	}

	public void setText( String text )
	{
		_label.setText( text );
	}

	public GuiLabel getLabel() { return _label; }

	public GuiButton()
	{
		initialize();
	}

	public GuiButton( Rect bounds )
	{
		super( bounds );
		setBounds( bounds );
	}

	public GuiButton( Rect bounds, String text )
	{
		super( bounds );
		setBounds( bounds );
		setText( text );
	}

	@Override
	protected void initialize()
	{
		super.initialize();

		_rounded = true;
		_curves = new Vec2( 12.0f, 12.0f );

		_label = new GuiLabel();
		_label.getText().setTextPadding( new Vec2( 8.0f, 8.0f ) );

		_children.add( _label );
	}
}
