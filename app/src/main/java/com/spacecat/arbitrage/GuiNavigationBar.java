package com.spacecat.arbitrage;

import android.graphics.*;

/**
 * Created by Nix on 2018-03-11.
 */

public class GuiNavigationBar
{
	private Rect _bounds;
	private int _backgroundColor;
	private int _foregroundColor;
	private GuiButton _backButton;

	public void setBounds( Rect bounds ) { _bounds = new Rect( bounds ); }
	public void setBackgroundColor( int color ) { _backgroundColor = color; }
	public void setForegroundColor( int color ) { _foregroundColor = color; }

	public Rect getBounds() { return _bounds; }
	public int getBackgroundColor() { return _backgroundColor; }
	public int getForegroundColor() { return _foregroundColor; }

	public GuiNavigationBar( Rect bounds )
	{
		_bounds = new Rect( bounds );
		initialize();
	}

	protected void initialize()
	{
		Rect buttonBounds = Utils.makeRect( _bounds.left, _bounds.top, 256, _bounds.height() );
		_backButton = new GuiButton( buttonBounds, "< Back" );
		_backButton.setColors( 0, 0, Color.LTGRAY );

		_backgroundColor = Color.argb( 255, 35, 128, 128 );
		_foregroundColor = Color.BLACK;
	}

	public void draw()
	{
		// draw background
		Rendering.drawRect( _bounds, _backgroundColor, _foregroundColor );

		// draw buttons
		_backButton.draw();
	}
}
