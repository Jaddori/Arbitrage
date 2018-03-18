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

public class GuiWares extends GuiPage
{
	public interface IWareListener
	{
		void onWareSelected( Ware ware );
	}

	private final int MAX_WARES = 3;

	private GuiButton[] _buttons;
	private int _wareCount;
	private City _city;
	private Ware[] _cityWares;
	private Player _player;
	private IWareListener _wareListener;

	public void setCity( City city ) { _city = city; }
	public void setPlayer( Player player )
	{
		_player = player;
	}
	public void setOnWareSelected( IWareListener listener ) { _wareListener = listener; }

	public boolean getVisible() { return _visible; }

	public GuiWares()
	{
		initialize();
	}

	@Override
	protected void initialize()
	{
		super.initialize();

		Point ws = Utils.windowSize;
		int height = 512;
		_bounds = Utils.makeRect( 0, ws.y - height, ws.x, height );

		_buttons = new GuiButton[MAX_WARES];

		int buttonWidth = _bounds.width() - 64*2;
		int buttonHeight = 96;
		int spaceHeight = height - buttonHeight * MAX_WARES;
		int spaceSize = spaceHeight / 4;
		int verticalOffset = spaceSize;
		for( int i=0; i<MAX_WARES; i++ )
		{
			final int index = i;

			Rect buttonRect = Utils.makeRect( 64, _bounds.top + verticalOffset, buttonWidth, buttonHeight );
			_buttons[i] = new GuiButton( buttonRect );
			_buttons[i].setColors( Color.RED, Color.BLACK, Color.GRAY );
			_buttons[i].setOnTouch( new GuiButton.ITouchListener()
										  {
											  @Override
											  public void onTouch( MotionEvent e )
											  {
											  	onButtonPressed( index );
											  }
										  } );

			verticalOffset += spaceSize + buttonHeight;
		}
		addElements( _buttons );

		_cityWares = new Ware[MAX_WARES];

		_wareCount = 0;

		_visible = false;
	}

	@Override
	public void appearing()
	{
		super.appearing();

		_wareCount = _city.getWares().size();

		// NOTE: Make sure _wareCount < MAX_WARES

		for( int i=0; i<_wareCount; i++ )
		{
			_buttons[i].setText( _city.getWares().get( i ).getName() );
			_cityWares[i] = _city.getWares().get( i );
		}
	}

	@Override
	protected void drawElements()
	{
		for( int i=0; i<_wareCount; i++ )
			_buttons[i].draw();
	}

	private void onButtonPressed( int index )
	{
		if( _wareListener != null )
			_wareListener.onWareSelected( _cityWares[index] );
	}
}
