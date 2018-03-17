package com.spacecat.arbitrage;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Nix on 2018-03-06.
 */

public class GameView extends View implements View.OnTouchListener
{
	ArrayList<City> _cities;
	City _selectedCity;
	boolean _disabledAntiAlias;
	Player _player;
	GuiTrade _guiTrade;

	public GameView( Context context )
	{
		super( context );
	}

	public void initialize()
	{
		Rendering.initialize();

		// Cities
		_cities = new ArrayList<>();

		City city = new City();
		city.setPosition( new Vec2( 32, 32 ) );
		city.setSize( new Vec2( 256, 256 ) );
		city.setName( "Bojangles" );
		city.addWare( new Ware( "Apples", 5 ) );
		city.addWare( new Ware( "Bananas", 10 ) );

		City otherCity = new City();
		otherCity.setPosition( new Vec2( 64, 512 ) );
		otherCity.setSize( new Vec2( 512, 128 ) );
		otherCity.setName( "Bluebear" );
		otherCity.addWare( new Ware( "Pineapples", 5 ) );
		otherCity.addWare( new Ware( "Pears", 10 ) );
		otherCity.addWare( new Ware("Melons", 15 ) );

		_cities.add( city );
		_cities.add( otherCity );

		setOnTouchListener( this );

		_disabledAntiAlias = false;

		_player = new Player( "Bojangles" );
		_player.getMoney().add( new Money( 10 ) );
		_player.getWares().add( new Ware( "Apples", 7 ) );

		_guiTrade = new GuiTrade();
		_guiTrade.setPlayer( _player );
		_guiTrade.setCity( city );
		_guiTrade.setWare( new Ware( "Apples" ) );
	}

	@Override
	protected void onDraw( Canvas canvas )
	{
		if( !_disabledAntiAlias )
		{
			final DrawFilter filter = new PaintFlagsDrawFilter( Paint.ANTI_ALIAS_FLAG, 0);
			canvas.setDrawFilter( filter );
		}

		super.onDraw( canvas );

		Rendering.setCanvas( canvas );

		canvas.drawColor( Color.BLUE );

		for( City city : _cities )
		{
			city.draw( canvas );
		}

		_guiTrade.draw();
	}

	@Override
	public boolean onTouch( View view, MotionEvent e )
	{
		if( _guiTrade.onTouch( e ) )
		{
		}
		else
		{
			_selectedCity = null;
			for( City city : _cities )
			{
				if( city.onTouch( e ) )
					_selectedCity = city;
			}

			if( _selectedCity != null )
			{
				_guiTrade.setCity( _selectedCity );
			}
			//else
			//	_guiTrade.setVisible( false );
		}

		invalidate();

		return true;
	}
}
