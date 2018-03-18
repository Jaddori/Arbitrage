package com.spacecat.arbitrage;

/**
 * Created by Nix on 2018-03-17.
 */

public class Ware
{
	private String _name;
	private int _supply;
	private int _demand;

	public void setName( String name ) { _name = new String( name ); }
	public void setSupply( int supply ) { _supply = supply; }
	public void setDemand( int demand ) { _demand = demand; }

	public String getName() { return _name; }
	public int getSupply() { return _supply; }
	public int getDemand() { return _demand; }

	public Ware()
	{
	}

	public Ware( Ware ref )
	{
		_name = ref._name;
		_supply = ref._supply;
		_demand = ref._demand;
	}

	public Ware( String name )
	{
		this( name, 0, 0 );
	}

	public Ware( String name, int supply, int demand )
	{
		_name = new String( name );
		_supply = supply;
		_demand = demand;
	}

	public void incrementSupply( int amount )
	{
		_supply += amount;
	}

	public void decrementSupply( int amount )
	{
		_supply -= amount;
	}

	public void incrementDemand( int amount )
	{
		_demand += amount;
	}

	public void decrementDemand( int amount )
	{
		_demand -= amount;
	}
}
