package com.spacecat.arbitrage;

import android.graphics.Rect;
import android.view.MotionEvent;

/**
 * Created by Nix on 2018-03-17.
 */

public class GuiTrade
{
	public final int PAGE_WARES = 0;
	public final int PAGE_INVENTORY = 1;
	public final int PAGE_TRANSACTION = 2;
	public final int MAX_PAGES = PAGE_TRANSACTION+1;

	private GuiPage[] _pages;
	private GuiNavigationBar _navigationBar;
	private int _currentPage;
	private Ware _selectedWare;
	private City _city;
	private Player _player;

	public void setPlayer( Player player )
	{
		_player = player;

		((GuiWares)_pages[PAGE_WARES]).setPlayer( player );
		((GuiInventory)_pages[PAGE_INVENTORY]).setPlayer( player );
		((GuiTransaction)_pages[PAGE_TRANSACTION]).setPlayer( player );
	}

	public void setCity( City city )
	{
		_city = city;

		((GuiWares)_pages[PAGE_WARES]).setCity( city );
		((GuiInventory)_pages[PAGE_INVENTORY]).setCity( city );
		((GuiTransaction)_pages[PAGE_TRANSACTION]).setCity( city );
	}

	public void setWare( Ware ware )
	{
		((GuiInventory)_pages[PAGE_INVENTORY]).setWare( ware );
		((GuiTransaction)_pages[PAGE_TRANSACTION]).setWare( ware );
	}

	public GuiTrade()
	{
		initialize();
	}

	private void initialize()
	{
		_currentPage = 0;

		_pages = new GuiPage[MAX_PAGES];

		GuiWares wares = new GuiWares();
		wares.setOnWareSelected( new GuiWares.IWareListener()
								 {
									 @Override
									 public void onWareSelected( Ware ware )
									 {
										 _currentPage = PAGE_INVENTORY;
										 GuiInventory inventory = (GuiInventory)_pages[_currentPage];

										 _selectedWare = ware;
										 inventory.setWare( ware );
										 inventory.appearing();

										 _navigationBar.setStage( PAGE_INVENTORY, ware.getName() );
										 _navigationBar.showStage( PAGE_INVENTORY );
									 }
								 } );

		GuiInventory inventory = new GuiInventory();
		inventory.setOnModeSelected( new GuiInventory.IModeListener()
									 {
										 @Override
										 public void onModeSelected( int mode )
										 {
											 _currentPage = PAGE_TRANSACTION;

											 GuiTransaction transaction = (GuiTransaction)_pages[_currentPage];
											 transaction.setWare( _selectedWare );
											 transaction.appearing();

											 String modeName = "Buy";
											 if( mode == transaction.MODE_SELL
											 _navigationBar.setStage( PAGE_TRANSACTION, modeName );
											 _navigationBar.showStage( PAGE_TRANSACTION );
										 }
									 } );

		GuiTransaction transaction = new GuiTransaction();
		transaction.setOnConfirmation( new GuiTransaction.IConfirmationListener()
									   {
										   @Override
										   public void onConfirm( Ware ware, Money price )
										   {
											   _player.buyWares( ware, price );

											   GuiTransaction t = (GuiTransaction)_pages[PAGE_TRANSACTION];
											   t.updateAppearance();
										   }
									   } );

		_pages[PAGE_WARES] = wares;
		_pages[PAGE_INVENTORY] = inventory;
		_pages[PAGE_TRANSACTION] = transaction;

		for( GuiPage page : _pages )
			page.setVisible( true );

		Rect navBounds = Utils.makeRect( 0, Utils.windowSize.y-512-128, Utils.windowSize.x, 128 );
		_navigationBar = new GuiNavigationBar( navBounds );
		_navigationBar.setHasBackButton( false );
		_navigationBar.addStage( "", new GuiElement.ITouchListener()
		{
			@Override
			public void onTouch( MotionEvent e )
			{
				_currentPage = PAGE_WARES;
			}
		} );
		_navigationBar.addStage( "", new GuiElement.ITouchListener()
		{
			@Override
			public void onTouch( MotionEvent e )
			{
				_currentPage = PAGE_INVENTORY;
			}
		} );
	}

	public void appearing()
	{
		_currentPage = PAGE_WARES;
		_pages[_currentPage].appearing();

		_navigationBar.setStage( PAGE_WARES, _city.getName() );
		_navigationBar.showStage( PAGE_WARES );
	}

	public void disappearing()
	{
		_pages[_currentPage].disappearing();
	}

	public void draw()
	{
		_pages[_currentPage].draw();
		_navigationBar.draw();
	}

	public boolean onTouch( MotionEvent e )
	{
		boolean result = false;

		if( _pages[_currentPage].onTouch( e ) )
			result = true;

		if( _navigationBar.onTouch( e ) )
			result = true;

		return result;
	}
}
