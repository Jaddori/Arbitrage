package com.spacecat.arbitrage;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Nix on 2018-03-06.
 */

public class UIPanel extends UIBase
{
	protected Rect bounds;
	protected Paint backgroundPaint;
	protected Paint foregroundPaint;
	protected boolean rounded;
	protected Vec2 curves;

	public void setBounds( Rect bounds ) { this.bounds = bounds; }
	public void setRounded( boolean rounded ) { this.rounded = rounded; }
	public void setCurves( Vec2 curves ) { this.curves = curves; }

	public Rect getBounds() { return bounds; }
	public boolean getRounded() { return rounded; }
	public Vec2 getCurvers() { return curves; }

	public UIPanel()
	{
		bounds = new Rect( 0, 0, 0, 0 );
		rounded = false;
		curves = new Vec2();
	}

	public UIPanel( Rect bounds )
	{
		this.bounds = bounds;
		rounded = false;
		curves = new Vec2();
	}

	@Override
	public void initialize( Resources resources )
	{
		backgroundPaint = new Paint();
		backgroundPaint.setColor( Color.BLACK );
		foregroundPaint = new Paint();
		foregroundPaint.setColor( Color.WHITE );
		foregroundPaint.setStyle( Paint.Style.STROKE );
		foregroundPaint.setStrokeWidth( 4.0f );
	}

	@Override
	public void draw( Canvas canvas )
	{
		if( rounded )
		{
			canvas.drawRoundRect( bounds.left, bounds.top, bounds.right, bounds.bottom, curves.x, curves.y, backgroundPaint );
			canvas.drawRoundRect( bounds.left, bounds.top, bounds.right, bounds.bottom, curves.x, curves.y, foregroundPaint );
		}
		else
		{
			canvas.drawRect( bounds, backgroundPaint );
			canvas.drawRect( bounds, foregroundPaint );
		}
	}
}
