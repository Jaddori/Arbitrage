package com.spacecat.arbitrage;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import java.util.ArrayList;

/**
 * Created by Nix on 2018-03-04.
 */

public abstract class UIBase
{
	public interface IOnTouch
	{
		void onTouch( MotionEvent e );
	}

	protected ArrayList<UIBase> children;
	protected Rect bounds;
	protected UIController parentController;
	protected IOnTouch onTouchDelegate;

	public void setBounds( Rect bounds ) { this.bounds = bounds; }
	public void setParentController( UIController parent ) { this.parentController = parent; }
	public void setOnTouchDelegate( IOnTouch del ) { this.onTouchDelegate = del; }

	public Rect getBounds() { return bounds; }
	public UIController getParentController() { return parentController; }

	public UIBase()
	{
		children = new ArrayList<>();
	}

	public abstract void initialize();
	public abstract void draw( Canvas canvas );
	public boolean onTouch( MotionEvent e )
	{
		boolean result = Utils.insideRect( bounds, e.getX(), e.getY() );

		if( result )
		{
			if( onTouchDelegate != null )
				onTouchDelegate.onTouch( e );
		}

		return result;
	}

	public void addChild( UIBase child )
	{
		children.add( child );
	}
}
