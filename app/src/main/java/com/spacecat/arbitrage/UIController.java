package com.spacecat.arbitrage;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * Created by Nix on 2018-03-10.
 */

public class UIController
{
	protected String name;
	protected UIBase base;
	protected UIController parentController;

	public void setName( String name ) { this.name = name; }
	public void setBase( UIBase base ) { this.base = base; }
	public void setParentController( UIController parent ) { this.parentController = parent; }

	public String getName() { return name; }
	public UIBase getBase() { return base; }
	public UIController getParentController() { return parentController; }

	public UIController( String name )
	{
		this.name = name;
	}

	public void initialize()
	{
	}

	public void draw( Canvas canvas )
	{
		if( base != null )
			base.draw( canvas );
	}

	public void onTouch( MotionEvent e )
	{
		if( base != null )
			base.onTouch( e );
	}
}
