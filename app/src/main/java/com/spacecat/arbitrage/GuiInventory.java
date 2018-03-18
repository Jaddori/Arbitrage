package com.spacecat.arbitrage;

import android.graphics.Color;
import android.graphics.Rect;
import android.view.MotionEvent;

/**
 * Created by Nix on 2018-03-17.
 */

public class GuiInventory extends GuiPage
{
	public interface IModeListener
	{
		void onModeSelected( int mode );
	}

	public final int MODE_BUY = 0;
	public final int MODE_SELL = 1;

	private Player _player;
	private City _city;
	private Ware _ware;
	private GuiLabel _cityLabel;
	private GuiLabel _cityWareLabel;
	private GuiLabel _inventoryLabel;
	private GuiLabel _inventoryWareLabel;
	private GuiButton _buyButton;
	private GuiButton _sellButton;
	private IModeListener _modeListener;

	public void setPlayer( Player player ) { _player = player; }
	public void setCity( City city ) { _city = city; }
	public void setWare( Ware ware ) { _ware = new Ware( ware ); }
	public void setOnModeSelected( IModeListener listener ) { _modeListener = listener; }

	public GuiInventory()
	{
		super();
	}

	@Override
	protected void initialize()
	{
		super.initialize();

		Point ws = Utils.windowSize;

		int height = 512;
		_bounds = Utils.makeRect( 0, ws.y - height, ws.x, height );

		_cityLabel = new GuiLabel( Utils.makeRect( 32, _bounds.top + 64, _bounds.width() / 2 - 32, 64 ) );
		_cityLabel.setText( "City: " );

		_cityWareLabel = new GuiLabel( Utils.makeRect( _bounds.width() / 2, _bounds.top + 64, _bounds.width() / 2, 64 ) );

		_inventoryLabel = new GuiLabel( Utils.makeRect( 32, _bounds.top + 196, _bounds.width() / 2 - 32, 64 ) );
		_inventoryLabel.setText( "Inventory: " );

		_inventoryWareLabel = new GuiLabel( Utils.makeRect( _bounds.width() / 2, _bounds.top + 196, _bounds.width() / 2, 64 ) );

		int buttonMargin = 64;
		int buttonWidth = ( ws.x - buttonMargin*3 ) / 2;
		int buttonHeight = 96;
		_buyButton = new GuiButton( Utils.makeRect( buttonMargin, ws.y - 160, buttonWidth, buttonHeight ), "Buy" );
		_buyButton.getLabel().getText().setHorizontalAlignment( Alignment.Center );
		_buyButton.setColors( Color.RED, Color.BLACK, Color.GRAY );
		_buyButton.setOnTouch( new GuiElement.ITouchListener()
							   {
								   @Override
								   public void onTouch( MotionEvent e )
								   {
									   onButtonPressed( MODE_BUY );
								   }
							   } );

		_sellButton = new GuiButton( Utils.makeRect( buttonMargin * 2 + buttonWidth, ws.y - 160, buttonWidth, buttonHeight ), "Sell" );
		_sellButton.getLabel().getText().setHorizontalAlignment( Alignment.Center );
		_sellButton.setColors( Color.RED, Color.BLACK, Color.GRAY );
		_sellButton.setOnTouch( new GuiElement.ITouchListener()
								{
									@Override
									public void onTouch( MotionEvent e )
									{
										onButtonPressed( MODE_SELL );
									}
								} );

		addElements( _cityLabel, _cityWareLabel, _inventoryLabel, _inventoryWareLabel, _buyButton, _sellButton );
	}

	@Override
	public void appearing()
	{
		Ware playerWare = _player.getWare( _ware.getName() );
		if( playerWare != null )
		{
			_inventoryWareLabel.setText( Integer.toString( playerWare.getSupply() ) );
		}

		Ware cityWare = _city.getWare( _ware.getName() );
		if( cityWare != null )
		{
			_cityWareLabel.setText( Integer.toString( cityWare.getSupply() ) );
		}
	}

	private void onButtonPressed( int mode )
	{
		if( _modeListener != null )
			_modeListener.onModeSelected( mode );
	}
}
