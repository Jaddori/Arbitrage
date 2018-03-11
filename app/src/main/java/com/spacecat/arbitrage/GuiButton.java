package com.spacecat.arbitrage;

import android.graphics.*;
import android.view.MotionEvent;

/**
 * Created by Tunder on 2018-03-10.
 */

public class GuiButton
{
	public interface ITouchListener
	{
		void onTouch( MotionEvent e );
	}

	private Rect _bounds;
	private String _text;
	private int _backgroundColor;
	private int _foregroundColor;
	private int _pressedColor;
	private boolean _rounded;
	private Vec2 _curves;
	private Vec2 _padding;
	private ITouchListener _touchListener;
	private boolean _pressed;

	public void setBounds( Rect bounds ) { _bounds = new Rect( bounds ); }
	public void setText( String text ) { _text = new String( text ); }
	public void setBackgroundColor( int color ) { _backgroundColor = color; }
	public void setForegroundColor( int color ) { _foregroundColor = color; }
	public void setPressedColor( int color ) { _pressedColor = color; }
	public void setRounded( boolean rounded ) { _rounded = rounded; }
	public void setCurves( Vec2 curves ) { _curves = new Vec2( curves ); }
	public void setPadding( Vec2 padding ) { _padding = new Vec2( padding ); }
	public void setTouchListener( ITouchListener listener )
	{
		_touchListener = listener;
	}

	public GuiButton()
	{
		initialize();
	}

	public GuiButton( Rect bounds )
	{
		initialize();
		_bounds = bounds;
	}

	public GuiButton( Rect bounds, String text )
	{
		initialize();
		_bounds = bounds;
		_text = text;
	}

	protected void initialize()
	{
		_bounds = new Rect();
		_text = "";
		_backgroundColor = Color.GRAY;
		_foregroundColor = Color.WHITE;
		_pressedColor = Color.BLACK;
		_pressed = false;
		_rounded = true;
		_curves = new Vec2( 12.0f, 12.0f );
		_padding = new Vec2( 12.0f, 12.0f );
	}

	public void draw()
	{
		int finalBackgroundColor = ( _pressed ? _pressedColor : _backgroundColor );

		if( _rounded )
			Rendering.drawRoundedRect( _bounds, _curves, finalBackgroundColor, _foregroundColor );
		else
			Rendering.drawRect( _bounds, finalBackgroundColor, _foregroundColor );

		Rendering.drawText( _text, _bounds.left + _padding.x, _bounds.top + _padding.y, 64.0f );
	}

	public boolean onTouch( MotionEvent e )
	{
		if( e.getActionMasked() == MotionEvent.ACTION_DOWN )
			_pressed = Utils.insideRect( _bounds, e.getX(), e.getY() );
		else
		{
			if( _pressed )
			{
				if( _touchListener != null )
					_touchListener.onTouch( e );
			}

			_pressed = false;
		}

		return _pressed;
	}
}
