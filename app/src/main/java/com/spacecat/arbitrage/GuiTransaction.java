package com.spacecat.arbitrage;

import android.view.MotionEvent;

/**
 * Created by Nix on 2018-03-18.
 */

public class GuiTransaction extends GuiPage
{
	public interface IConfirmationListener
	{
		void onConfirm( Ware ware, Money price );
	}

	private final int PADDING = 32;
	public final int MODE_BUY = 0;
	public final int MODE_SELL = 1;

	private GuiLabel _capitalValueLabel;
	private GuiLabel _wareLabel;
	private GuiLabel _wareValueLabel;
	private GuiButton _confirmButton;
	private Player _player;
	private City _city;
	private Ware _ware;
	private int _mode;
	private IConfirmationListener _confirmationListener;

	public void setPlayer( Player player ) { _player = player; }
	public void setCity( City city ) { _city = city; }
	public void setWare( Ware ware ) { _ware = ware; }
	public void setMode( int mode ) { _mode = mode; }
	public void setOnConfirmation( IConfirmationListener listener ) { _confirmationListener = listener; }

	public GuiTransaction()
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

		GuiLabel capitalLabel = new GuiLabel( Utils.makeRect( PADDING, _bounds.top + 64, ws.x / 2 - PADDING, 64 ) );
		capitalLabel.setText( "Capital: " );

		_capitalValueLabel = new GuiLabel( Utils.makeRect( ws.x / 2, _bounds.top + 64, ws.x / 2 - PADDING, 64 ) );
		_wareLabel = new GuiLabel( Utils.makeRect( PADDING, _bounds.top + 196, ws.x / 2 - PADDING, 64 ) );
		_wareValueLabel = new GuiLabel( Utils.makeRect( ws.x / 2, _bounds.top + 196, ws.x / 2 - PADDING, 64 ) );

		_confirmButton = new GuiButton( Utils.makeRect( PADDING, ws.y - 160, ws.x - PADDING*2, 96 ), "Confirm" );
		_confirmButton.getLabel().getText().setHorizontalAlignment( Alignment.Center );
		_confirmButton.setOnTouch( new GuiElement.ITouchListener()
								   {
									   @Override
									   public void onTouch( MotionEvent e )
									   {
										   onConfirmation();
									   }
								   } );

				addElements( capitalLabel, _capitalValueLabel, _wareLabel, _wareValueLabel, _confirmButton );
	}

	@Override
	public void appearing()
	{
		super.appearing();

		_capitalValueLabel.setText( Integer.toString( _player.getMoney().getGold() ) );

		String wareName = _ware.getName();

		_wareLabel.setText( wareName + ": " );

		int quantity = 0;
		if( _mode == MODE_BUY )
			quantity = _city.getWare( wareName ).getSupply();
		else
			quantity = _player.getWare( wareName ).getSupply();

		_wareValueLabel.setText( Integer.toString( quantity ) );
	}

	public void updateAppearance()
	{
		_capitalValueLabel.setText( Integer.toString( _player.getMoney().getGold() ) );

		String wareName = _ware.getName();

		_wareLabel.setText( wareName + ": " );

		int quantity = 0;
		if( _mode == MODE_BUY )
			quantity = _city.getWare( wareName ).getSupply();
		else
			quantity = _player.getWare( wareName ).getSupply();

		_wareValueLabel.setText( Integer.toString( quantity ) );
	}

	private void onConfirmation()
	{
		if( _confirmationListener != null )
		{
			Money price = _city.calculatePrice( _ware.getName(), 1 );
			_confirmationListener.onConfirm( _ware, price );
		}
	}
}
