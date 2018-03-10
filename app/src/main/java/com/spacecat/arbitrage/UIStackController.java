package com.spacecat.arbitrage;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by Nix on 2018-03-10.
 */

public class UIStackController extends UIController
{
	private Stack<UIController> controllers;
	private Stack<UILabel> pathLabels;
	private boolean pathVisible;
	private int pathOffset;
	private Rect bounds;

	public UIStackController( String name )
	{
		super( name );

		bounds = new Rect( 0, 0, 0, 0 );
		controllers = new Stack<>();
		pathLabels = new Stack<>();
		pathVisible = true;
		pathOffset = 0;
	}

	public UIStackController( String name, Rect bounds )
	{
		super( name );

		this.bounds = bounds;
		controllers = new Stack<>();
		pathLabels = new Stack<>();
		pathVisible = true;
		pathOffset = 0;
	}

	public void pushController( UIController controller )
	{
		Rect baseBounds = controller.getBase().getBounds();
		int hdif = bounds.left - baseBounds.left;
		int vdif = bounds.top - baseBounds.top;
		baseBounds.left += hdif;
		baseBounds.right += hdif;
		baseBounds.top += vdif;
		baseBounds.bottom += vdif;
		controller.getBase().setBounds( baseBounds );

		controllers.push( controller );

		int left = pathOffset;
		int top = bounds.top;
		int right = bounds.right;
		int bottom = bounds.bottom;

		UILabel label = new UILabel( new Rect( left, right, top, bottom ), controller.getName() );
		label.initialize();
		pathLabels.add( label );
	}

	public void popController()
	{
		if( !controllers.isEmpty() )
		{
			controllers.pop();
			pathLabels.pop();
		}
	}

	public void draw( Canvas canvas )
	{
		super.draw( canvas );

		controllers.peek().draw( canvas );

		for( UILabel label : pathLabels )
		{
			label.draw( canvas );
		}
	}

	public void onTouch( MotionEvent e )
	{
		super.onTouch( e );

		controllers.peek().onTouch( e );
	}
}
