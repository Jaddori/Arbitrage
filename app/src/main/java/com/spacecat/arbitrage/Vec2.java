package com.spacecat.arbitrage;

/**
 * Created by Nix on 2018-03-06.
 */

public class Vec2
{
	final float FLOAT_ERR = 0.0001f;

	public float x;
	public float y;

	public Vec2()
	{
		x = y = 0.0f;
	}

	public Vec2( float value )
	{
		x = y = value;
	}

	public Vec2( float x, float y )
	{
		this.x = x;
		this.y = y;
	}

	public Vec2( Vec2 ref )
	{
		x = ref.x;
		y = ref.y;
	}

	public boolean equals( Vec2 ref )
	{
		return ( Math.abs( x - ref.x ) < FLOAT_ERR && Math.abs( y - ref.y ) < FLOAT_ERR );
	}

	public void add( float value )
	{
		x += value;
		y += value;
	}

	public void add( Vec2 ref )
	{
		x += ref.x;
		y += ref.y;
	}

	public void sub( float value )
	{
		x -= value;
		y -= value;
	}

	public void sub( Vec2 ref )
	{
		x -= ref.x;
		y -= ref.y;
	}

	public void mul( float value )
	{
		x *= value;
		y *= value;
	}

	public void mul( Vec2 ref )
	{
		x *= ref.x;
		y *= ref.y;
	}

	public void div( float value )
	{
		x /= value;
		y /= value;
	}

	public void div( Vec2 ref )
	{
		x /= ref.x;
		y /= ref.y;
	}

	public void normalize()
	{
		div( magnitude() );
	}

	public float magnitude()
	{
		return (float)Math.sqrt( x*x + y*y );
	}
}
