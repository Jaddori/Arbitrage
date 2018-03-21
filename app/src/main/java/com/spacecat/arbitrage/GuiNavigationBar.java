package com.spacecat.arbitrage;

import android.graphics.*;
import android.view.MotionEvent;

import java.util.ArrayList;

/**
 * Created by Nix on 2018-03-11.
 */

public class GuiNavigationBar extends GuiElement
{
	private final int STAGE_PADDING = 16;

	private GuiButton _backButton;
	private boolean _hasBackButton;
	private ArrayList<GuiButton> _stages;
	private ArrayList<GuiAlignedText> _stagePaddings;
	private int _currentStage;

	public void setHasBackButton( boolean backButton ) { _hasBackButton = backButton; }

	public boolean getHasBackButton() { return _hasBackButton; }

	public GuiNavigationBar( Rect bounds )
	{
		super( bounds );
	}

	@Override
	protected void initialize()
	{
		super.initialize();

		Rect buttonBounds = Utils.makeRect( _bounds.left, _bounds.top, 256, _bounds.height() );
		_backButton = new GuiButton( buttonBounds, "< Back" );
		_backButton.setColors( 0, 0, Color.LTGRAY );

		_backgroundColor = Color.argb( 255, 35, 128, 128 );

		_stages = new ArrayList<>();
		_stagePaddings = new ArrayList<>();
	}

	@Override
	protected void drawChildren()
	{
		super.drawChildren();

		if( _hasBackButton )
		{
			// draw button
			_backButton.draw();
		}
		else
		{
			/*for( GuiAlignedText padding : _stagePaddings )
			{
				padding.draw();
			}

			for( GuiButton stage : _stages )
			{
				stage.draw();
			}*/

			for( int i=0; i<_currentStage; i++ )
			{
				_stagePaddings.get( i ).draw();
			}

			for( int i=0; i<_currentStage+1; i++ )
			{
				_stages.get( i ).draw();
			}
		}
	}

	@Override
	public boolean onTouch( MotionEvent e )
	{
		boolean result = false;

		for( int i=0; i<_stages.size() && !result; i++ )
			result = _stages.get( i ).onTouch( e );

		if( Utils.insideRect( _bounds, e.getX(), e.getY() ) )
			result = true;

		return result;
	}

	public void resetStages()
	{
		_stages.clear();
	}

	public void addStage( String text, GuiButton.ITouchListener delegate )
	{
		// add padding
		if( _stages.size() > 0 )
		{
			GuiAlignedText padding = new GuiAlignedText( ">" );
			padding.calculateTextBounds();

			_stagePaddings.add( padding );
		}

		// add stage
		Rect bounds = new Rect();

		GuiButton stage = new GuiButton( bounds, text );
		stage.setOnTouch( delegate );
		stage.setColors( 0, 0, Color.GRAY );

		_stages.add( stage );

		updateDimensions();
	}

	public void setStage( int index, String text )
	{
		if( index >= 0 && index < _stages.size() )
		{
			_stages.get( index ).setText( text );
			updateDimensions();
		}
	}

	public void showStage( int index )
	{
		_currentStage = index;
	}

	private void updateDimensions()
	{
		int offset = STAGE_PADDING;
		for( int i=0; i<_stages.size(); i++ )
		{
			// update padding dimensions
			if( i > 0 )
			{
				GuiAlignedText padding = _stagePaddings.get( i-1 );

				Rect paddingBounds = new Rect( offset, _bounds.top, offset + (int)padding.getTextWidth(), _bounds.bottom );
				padding.setBounds( paddingBounds );

				offset += paddingBounds.width() + STAGE_PADDING;
			}

			// update stage dimensions
			Rect bounds = new Rect( offset, _bounds.top, offset, _bounds.bottom );

			GuiButton stage = _stages.get( i );
			stage.getLabel().getText().calculateTextBounds();

			bounds.right += stage.getLabel().getText().getTextWidth() + stage.getLabel().getText().getTextPadding().x*2;
			stage.setBounds( bounds );

			offset += bounds.width() + STAGE_PADDING;
		}
	}
}
