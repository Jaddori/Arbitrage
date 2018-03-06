package com.spacecat.arbitrage;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Nix on 2018-03-06.
 */

public class GameView extends View
{
	ArrayList<UIBase> elements;

	public GameView( Context context )
	{
		super( context );
	}

	public void initialize()
	{
		elements = new ArrayList<>();

		UILabel label = new UILabel( new Rect( 32, 32, 32, 32 ) );
		label.setText( "Label" );

		UIButton button = new UIButton( new Rect( 32, 64, 256, 128 ), "Button" );
		button.getPanel().rounded = true;
		button.getPanel().setCurves( new Vec2( 16.0f, 16.0f ) );

		elements.add( label );
		elements.add( button );

		for( UIBase element : elements )
		{
			element.initialize( getResources() );
		}
	}

	@Override
	protected void onDraw( Canvas canvas )
	{
		super.onDraw( canvas );

		canvas.drawColor( Color.BLUE );

		for( UIBase element : elements )
		{
			element.draw( canvas );
		}
	}
}
