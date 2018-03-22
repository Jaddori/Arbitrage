package com.spacecat.arbitrage;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Nix on 2018-03-22.
 */

public class MoneyUnitTests
{
	@Test
	public void ctor_ValidInput_RemainsTheSame()
	{
		Money m = new Money( 13, 37 );

		assertEquals( 13, m.getGold() );
		assertEquals( 37, m.getSilver() );
	}

	@Test
	public void ctor_TooMuchSilver_TransformsToGold()
	{
		Money m = new Money( 12, 137 );

		assertEquals( 13, m.getGold() );
		assertEquals( 37, m.getSilver() );
	}

	@Test
	public void add_Normal_AddsInput()
	{
		Money a = new Money( 10, 30 );
		Money b = new Money( 3, 7 );

		a.add( b );

		assertEquals( 13, a.getGold() );
		assertEquals( 37, a.getSilver() );
	}

	@Test
	public void add_TooMuchSilver_TransformsToGold()
	{
		Money a = new Money( 10, 90 );
		Money b = new Money( 2, 47 );

		a.add( b );

		assertEquals( 13, a.getGold() );
		assertEquals( 37, a.getSilver() );
	}

	@Test
	public void add_Normal_AddedMoneyRemainsUnaltered()
	{
		Money a = new Money( 10, 30 );
		Money b = new Money( 3, 7 );

		a.add( b );

		assertEquals( 3, b.getGold() );
		assertEquals( 7, b.getSilver() );
	}

	@Test
	public void addSilver_Normal_AddsSilver()
	{
		Money a = new Money( 13, 30 );

		a.addSilver( 7 );

		assertEquals( 13, a.getGold() );
		assertEquals( 37, a.getSilver() );
	}

	@Test
	public void addSilver_TooMuch_TransformsToGold()
	{
		Money a = new Money( 12, 97 );

		a.addSilver( 40 );

		assertEquals( 13, a.getGold() );
		assertEquals( 37, a.getSilver() );
	}

	@Test
	public void sub_Normal_SubtractInput()
	{
		Money a = new Money( 14, 47 );
		Money b = new Money( 1, 10 );

		a.sub( b );

		assertEquals( 13, a.getGold() );
		assertEquals( 37, a.getSilver() );
	}

	@Test
	public void sub_TooLittleSilver_TransformsFromGold()
	{
		Money a = new Money( 15, 7 );
		Money b = new Money( 1, 70 );

		a.sub( b );

		assertEquals( 13, a.getGold() );
		assertEquals( 37, a.getSilver() );
	}

	@Test
	public void sub_Normal_SubtractorRemainsUnaltered()
	{
		Money a = new Money( 14, 47 );
		Money b = new Money( 1, 10 );

		a.sub( b );

		assertEquals( 1, b.getGold() );
		assertEquals( 10, b.getSilver() );
	}

	@Test
	public void greater_FirstGreaterThanLastGold_ReturnsTrue()
	{
		Money a = new Money( 5, 0 );
		Money b = new Money( 2, 0 );

		assertTrue( a.greater( b ) );
	}

	@Test
	public void greater_FirstLessThanLastGold_ReturnsFalse()
	{
		Money a = new Money( 2, 0 );
		Money b = new Money( 5, 0 );

		assertFalse( a.greater( b ) );
	}

	@Test
	public void greater_FirstEqualToLastGold_ReturnsFalse()
	{
		Money a = new Money( 2, 0 );
		Money b = new Money( 2, 0 );

		assertFalse( a.greater( b ) );
	}

	@Test
	public void greater_FirstGreaterThanLastSilverButNotGold_ReturnsFalse()
	{
		Money a = new Money( 2, 30 );
		Money b = new Money( 5, 10 );

		assertFalse( a.greater( b ) );
	}

	@Test
	public void greaterEquals_FirstGreaterThanLast_ReturnsTrue()
	{
		Money a = new Money( 5, 0 );
		Money b = new Money( 2, 0 );

		assertTrue( a.greaterEquals( b ) );
	}

	@Test
	public void greaterEquals_FirstLessThanSecond_ReturnsFalse()
	{
		Money a = new Money( 2, 0 );
		Money b = new Money( 5, 0 );

		assertFalse( a.greaterEquals( b ) );
	}

	@Test
	public void greaterEquals_FirstEqualToLast_ReturnsTrue()
	{
		Money a = new Money( 2, 0 );
		Money b = new Money( 2, 0 );

		assertTrue( a.greaterEquals( b ) );
	}

	@Test
	public void greaterEquals_FirstGreaterThanSecondSilverButNotGold_ReturnsFalse()
	{
		Money a = new Money( 2, 30 );
		Money b = new Money( 5, 10 );

		assertFalse( a.greaterEquals( b ) );
	}

	@Test
	public void equals_ObjectsAreEqual_ReturnsTrue()
	{
		Money a = new Money( 13, 37 );
		Money b = new Money( 13, 37 );

		assertTrue( a.equals( b ) );
	}

	@Test
	public void equals_ObjectsAreDifferent_ReturnsFalse()
	{
		Money a = new Money( 13, 37 );
		Money b = new Money( 14, 47 );

		assertFalse( a.equals( b ) );
	}
}
