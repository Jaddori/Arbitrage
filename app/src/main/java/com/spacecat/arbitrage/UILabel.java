package com.spacecat.arbitrage;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Nix on 2018-03-06.
 */

public class UILabel extends UIBase
{
	protected Rect bounds;
	protected String text;
	protected Rect textBounds;
	protected Vec2 textPosition;
	protected Alignment horizontalAlignment;
	protected Alignment verticalAlignment;
	protected Paint textPaint;

	public void setBounds( Rect bounds )
	{
		this.bounds = bounds;
		calculateTextPosition();
	}

	public void setText( String text )
	{
		this.text = text;
		calculateTextBounds();
		calculateTextPosition();
	}

	public void setAlignment( Alignment horizontal, Alignment vertical )
	{
		horizontalAlignment = horizontal;
		verticalAlignment = vertical;
		calculateTextPosition();
	}

	public Rect getBounds() { return bounds; }
	public String getText() { return text; }
	public Alignment getHorizontalAlignment() { return horizontalAlignment; }
	public Alignment getVerticalAlignment() { return verticalAlignment; }

	public UILabel()
	{
		bounds = new Rect( 0, 0, 0, 0 );
		textBounds = new Rect();
		textPosition = new Vec2();
		text = "";
	}

	public UILabel( Rect bounds )
	{
		this.bounds = bounds;
		textBounds = new Rect();
		textPosition = new Vec2();
		text = "";
	}

	public UILabel( Rect bounds, String text )
	{
		this.bounds = bounds;
		textBounds = new Rect();
		textPosition = new Vec2();
		setText( text );
	}

	@Override
	public void initialize( Resources resources )
	{
		textPaint = new Paint();
		textPaint.setColor( Color.WHITE );
		textPaint.setTextSize( 32.0f );

		calculateTextBounds();
		calculateTextPosition();
	}

	@Override
	public void draw( Canvas canvas )
	{
		canvas.drawText( text, textPosition.x, textPosition.y, textPaint );
	}

	protected void calculateTextBounds()
	{
		if( textPaint != null )
			textPaint.getTextBounds( text, 0, text.length(), textBounds );
	}

	protected void calculateTextPosition()
	{
		float x = bounds.left;
		if( horizontalAlignment == Alignment.Center )
			x = bounds.centerX() - textBounds.width() * 0.5f;
		else if( horizontalAlignment == Alignment.Far )
			x = bounds.right - textBounds.width();

		float y = bounds.top + textBounds.height();
		if( verticalAlignment == Alignment.Center )
			y = bounds.centerY() + textBounds.height() * 0.5f;
		else if( verticalAlignment == Alignment.Far )
			y = bounds.bottom;

		textPosition.x = x;
		textPosition.y = y;
	}
}
