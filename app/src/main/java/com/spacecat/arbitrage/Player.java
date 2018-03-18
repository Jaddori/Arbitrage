package com.spacecat.arbitrage;

import java.util.ArrayList;

/**
 * Created by Nix on 2018-03-17.
 */

public class Player
{
	private String _name;
	private Money _money;
	private ArrayList<Ware> _wares;

	public void setName( String name ) { _name = name; }

	public String getName() { return _name; }
	public Money getMoney() { return _money; }
	public ArrayList<Ware> getWares() { return _wares; }

	public Player()
	{
		initialize();
	}

	public Player( String name )
	{
		initialize();

		_name = new String( name );
	}

	private void initialize()
	{
		_name = "";
		_money = new Money();
		_wares = new ArrayList<>();
	}

	public boolean buyWares( Ware ware, Money price )
	{
		boolean result = false;

		if( _money.greaterThan( price ) )
		{
			_money.sub( price );
			_wares.add( new Ware( ware ) );

			result = true;
		}

		return result;
	}

	public boolean sellWares( Ware ware, Money price )
	{
		boolean result = false;

		Ware myWare = null;
		for( int i=0; i<_wares.size() && myWare == null; i++ )
			if( _wares.get( i ).equals( ware ) )
				myWare = _wares.get( i );

		if( myWare != null )
		{
			if( myWare.getSupply() > ware.getSupply() )
			{
				myWare.decrementSupply( ware.getSupply() );
				_money.add( price );

				result = true;
			}
		}

		return result;
	}

	public Ware getWare( String name )
	{
		Ware result = null;

		for( int i=0; i<_wares.size() && result == null; i++ )
			if( _wares.get( i ).getName().equals( name ) )
				result = _wares.get( i );

		return result;
	}

	public Money calculatePrice( String wareName, int amount )
	{
		Money result = new Money();

		Ware ware = getWare( wareName );
		if( ware != null )
		{
			int silver = amount * ( ware.getDemand() / ware.getSupply() ) * 100;
			result.setSilver( silver );
		}

		return result;
	}
}
