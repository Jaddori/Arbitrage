package com.spacecat.arbitrage;

/**
 * Created by Nix on 2018-03-17.
 */

public class Ware
{
	private String _name;
	private int _quantity;

	public void setName( String name ) { _name = new String( name ); }
	public void setQuantity( int quantity ) { _quantity = quantity; }

	public String getName() { return _name; }
	public int getQuantity() { return _quantity; }

	public Ware()
	{
	}

	public Ware( Ware ref )
	{
		_name = ref._name;
		_quantity = ref._quantity;
	}

	public Ware( String name )
	{
		this( name, 0 );
	}

	public Ware( String name, int quantity )
	{
		_name = new String( name );
		_quantity = quantity;
	}

	public void incrementQuantity( int amount )
	{
		_quantity += amount;
	}

	public void decrementQuantity( int amount )
	{
		_quantity -= amount;
	}
}
