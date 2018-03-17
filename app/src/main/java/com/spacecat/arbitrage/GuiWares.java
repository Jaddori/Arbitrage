package com.spacecat.arbitrage;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.widget.Toast;

/**
 * Created by Tunder on 2018-03-10.
 */

public class GuiWares
{
	private final int MAX_WARES = 3;

	private GuiButton[] _buttons;
	private String[] _wares;
	private int _wareCount;
	private Rect _bounds;
	private GuiNavigationBar _navigationBar;
	private boolean _visible;
	private City _selectedCity;

	public void setVisible( boolean visible ) { _visible = visible; }
	public void setSelectedCity( City city )
	{
		_selectedCity = city;

		_wareCount = _selectedCity.getWares().size();

		// NOTE: Make sure _wareCount < MAX_WARES

		for( int i=0; i<_wareCount; i++ )
		{
			_buttons[i].setText( _selectedCity.getWares().get( i ).getName() );
		}
	}

	public boolean getVisible() { return _visible; }

	public GuiWares()
	{
		initialize();
	}

	private void initialize()
	{
		_bounds = new Rect();
		_bounds.left = 0;
		_bounds.right = Utils.windowSize.x;
		_bounds.top = Utils.windowSize.y - 512;
		_bounds.bottom = Utils.windowSize.y;

		_buttons = new GuiButton[MAX_WARES];
		_wares = new String[MAX_WARES];

		int height = 512;
		int buttonWidth = _bounds.width() - 64*2;
		int buttonHeight = 96;
		int spaceHeight = height - buttonHeight * MAX_WARES;
		int spaceSize = spaceHeight / 4;
		int verticalOffset = spaceSize;
		for( int i=0; i<MAX_WARES; i++ )
		{
			_wares[i] = "";

			Rect buttonRect = Utils.makeRect( 64, _bounds.top + verticalOffset, buttonWidth, buttonHeight );
			_buttons[i] = new GuiButton( buttonRect );
			_buttons[i].setColors( Color.RED, Color.BLACK, Color.GRAY );
			_buttons[i].setTouchListener( new GuiButton.ITouchListener()
										  {
											  @Override
											  public void onTouch( MotionEvent e )
											  {
												  //setWare( 2, "CLICKED" );
											  }
										  } );

			verticalOffset += spaceSize + buttonHeight;
		}
		_wareCount = 0;

		Rect navBounds = new Rect( _bounds.left, _bounds.top - 128, _bounds.right, _bounds.top );
		_navigationBar = new GuiNavigationBar( navBounds );
		_navigationBar.setHasBackButton( false );

		GuiButton.ITouchListener firstListener = new GuiButton.ITouchListener()
		{
			@Override
			public void onTouch( MotionEvent e )
			{
				_navigationBar.addStage( "Fourth", null );
			}
		};

		_navigationBar.addStage( "First", firstListener );
		_navigationBar.addStage( "Second", null );
		_navigationBar.addStage( "Third", null );

		_visible = false;
	}

	/*public void addWare( String ware )
	{
		if( _wareCount < MAX_WARES )
		{
			_wares[_wareCount] = ware;
			_buttons[_wareCount].setText( ware );
			_wareCount++;
		}
	}

	public void setWare( int index, String ware )
	{
		if( index >= 0 && index < _wareCount )
		{
			_wares[index] = ware;
			_buttons[index].setText( ware );
		}
	}

	public void resetWares()
	{
		_wareCount = 0;
	}*/

	public void draw( Canvas canvas )
	{
		if( _visible )
		{
			// draw background
			Rendering.drawRect( _bounds, Color.YELLOW, Color.BLACK );

			// draw wares
			for( int i = 0; i < _wareCount; i++ )
			{
				//Rendering.drawText( _wares[i], _bounds.left + 8.0f, _bounds.top + 8.0f + ( i * 64.0f ), 64.0f );
				_buttons[i].draw();
			}

			// draw navigation bar
			_navigationBar.draw();
		}
	}

	public boolean onTouch( MotionEvent e )
	{
		boolean result = false;

		result = _navigationBar.onTouch( e );

		for( GuiButton button : _buttons )
			result = button.onTouch( e );

		return result;
	}
}
