package com.spacecat.arbitrage;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;

import java.util.ArrayList;

/**
 * Created by Nix on 2018-03-10.
 */

public class City
{
	private String _name;
	private Vec2 _position;
	private Vec2 _size;
	private Paint _paint;

	private ArrayList<Ware> _wares;

	public void setName( String name ) { _name = name; }
	public void setPosition( Vec2 position )
	{
		_position = position;
	}
	public void setSize( Vec2 size ) { _size = size; }

	public String getName() { return _name; }
	public Vec2 getPosition() { return _position; }
	public Vec2 getSize() { return _size; }
	public Paint getPaint() { return _paint; }
	public ArrayList<Ware> getWares() { return _wares; }

	public City()
	{
		initialize();
	}

	private void initialize()
	{
		_name = "";
		_position = new Vec2();
		_size = new Vec2();
		_wares = new ArrayList<>();

		_paint = new Paint();
		_paint.setColor( Color.RED );
	}

	public void draw( Canvas canvas )
	{
		canvas.drawRect( new RectF( _position.x, _position.y, _position.x+_size.x, _position.y+_size.y ), _paint );
		Rendering.drawText( _name, _position, 32.0f );
	}

	public boolean onTouch( MotionEvent e )
	{
		boolean result = Utils.insideRect( _position, _size, e.getX(), e.getY() );

		if( e.getActionMasked() == MotionEvent.ACTION_DOWN )
		{
			if( result )
				_paint.setColor( Color.YELLOW );
		}
		else if( e.getActionMasked() == MotionEvent.ACTION_UP )
		{
			if( result )
				_paint.setColor( Color.GREEN );
			else
				_paint.setColor( Color.RED );
		}

		return result;
	}

	public void addWare( Ware ware )
	{
		_wares.add( new Ware( ware ) );
	}

	public Ware getWare( String name )
	{
		Ware result = null;

		for( int i=0; i<_wares.size() && result == null; i++ )
			if( _wares.get( i ).getName().equals( name ) )
				result = _wares.get( i );

		return result;
	}

	public Money calculatePrice( String wareName, int amount )
	{
		Money result = new Money();

		Ware ware = getWare( wareName );
		if( ware != null )
		{
			int silver = amount * (int)( ( (float)ware.getDemand() / (float)ware.getSupply() ) * 100.0f );
			result.setSilver( silver );
		}

		return result;
	}

	public boolean buyWares( Ware ware, Money totalPrice, int amount )
	{
		boolean result = false;

		//if( _money.greaterEquals( totalPrice ) )
		{
			//_money.sub( totalPrice );

			Ware correspondingWare = getWare( ware.getName() );
			if( correspondingWare != null )
			{
				correspondingWare.incrementSupply( amount );
			}

			result = true;
		}

		return result;
	}

	public boolean sellWares( Ware ware, Money totalPrice, int amount )
	{
		boolean result = false;

		Ware myWare = null;
		for( int i=0; i<_wares.size() && myWare == null; i++ )
			if( _wares.get( i ).equals( ware ) )
				myWare = _wares.get( i );

		if( myWare != null )
		{
			if( myWare.getSupply() >= amount )
			{
				myWare.decrementSupply( amount );
				//_money.add( totalPrice );

				result = true;
			}
		}

		return result;
	}
}
