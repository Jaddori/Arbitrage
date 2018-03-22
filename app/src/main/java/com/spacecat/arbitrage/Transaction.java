package com.spacecat.arbitrage;

/**
 * Created by Nix on 2018-03-22.
 */

public class Transaction
{
	public static Money calculatePrice( int supply, int demand, int amount )
	{
		Money result = new Money();

		for( int i=0; i<amount; i++ )
		{
			int silver = (int)( ( (float)demand / (float)( supply + i ) ) * 100.0f );
			result.addSilver( silver );
		}

		return result;
	}
}
