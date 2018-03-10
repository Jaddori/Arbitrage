package com.spacecat.arbitrage;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

import java.util.ArrayList;

/**
 * Created by Nix on 2018-03-10.
 */

public class UIStageController extends UIController
{
	private Rect bounds;
	private ArrayList<UIController> controllers;
	private ArrayList<UILabel> labels;
	private int barHeight;
	private UIPanel panel;
	private int currentStage;
	private int labelOffset;
	private int labelPadding;

	public UIStageController( String name )
	{
		super( name );

		bounds = new Rect();
	}

	public UIStageController( String name, Rect bounds )
	{
		super( name );

		this.bounds = bounds;
	}

	public void initialize()
	{
		controllers = new ArrayList<>();
		labels = new ArrayList<>();
		barHeight = 32;
		labelOffset = 8;
		labelPadding = 8;
		panel = new UIPanel();
		panel.initialize();

		currentStage = 0;
	}

	public void addController( UIController... newControllers )
	{
		for( UIController controller : newControllers )
		{
			// adjust bounds of sub controller
			Rect cb = controller.getBase().getBounds();
			int hdif = bounds.left - cb.left;
			int vdif = bounds.top - cb.top + barHeight;

			cb.left += hdif;
			cb.right += hdif;
			cb.top += vdif;
			cb.bottom += vdif;

			controller.getBase().setBounds( cb );

			// add label
			UILabel label = new UILabel( new Rect( bounds.left + labelOffset, bounds.top, bounds.right, bounds.bottom ), controller.getName() );
			label.initialize();

			labelOffset += label.getTextPaint().measureText( label.getText() ) + labelPadding;

			// add label and controller
			controllers.add( controller );
			labels.add( label );
		}
	}

	@Override
	public void draw( Canvas canvas )
	{
		panel.draw( canvas );

		for( int i=0; i<=currentStage; i++ )
			labels.get( i ).draw( canvas );

		controllers.get( currentStage ).draw( canvas );
	}

	@Override
	public void onTouch( MotionEvent e )
	{
		controllers.get( currentStage ).onTouch( e );
	}

	public void nextStage()
	{
		if( currentStage < controllers.size() - 1 )
			currentStage++;
	}

	public void previousStage()
	{
		if( currentStage > 0 )
			currentStage--;
	}

	public void resetStage()
	{
		currentStage = 0;
	}
}
