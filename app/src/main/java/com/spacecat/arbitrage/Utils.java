package com.spacecat.arbitrage;

import android.graphics.Rect;

/**
 * Created by Nix on 2018-03-10.
 */

public class Utils
{
	public static Point windowSize;

	public static boolean insideRect( Rect rect, Point point )
	{
		return ( point.x >= rect.left && point.x <= rect.right && point.y >= rect.top && point.y <= rect.bottom );
	}

	public static boolean insideRect( Rect rect, Vec2 point )
	{
		return ( point.x >= rect.left && point.x <= rect.right && point.y >= rect.top && point.y <= rect.bottom );
	}

	public static boolean insideRect( Rect rect, float x, float y )
	{
		return ( x >= rect.left && x <= rect.right && y >= rect.top && y <= rect.bottom );
	}

	public static boolean insideRect( Vec2 position, Vec2 size, Point point )
	{
		return ( point.x >= position.x && point.x <= position.x+size.x &&
				point.y >= position.y && point.y <= position.y+size.y );
	}

	public static boolean insideRect( Vec2 position, Vec2 size, Vec2 point )
	{
		return ( point.x >= position.x && point.x <= position.x+size.x &&
				point.y >= position.y && point.y <= position.y+size.y );
	}

	public static boolean insideRect( Vec2 position, Vec2 size, float x, float y )
	{
		return ( x >= position.x && x <= position.x+size.x &&
				y >= position.y && y <= position.y+size.y );
	}

	public static Rect makeRect( int x, int y, int width, int height )
	{
		return new Rect( x, y, x+width, y+height );
	}
}
