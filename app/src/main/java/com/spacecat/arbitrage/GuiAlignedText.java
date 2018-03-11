package com.spacecat.arbitrage;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Tunder on 2018-03-11.
 */

public class GuiAlignedText
{
	private String _text;
	private float _size;
	private Alignment _horizontalAlignment;
	private Alignment _verticalAlignment;
	private float _textWidth;
	private boolean _dirtyWidth;
	private boolean _dirtyPosition;
	private Paint _paint;
	private Vec2 _textPosition;
	private Vec2 _textPadding;
	private Rect _bounds;
	private Paint.FontMetrics _fontMetrics;

	public void setText( String text )
	{
		_text = new String( text );
		_dirtyWidth = true;
	}
	public void setSize( float size )
	{
		_size = size;
		_dirtyWidth = true;
	}
	public void setHorizontalAlignment( Alignment alignment )
	{
		_horizontalAlignment = alignment;
		_dirtyPosition = true;
	}
	public void setVerticalAlignment( Alignment alignment )
	{
		_verticalAlignment = alignment;
		_dirtyPosition = true;
	}
	public void setAlignment( Alignment horizontal, Alignment vertical )
	{
		_horizontalAlignment = horizontal;
		_verticalAlignment = vertical;
		_dirtyPosition = true;
	}
	public void setTextPadding( Vec2 padding )
	{
		_textPadding = new Vec2( padding );
		_dirtyPosition = true;
	}
	public void setBounds( Rect bounds )
	{
		_bounds = new Rect( bounds );
		_dirtyPosition = true;
	}

	public String getText() { return _text; }
	public float getSize() { return _size; }
	public Alignment getHorizontalAlignment() { return _horizontalAlignment; }
	public Alignment getVerticalAlignment() { return _verticalAlignment; }
	public float getTextWidth() { return _textWidth; }
	public boolean getDirtyBounds() { return _dirtyWidth; }
	public Paint getPaint() { return _paint; }
	public Vec2 getTextPosition() { return _textPosition; }
	public Vec2 getTextPadding() { return _textPadding; }
	public Rect getBounds() { return _bounds; }

	public GuiAlignedText()
	{
		initialize();
	}

	public GuiAlignedText( String text )
	{
		initialize();
		_text = text;
	}

	public GuiAlignedText( String text, float size )
	{
		initialize();
		_text = text;
		_size = size;
	}

	public GuiAlignedText( String text, float size, Alignment horizontal, Alignment vertical )
	{
		initialize();
		_text = text;
		_size = size;
		_horizontalAlignment = horizontal;
		_verticalAlignment = vertical;
	}

	protected void initialize()
	{
		_text = "";
		_size = 64.0f;
		_horizontalAlignment = Alignment.Near;
		_verticalAlignment = Alignment.Center;
		_textWidth = 0.0f;
		_dirtyWidth = true;
		_dirtyPosition = true;
		_paint = new Paint();
		_paint.setTextSize( _size );
		_paint.setColor( Color.WHITE );
		_paint.setShadowLayer( 1.0f, 4.0f, 4.0f, Color.BLACK );
		_textPosition = new Vec2();
		_textPadding = new Vec2();
		_bounds = new Rect();
	}

	public void calculateTextBounds()
	{
		_textWidth = _paint.measureText( _text );
		_dirtyWidth = false;
		_dirtyPosition = true;

		_fontMetrics = _paint.getFontMetrics();
	}

	public void calculateTextPosition()
	{
		switch( _horizontalAlignment )
		{
			case Near:
				_textPosition.x = _bounds.left + _textPadding.x;
				break;

			case Far:
				_textPosition.x = _bounds.right - _textPadding.x - _textWidth;
				break;

			case Center:
				_textPosition.x = _bounds.centerX() - _textWidth * 0.5f;
				break;
		}

		float height = (_fontMetrics.descent - _fontMetrics.ascent);
		switch( _verticalAlignment )
		{
			case Near:
				_textPosition.y = _bounds.top + _textPadding.y;
				break;

			case Far:
				_textPosition.y = _bounds.bottom - _textPadding.y - height;
				break;

			case Center:
				_textPosition.y = _bounds.centerY() - height * 0.5f;
				break;
		}

		_dirtyPosition = false;
	}

	public void draw()
	{
		if( _dirtyWidth )
			calculateTextBounds();
		if( _dirtyPosition )
			calculateTextPosition();

		Rendering.getCanvas().drawText( _text, _textPosition.x, _textPosition.y - _fontMetrics.ascent, _paint );
	}
}
