package com.spacecat.arbitrage;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Nix on 2018-03-06.
 */

public class GameView extends View implements View.OnTouchListener
{
	ArrayList<UIBase> elements;
	ArrayList<City> cities;
	UIStageController stageController;

	public GameView( Context context )
	{
		super( context );
	}

	public void initialize()
	{
		// UI Elements
		elements = new ArrayList<>();

		UILabel label = new UILabel( new Rect( 32, 32, 32, 32 ) );
		label.setText( "Label" );

		UIButton button = new UIButton( new Rect( 32, 256, 256, 256+128 ), "Button" );
		button.getPanel().rounded = true;
		button.getPanel().setCurves( new Vec2( 16.0f, 16.0f ) );

		elements.add( label );
		elements.add( button );

		for( UIBase element : elements )
		{
			element.initialize();
		}

		// Cities
		cities = new ArrayList<>();

		City city = new City();
		city.initialize();
		city.setPosition( new Vec2( 32, 32 ) );
		city.setSize( new Vec2( 256, 256 ) );
		city.setName( "Bojangles" );

		City otherCity = new City();
		otherCity.initialize();
		otherCity.setPosition( new Vec2( 64, 512 ) );
		otherCity.setSize( new Vec2( 512, 128 ) );
		otherCity.setName( "Bluebear" );

		cities.add( city );
		cities.add( otherCity );

		// Stage Controller
		stageController = new UIStageController( "Stages", new Rect( 0, 512, 1024, 1024 ) );
		stageController.initialize();

		UIController a = new UIController( "First" );
		a.initialize();
		UIPanel ap = new UIPanel( new Rect( 0, 0, 1024, 512 ) );
		ap.initialize();
		ap.getBackgroundPaint().setColor( Color.RED );
		ap.setOnTouchDelegate( new UIBase.IOnTouch()
							   {
								   @Override
								   public void onTouch( MotionEvent e )
								   {
									   if( e.getActionMasked() == MotionEvent.ACTION_DOWN )
									   	return;
									   	stageController.nextStage();
								   }
							   } );
		a.setBase( ap );

		UIController b = new UIController( "Second" );
		b.initialize();
		UIPanel bp = new UIPanel( new Rect( 0, 0, 1024, 512 ) );
		bp.initialize();
		bp.getBackgroundPaint().setColor( Color.GREEN );
		bp.setOnTouchDelegate( new UIBase.IOnTouch()
							   {
								   @Override
								   public void onTouch( MotionEvent e )
								   {
								   	if( e.getActionMasked() == MotionEvent.ACTION_DOWN )
								   		return;
									   stageController.nextStage();
								   }
							   } );
		b.setBase( bp );

		UIController c = new UIController( "Third" );
		c.initialize();
		UIPanel cp = new UIPanel( new Rect( 0, 0, 1024, 512 ) );
		cp.initialize();
		cp.getBackgroundPaint().setColor( Color.YELLOW );
		cp.setOnTouchDelegate( new UIBase.IOnTouch()
							   {
								   @Override
								   public void onTouch( MotionEvent e )
								   {
								   	if( e.getActionMasked() == MotionEvent.ACTION_DOWN )
								   		return;
									   stageController.resetStage();
								   }
							   } );
		c.setBase( cp );

		stageController.addController( a, b, c );

		setOnTouchListener( this );
	}

	@Override
	protected void onDraw( Canvas canvas )
	{
		super.onDraw( canvas );

		canvas.drawColor( Color.BLUE );

		for( UIBase element : elements )
		{
			//element.draw( canvas );
		}

		for( City city : cities )
		{
			//city.draw( canvas );
		}

		stageController.draw( canvas );
	}

	@Override
	public boolean onTouch( View view, MotionEvent e )
	{
		stageController.onTouch( e );

		for( City city : cities )
		{
			city.onTouch( e );
		}

		invalidate();

		return true;
	}
}
