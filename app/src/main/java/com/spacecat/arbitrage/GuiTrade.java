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

	public void setPlayer( Player player )
	{
		((GuiWares)_pages[PAGE_WARES]).setPlayer( player );
		((GuiInventory)_pages[PAGE_INVENTORY]).setPlayer( player );
	}

	public void setCity( City city )
	{
		((GuiWares)_pages[PAGE_WARES]).setCity( city );
		((GuiInventory)_pages[PAGE_INVENTORY]).setCity( city );
	}

	public void setWare( Ware ware )
	{
		((GuiInventory)_pages[PAGE_INVENTORY]).setWare( ware );
	}

	public GuiTrade()
	{
		initialize();
	}

	private void initialize()
	{
		_currentPage = 0;

		_pages = new GuiPage[MAX_PAGES];
		_pages[PAGE_WARES] = new GuiWares();
		_pages[PAGE_INVENTORY] = new GuiInventory();

		Rect navBounds = Utils.makeRect( 0, Utils.windowSize.y-512-128, Utils.windowSize.x, 128 );
		_navigationBar = new GuiNavigationBar( navBounds );
		_navigationBar.setHasBackButton( false );
		_navigationBar.addStage( "First", null );
	}

	public void draw()
	{
		// TODO: This is temporary. Remove this:
		_pages[_currentPage].setVisible( true );

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
