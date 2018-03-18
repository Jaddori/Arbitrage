package com.spacecat.arbitrage;

import android.graphics.Color;
import android.graphics.Rect;
import android.view.MotionEvent;

import java.util.ArrayList;

/**
 * Created by Nix on 2018-03-17.
 */

public class GuiElement
{
	public interface ITouchListener
	{
		void onTouch( MotionEvent e );
	}
	
	protected Rect _bounds;
	protected boolean _visible;
	protected boolean _enabled;
	protected boolean _pressed;
	protected boolean _drawBackground;
	protected int _backgroundColor;
	protected int _foregroundColor;
	protected int _pressedColor;
	protected ITouchListener _touchListener;
	protected boolean _rounded;
	protected Vec2 _curves;
	protected ArrayList<GuiElement> _children;

	public void setBounds( Rect bounds ) { _bounds = new Rect( bounds ); }
	public void setVisible( boolean visible ) { _visible = visible; }
	public void setEnabled( boolean enabled ) { _enabled = enabled; }
	public void setDrawBackground( boolean draw ) { _drawBackground = draw; }
	public void setBackgroundColor( int color ) { _backgroundColor = color; }
	public void setForegroundColor( int color ) { _foregroundColor = color; }
	public void setPressedColor( int color ) { _pressedColor = color; }
	public void setColors( int bg, int fg, int pressed )
	{
		_backgroundColor = bg;
		_foregroundColor = fg;
		_pressedColor = pressed;
	}
	public void setOnTouch( ITouchListener listener ) { _touchListener = listener; }
	public void setRounded( boolean rounded ) { _rounded = rounded; }
	public void setCurves( Vec2 curves ) { _curves = new Vec2( curves ); }

	public Rect getBounds() { return _bounds; }
	public boolean getVisible() { return _visible; }
	public boolean getEnabled() { return _enabled; }
	public boolean getPressed() { return _pressed; }
	public boolean getDrawBackground() { return _drawBackground; }
	public int getBackgroundColor() { return _backgroundColor; }
	public int getForegroundColor() { return _foregroundColor; }
	public int getPressedColor() { return _pressedColor; }
	public boolean getRounded() { return _rounded; }
	public Vec2 getCurves() { return _curves; }
	public ArrayList<GuiElement> getChildren() { return _children; }

	public GuiElement()
	{
		initialize();
	}

	public GuiElement( Rect bounds )
	{
		initialize();
		_bounds = new Rect( bounds );
	}

	protected void initialize()
	{
		_bounds = new Rect();
		_visible = true;
		_enabled = true;
		_pressed = false;
		_drawBackground = true;
		_rounded = false;
		_curves = new Vec2( 4.0f, 4.0f );

		_backgroundColor = Color.RED;
		_foregroundColor = Color.BLACK;
		_pressedColor = Color.GRAY;

		_children = new ArrayList<>();
	}

	public void draw()
	{
		if( _visible )
		{
			drawBackground();
			drawChildren();
		}
	}

	protected void drawBackground()
	{
		int finalBgColor = 0;
		if( _drawBackground )
			finalBgColor = ( _pressed ? _pressedColor : _backgroundColor );

		if( _rounded )
			Rendering.drawRoundedRect( _bounds, _curves, finalBgColor, _foregroundColor );
		else
			Rendering.drawRect( _bounds, finalBgColor, _foregroundColor );
	}

	protected void drawChildren()
	{
		for( GuiElement child : _children )
			child.draw();
	}

	public boolean onTouch( MotionEvent e )
	{
		boolean result = false;

		if( _enabled )
		{
			result = Utils.insideRect( _bounds, e.getX(), e.getY() );

			// TODO: Handle onTouch on children

			if( e.getActionMasked() == MotionEvent.ACTION_DOWN )
				_pressed = result;
			else if( _pressed )
			{
				if( _touchListener != null )
					_touchListener.onTouch( e );

				_pressed = false;
			}
		}

		return result;
	}
}
