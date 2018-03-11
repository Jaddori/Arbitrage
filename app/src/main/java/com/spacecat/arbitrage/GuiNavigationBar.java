package com.spacecat.arbitrage;

import android.graphics.*;
import android.view.MotionEvent;

import java.util.ArrayList;

/**
 * Created by Nix on 2018-03-11.
 */

public class GuiNavigationBar
{
	private Rect _bounds;
	private int _backgroundColor;
	private int _foregroundColor;
	private GuiButton _backButton;
	private boolean _hasBackButton;
	private ArrayList<GuiButton> _stages;
	private ArrayList<GuiAlignedText> _stagePaddings;
	private int _stageOffset;
	private int _stagePadding;

	public void setBounds( Rect bounds ) { _bounds = new Rect( bounds ); }
	public void setBackgroundColor( int color ) { _backgroundColor = color; }
	public void setForegroundColor( int color ) { _foregroundColor = color; }
	public void setHasBackButton( boolean backButton ) { _hasBackButton = backButton; }

	public Rect getBounds() { return _bounds; }
	public int getBackgroundColor() { return _backgroundColor; }
	public int getForegroundColor() { return _foregroundColor; }
	public boolean getHasBackButton() { return _hasBackButton; }

	public GuiNavigationBar( Rect bounds )
	{
		_bounds = new Rect( bounds );
		initialize();
	}

	protected void initialize()
	{
		Rect buttonBounds = Utils.makeRect( _bounds.left, _bounds.top, 256, _bounds.height() );
		_backButton = new GuiButton( buttonBounds, "< Back" );
		_backButton.setColors( 0, 0, Color.LTGRAY );

		_backgroundColor = Color.argb( 255, 35, 128, 128 );
		_foregroundColor = Color.BLACK;

		_stages = new ArrayList<>();
		_stagePaddings = new ArrayList<>();
		_stageOffset = 0;
		_stagePadding = 16;
	}

	public void draw()
	{
		// draw background
		Rendering.drawRect( _bounds, _backgroundColor, _foregroundColor );

		if( _hasBackButton )
		{
			// draw button
			_backButton.draw();
		}
		else
		{
			for( GuiAlignedText padding : _stagePaddings )
			{
				padding.draw();
			}

			for( GuiButton stage : _stages )
			{
				stage.draw();
			}
		}
	}

	public boolean onTouch( MotionEvent e )
	{
		boolean result = false;

		for( int i=0; i<_stages.size() && !result; i++ )
			result = _stages.get( i ).onTouch( e );

		return result;
	}

	public void addStage( String text, GuiButton.ITouchListener delegate )
	{
		// add padding
		if( _stages.size() > 0 )
		{
			GuiAlignedText padding = new GuiAlignedText( ">" );
			padding.calculateTextBounds();

			Rect paddingBounds = new Rect();
			paddingBounds.left = _stageOffset;
			paddingBounds.top = _bounds.top;
			paddingBounds.right = paddingBounds.left + (int)padding.getTextWidth();
			paddingBounds.bottom = _bounds.bottom;

			padding.setBounds( paddingBounds );

			_stagePaddings.add( padding );
			_stageOffset += paddingBounds.width() + _stagePadding;
		}

		// add stage
		Rect bounds = new Rect();
		bounds.left = _stageOffset;
		bounds.top = _bounds.top;
		bounds.right = bounds.left;
		bounds.bottom = _bounds.bottom;

		GuiButton stage = new GuiButton( bounds, text );
		stage.setTouchListener( delegate );
		stage.setColors( 0, 0, Color.GRAY );

		stage.getText().calculateTextBounds();
		bounds.right += stage.getText().getTextWidth() + stage.getText().getTextPadding().x*2;

		stage.setBounds( bounds );
		_stages.add( stage );

		_stageOffset += bounds.width() + _stagePadding;
	}
}
