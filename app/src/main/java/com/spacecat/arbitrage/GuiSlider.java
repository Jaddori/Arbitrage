package com.spacecat.arbitrage;

import android.graphics.Color;
import android.graphics.Rect;
import android.view.MotionEvent;

/**
 * Created by Nix on 2018-03-19.
 */

public class GuiSlider extends GuiElement
{
	public interface IValueChangeListener
	{
		void onValueChanged( int oldValue, int newValue );
	}

	private static final int SLIDER_INDENT_HEIGHT = 32;
	private static final int SLIDER_PAD_WIDTH = 32;

	private Rect _indentBounds;
	private Rect _padBounds;
	private GuiAlignedText _minValueText;
	private GuiAlignedText _maxValueText;
	private GuiAlignedText _curValueText;
	private int _value;
	private int _maxValue;
	private IValueChangeListener _valueChangedListener;

	public void setValue( int value )
	{
		_value = value;
		updateDimensions();
	}
	public void setMaxValue( int value )
	{
		_maxValue = value;
		_maxValueText.setText( Integer.toString( value ) );
		updateDimensions();
	}
	public void setOnValueChanged( IValueChangeListener listener )
	{
		_valueChangedListener = listener;
	}

	public int getValue() { return _value; }
	public int getMaxValue() { return _maxValue; }

	@Override
	public void setBounds( Rect bounds )
	{
		super.setBounds( bounds );
		updateDimensions();
	}

	public GuiSlider()
	{
		super();
	}

	public GuiSlider( Rect bounds )
	{
		super( bounds );
	}

	@Override
	protected void initialize()
	{
		super.initialize();

		_minValueText = new GuiAlignedText( "0" );
		_minValueText.setHorizontalAlignment( Alignment.Near );

		_maxValueText = new GuiAlignedText( "0" );
		_maxValueText.setHorizontalAlignment( Alignment.Far );

		_curValueText = new GuiAlignedText( "0" );
		_curValueText.setHorizontalAlignment( Alignment.Center );

		_value = 0;
		_maxValue = 0;

		updateDimensions();
	}

	private void updateDimensions()
	{
		int indentY = _bounds.height() / 2 - SLIDER_INDENT_HEIGHT / 2;
		_indentBounds = Utils.makeRect( _bounds.left, _bounds.top + indentY, _bounds.width(), SLIDER_INDENT_HEIGHT );
		_padBounds = Utils.makeRect( _bounds.left, _bounds.top, SLIDER_PAD_WIDTH, _bounds.height() );

		_minValueText.setBounds( Utils.makeRect( _bounds.left, _bounds.top - 64, 64, 64 ) );
		_maxValueText.setBounds( Utils.makeRect( _bounds.right - 64, _bounds.top - 64, 64, 64 ) );

		updatePad();
	}

	private void updatePad()
	{
		float padPercentage = (float)_value / (float)_maxValue;
		float padOffset = padPercentage * (float)_bounds.width();
		int center = _bounds.left + (int)padOffset;
		_padBounds.left = center - SLIDER_PAD_WIDTH / 2;
		_padBounds.right = _padBounds.left + SLIDER_PAD_WIDTH;

		Alignment halign = Alignment.Center;
		Rect curBounds = Utils.makeRect( _padBounds.left - 32, _padBounds.top - 96, 32*2 + SLIDER_PAD_WIDTH, 64 );
		if( curBounds.left < _bounds.left )
		{
			curBounds.left = _bounds.left;
			curBounds.right = curBounds.left + 64;
			halign = Alignment.Near;
		}
		else if( curBounds.right > _bounds.right )
		{
			curBounds.right = _bounds.right;
			curBounds.left = curBounds.right - 64;
			halign = Alignment.Far;
		}
		_curValueText.setBounds( curBounds );
		_curValueText.setText( Integer.toString( _value ) );
		_curValueText.setHorizontalAlignment( halign );
	}

	@Override
	protected void drawBackground()
	{
		Rendering.drawRect( _indentBounds, Color.GRAY, Color.BLACK );
	}

	@Override
	protected void drawChildren()
	{
		Rendering.drawRect( _padBounds, Color.CYAN, Color.BLACK );

		if( _value > 0 )
			_minValueText.draw();
		if( _value < _maxValue )
			_maxValueText.draw();
		_curValueText.draw();
	}

	@Override
	public boolean onTouch( MotionEvent e )
	{
		boolean result = super.onTouch( e );

		if( result )
		{
			int oldValue = _value;

			float percentage = (float)( e.getX() - _bounds.left ) / (float)_bounds.width();
			float potentialValue = percentage * _maxValue;
			_value = Math.round( potentialValue );

			updatePad();

			if( oldValue != _value )
			{
				if( _valueChangedListener != null )
					_valueChangedListener.onValueChanged( oldValue, _value );
			}
		}

		return result;
	}

	public void setCurrentValueTextColor( int color )
	{
		_curValueText.getPaint().setColor( color );
	}
}
