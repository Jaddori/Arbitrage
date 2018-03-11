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
	ArrayList<City> cities;
	GuiWares guiWares;
	boolean disabledAntiAlias;

	public GameView( Context context )
	{
		super( context );
	}

	public void initialize()
	{
		Rendering.initialize();

		// Cities
		cities = new ArrayList<>();

		City city = new City();
		city.setPosition( new Vec2( 32, 32 ) );
		city.setSize( new Vec2( 256, 256 ) );
		city.setName( "Bojangles" );

		City otherCity = new City();
		otherCity.setPosition( new Vec2( 64, 512 ) );
		otherCity.setSize( new Vec2( 512, 128 ) );
		otherCity.setName( "Bluebear" );

		cities.add( city );
		cities.add( otherCity );

		// Gui Wares
		guiWares = new GuiWares();
		guiWares.addWare( "Apples" );
		guiWares.addWare( "Bananas" );
		guiWares.addWare( "Pears" );

		setOnTouchListener( this );

		disabledAntiAlias = false;
	}

	@Override
	protected void onDraw( Canvas canvas )
	{
		if( !disabledAntiAlias )
		{
			final DrawFilter filter = new PaintFlagsDrawFilter( Paint.ANTI_ALIAS_FLAG, 0);
			canvas.setDrawFilter( filter );
		}

		super.onDraw( canvas );

		Rendering.setCanvas( canvas );

		canvas.drawColor( Color.BLUE );

		for( City city : cities )
		{
			city.draw( canvas );
		}

		guiWares.draw( canvas );
	}

	@Override
	public boolean onTouch( View view, MotionEvent e )
	{
		for( City city : cities )
		{
			city.onTouch( e );
		}

		guiWares.onTouch( e );

		invalidate();

		return true;
	}
}
