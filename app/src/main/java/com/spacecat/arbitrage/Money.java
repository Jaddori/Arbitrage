package com.spacecat.arbitrage;

/**
 * Created by Nix on 2018-03-17.
 */

public class Money
{
	public final int SILVER_PER_GOLD = 100;

	private int _gold;
	private int _silver;

	public void setGold( int gold ) { _gold = gold; }
	public void setSilver( int silver )
	{
		_silver = silver;
		transform();
	}

	public int getGold() { return _gold; }
	public int getSilver() { return _silver; }

	public Money()
	{
		this( 0, 0 );
	}

	public Money( int gold )
	{
		this( gold, 0 );
	}

	public Money( int gold, int silver )
	{
		_gold = gold;
		_silver = silver;

		transform();
	}

	private void transform()
	{
		if( _silver > 0 )
		{
			_gold += _silver / SILVER_PER_GOLD;
			_silver = _silver % SILVER_PER_GOLD;
		}
		else
		{
			int absSilver = -_silver;
			_gold -= ( absSilver / SILVER_PER_GOLD ) + 1;
			_silver = absSilver % SILVER_PER_GOLD;
		}
	}

	public void add( Money ref )
	{
		_gold += ref._gold;
		_silver += ref._silver;

		transform();
	}

	public void addGold( int gold )
	{
		_gold += gold;
	}

	public void addSilver( int silver )
	{
		_silver += silver;
		transform();
	}

	public void sub( Money ref )
	{
		_gold -= ref._gold;
		_silver -= ref._silver;

		transform();
	}

	public void subGold( int gold )
	{
		_gold -= gold;
	}

	public void subSilver( int silver )
	{
		_silver -= silver;
		transform();
	}

	public void mul( Money ref )
	{
		_gold *= ref._gold;
		_silver *= ref._gold;

		transform();
	}

	public void mul( int multiplier )
	{
		_gold *= multiplier;
		_silver *= multiplier;

		transform();
	}

	public void mul( int goldMul, int silverMul )
	{
		_gold *= goldMul;
		_silver *= silverMul;

		transform();
	}

	public void div( Money ref )
	{
		_gold /= ref._gold;
		_silver /= ref._silver;

		transform();
	}

	public void div( int divisor )
	{
		_gold /= divisor;
		_silver /= divisor;

		transform();
	}

	public void div( int goldDiv, int silverDiv )
	{
		_gold /= goldDiv;
		_silver /= silverDiv;

		transform();
	}

	public boolean greater( Money ref )
	{
		int total = _gold * SILVER_PER_GOLD + _silver;
		int refTotal = ref._gold * SILVER_PER_GOLD + ref._silver;

		return ( total > refTotal );
	}

	public boolean greaterEquals( Money ref )
	{
		int total = _gold * SILVER_PER_GOLD + _silver;
		int refTotal = ref._gold * SILVER_PER_GOLD + ref._silver;

		return ( total >= refTotal );
	}

	public boolean less( Money ref )
	{
		int total = _gold * SILVER_PER_GOLD + _silver;
		int refTotal = ref._gold * SILVER_PER_GOLD + ref._silver;

		return ( total < refTotal );
	}

	public boolean lessEquals( Money ref )
	{
		int total = _gold * SILVER_PER_GOLD + _silver;
		int refTotal = ref._gold * SILVER_PER_GOLD + ref._silver;

		return ( total <= refTotal );
	}

	public boolean equals( Money ref )
	{
		return ( _gold == ref._gold && _silver == ref._silver );
	}

	public static Money add( Money a, Money b )
	{
		return new Money( a._gold + b._gold, a._silver + b._silver );
	}

	public static Money sub( Money a, Money b )
	{
		return new Money( a._gold - b._gold, a._silver - b._silver );
	}

	public static Money mul( Money a, Money b )
	{
		return new Money( a._gold * b._gold, a._silver * b._silver );
	}

	public static Money mul( Money a, int multiplier )
	{
		return new Money( a._gold * multiplier, a._silver * multiplier );
	}

	public static Money mul( Money a, int _goldMul, int _silverMul )
	{
		return new Money( a._gold * _goldMul, a._silver * _silverMul );
	}

	public static Money div( Money a, Money b )
	{
		return new Money( a._gold / b._gold, a._silver / b._silver );
	}

	public static Money div( Money a, int divisor )
	{
		return new Money( a._gold / divisor, a._silver / divisor );
	}

	public static Money div( Money a, int _goldDiv, int _silverDiv )
	{
		return new Money( a._gold / _goldDiv, a._silver / _silverDiv );
	}
}
