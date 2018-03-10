package com.spacecat.arbitrage;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;

/**
 * Created by Nix on 2018-03-10.
 */

public class City
{
	private String name;
	private Vec2 position;
	private Vec2 size;
	private Paint paint;
	private UILabel nameLabel;

	public void setName( String name ) { this.name = name; nameLabel.setText( name ); }
	public void setPosition( Vec2 position )
	{
		this.position = position;
		nameLabel.setBounds( new Rect( (int)position.x, (int)position.y, (int)position.x, (int)position.y ) );
	}
	public void setSize( Vec2 size ) { this.size = size; }

	public String getName() { return name; }
	public Vec2 getPosition() { return position; }
	public Vec2 getSize() { return size; }
	public Paint getPaint() { return paint; }

	public City()
	{
		name = "";
		position = new Vec2();
		size = new Vec2();
		paint = new Paint();

		nameLabel = new UILabel( new Rect() );
	}

	public void initialize()
	{
		paint.setColor( Color.RED );

		nameLabel.initialize();
		nameLabel.getTextPaint().setTextSize( 64.0f );
	}

	public void draw( Canvas canvas )
	{
		canvas.drawRect( new RectF( position.x, position.y, position.x+size.x, position.y+size.y ), paint );

		nameLabel.draw( canvas );
	}

	public boolean onTouch( MotionEvent e )
	{
		boolean result = Utils.insideRect( position, size, e.getX(), e.getY() );;

		if( e.getActionMasked() == MotionEvent.ACTION_DOWN )
		{
			if( result )
				paint.setColor( Color.YELLOW );
		}
		else if( e.getActionMasked() == MotionEvent.ACTION_UP )
		{
			if( result )
				paint.setColor( Color.GREEN );
			else
				paint.setColor( Color.RED );
		}

		return result;
	}
}
