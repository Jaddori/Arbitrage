package com.spacecat.arbitrage;

import android.graphics.Color;
import android.graphics.Rect;
import android.view.MotionEvent;

import java.util.ArrayList;

/**
 * Created by Nix on 2018-03-17.
 */

public class GuiPage
{
	protected Rect _bounds;
	protected ArrayList<GuiElement> _elements;
	protected boolean _visible;

	public void setBounds( Rect bounds ) { _bounds = new Rect( bounds ); }
	public void setVisible( boolean visible ) { _visible = visible; }

	public Rect getBounds() { return _bounds; }
	public ArrayList<GuiElement> getElements() { return _elements; }
	public boolean getVisible() { return _visible; }

	public GuiPage()
	{
		initialize();
	}

	protected void initialize()
	{
		_bounds = new Rect();
		_elements = new ArrayList<>();
		_visible = true;
	}

	public void draw()
	{
		if( _visible )
		{
			drawBackground();
			drawElements();
		}
	}

	protected void drawBackground()
	{
		Rendering.drawRect( _bounds, Color.YELLOW, Color.BLACK );
	}

	protected void drawElements()
	{
		for( GuiElement element : _elements )
			element.draw();
	}

	public boolean onTouch( MotionEvent e )
	{
		boolean result = false;

		for( GuiElement element : _elements )
		{
			if( element.onTouch( e ) )
				result = true;
		}

		if( Utils.insideRect( _bounds, e.getX(), e.getY() ) )
			result = true;

		return result;
	}
}
