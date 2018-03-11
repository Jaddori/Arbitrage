package com.spacecat.arbitrage;

import android.graphics.*;

/**
 * Created by Tunder on 2018-03-10.
 */

public class Rendering
{
	private static Canvas _canvas;
	private static Paint _backgroundPaint;
	private static Paint _foregroundPaint;
	private static Paint _textPaint;

	public static void setCanvas( Canvas canvas ) { _canvas = canvas; }

	public static void initialize()
	{
		_backgroundPaint = new Paint();
		_backgroundPaint.setColor( Color.BLACK );

		_foregroundPaint = new Paint();
		_foregroundPaint.setColor( Color.WHITE );
		_foregroundPaint.setStyle( Paint.Style.STROKE );
		_foregroundPaint.setStrokeWidth( 4.0f );

		_textPaint = new Paint();
		_textPaint.setColor( Color.WHITE );
		_textPaint.setShadowLayer( 1.0f, 4.0f, 4.0f, Color.BLACK );
		_textPaint.setTextSize( 32.0f );
	}

	public static void drawText( String text, Vec2 position, float size )
	{
		_textPaint.setTextSize( size );

		_canvas.drawText( text, position.x, position.y + size, _textPaint );
	}

	public static void drawText( String text, Point position, float size )
	{
		drawText( text, new Vec2( position.x, position.y ), size );
	}

	public static void drawText( String text, float x, float y, float size )
	{
		drawText( text, new Vec2( x, y ), size );
	}

	public static void drawText( String text, int x, int y, float size )
	{
		drawText( text, new Vec2( x, y ), size );
	}

	public static void drawRect( Rect bounds, int backgroundColor, int foregroundColor )
	{
		_backgroundPaint.setColor( backgroundColor );
		_foregroundPaint.setColor( foregroundColor );

		_canvas.drawRect( bounds, _backgroundPaint );
		_canvas.drawRect( bounds, _foregroundPaint );
	}

	public static void drawRoundedRect( Rect bounds, Vec2 curves, int backgroundColor, int foregroundColor )
	{
		_backgroundPaint.setColor( backgroundColor );
		_foregroundPaint.setColor( foregroundColor );

		_canvas.drawRoundRect( bounds.left, bounds.top, bounds.right, bounds.bottom, curves.x, curves.y, _backgroundPaint );
		_canvas.drawRoundRect( bounds.left, bounds.top, bounds.right, bounds.bottom, curves.x, curves.y, _foregroundPaint );
	}
}
