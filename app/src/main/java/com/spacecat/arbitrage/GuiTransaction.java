package com.spacecat.arbitrage;

import android.graphics.Color;
import android.graphics.Rect;
import android.view.MotionEvent;

/**
 * Created by Nix on 2018-03-18.
 */

public class GuiTransaction extends GuiPage
{
	public interface IConfirmationListener
	{
		void onConfirm( Ware ware, Money totalPrice, int amount );
	}

	private final int PADDING = 32;
	public final int MODE_BUY = 0;
	public final int MODE_SELL = 1;

	private GuiLabel _capitalValueLabel;
	private GuiSlider _slider;
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
		//_wareLabel = new GuiLabel( Utils.makeRect( PADDING, _bounds.top + 196, ws.x / 2 - PADDING, 64 ) );
		//_wareValueLabel = new GuiLabel( Utils.makeRect( ws.x / 2, _bounds.top + 196, ws.x / 2 - PADDING, 64 ) );

		Rect sliderBounds = Utils.makeRect( PADDING, _bounds.top + 248, ws.x - PADDING*2, 64 );
		_slider = new GuiSlider( sliderBounds );
		_slider.setOnValueChanged( new GuiSlider.IValueChangeListener()
								   {
									   @Override
									   public void onValueChanged( int oldValue, int newValue )
									   {
										   Money price = _city.calculatePrice( _ware.getName(), newValue );
										   if( _player.canAfford( price ) )
										   		_slider.setCurrentValueTextColor( Color.WHITE );
										   else
										   		_slider.setCurrentValueTextColor( Color.RED );
									   }
								   } );

		_confirmButton = new GuiButton( Utils.makeRect( PADDING, ws.y - 160, ws.x - PADDING * 2, 96 ), "Confirm" );
		_confirmButton.getLabel().getText().setHorizontalAlignment( Alignment.Center );
		_confirmButton.setOnTouch( new GuiElement.ITouchListener()
								   {
									   @Override
									   public void onTouch( MotionEvent e )
									   {
										   onConfirmation();
									   }
								   } );

		addElements( capitalLabel, _capitalValueLabel, /*_wareLabel, _wareValueLabel,*/ _slider, _confirmButton );
	}

	@Override
	public void appearing()
	{
		super.appearing();

		updateAppearance();
	}

	public void updateAppearance()
	{
		_capitalValueLabel.setText( Integer.toString( _player.getMoney().getGold() ) );

		String wareName = _ware.getName();

		int supply = 0;
		if( _mode == MODE_BUY )
			supply = _city.getWare( wareName ).getSupply();
		else
			supply = _player.getWare( wareName ).getSupply();

		_slider.setMaxValue( supply );
		_slider.setValue( 0 );
		_slider.setCurrentValueTextColor( Color.WHITE );
	}

	private void onConfirmation()
	{
		if( _confirmationListener != null )
		{
			int amount = _slider.getValue();
			Money price = _city.calculatePrice( _ware.getName(), amount );
			_confirmationListener.onConfirm( _ware, price, amount );

			_slider.setValue( 0 );
			_slider.setCurrentValueTextColor( Color.WHITE );
		}
	}
}
