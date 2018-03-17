package com.spacecat.arbitrage;

import android.graphics.Color;
import android.graphics.Rect;
import android.view.MotionEvent;

/**
 * Created by Nix on 2018-03-17.
 */

public class GuiInventory extends GuiPage
{
	private Player _player;
	private City _city;
	private Ware _ware;
	private GuiAlignedText _cityLabel;
	private GuiAlignedText _cityWareLabel;
	private GuiAlignedText _inventoryLabel;
	private GuiAlignedText _inventoryWareLabel;
	private GuiButton _buyButton;
	private GuiButton _sellButton;

	public void setPlayer( Player player ) { _player = player; }
	public void setCity( City city ) { _city = city; }
	public void setWare( Ware ware )
	{
		_ware = new Ware( ware );

		Ware playerWare = _player.getWare( _ware.getName() );
		if( playerWare != null )
		{
			_inventoryWareLabel.setText( Integer.toString( playerWare.getQuantity() ) );
		}

		Ware cityWare = _city.getWare( _ware.getName() );
		if( cityWare != null )
		{
			_cityWareLabel.setText( Integer.toString( cityWare.getQuantity() ) );
		}
	}

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

		_cityLabel = new GuiAlignedText( "City: " );
		_cityLabel.setBounds( Utils.makeRect( 32, _bounds.top + 64, _bounds.width() / 2 - 32, 64 ) );

		_cityWareLabel = new GuiAlignedText( "0" );
		_cityWareLabel.setBounds( Utils.makeRect( _bounds.width() / 2, _bounds.top + 64, _bounds.width() / 2, 64 ) );

		_inventoryLabel = new GuiAlignedText( "Inventory: " );
		_inventoryLabel.setBounds( Utils.makeRect( 32, _bounds.top + 196, _bounds.width() / 2 - 32, 64 ) );

		_inventoryWareLabel = new GuiAlignedText( "0" );
		_inventoryWareLabel.setBounds( Utils.makeRect( _bounds.width() / 2, _bounds.top + 196, _bounds.width() / 2, 64 ) );

		int buttonMargin = 64;
		int buttonWidth = ( ws.x - buttonMargin*3 ) / 2;
		int buttonHeight = 96;
		_buyButton = new GuiButton( Utils.makeRect( buttonMargin, ws.y - 160, buttonWidth, buttonHeight ), "Buy" );
		_buyButton.getLabel().getText().setHorizontalAlignment( Alignment.Center );
		_buyButton.setColors( Color.RED, Color.BLACK, Color.GRAY );

		_sellButton = new GuiButton( Utils.makeRect( buttonMargin*2 + buttonWidth, ws.y - 160, buttonWidth, buttonHeight ), "Sell" );
		_sellButton.getLabel().getText().setHorizontalAlignment( Alignment.Center );
		_sellButton.setColors( Color.RED, Color.BLACK, Color.GRAY );
	}

	@Override
	protected void drawElements()
	{
		_cityLabel.draw();
		_cityWareLabel.draw();
		_inventoryLabel.draw();
		_inventoryWareLabel.draw();

		_buyButton.draw();
		_sellButton.draw();
	}

	public boolean onTouch( MotionEvent e )
	{
		boolean result = false;

		return result;
	}
}
