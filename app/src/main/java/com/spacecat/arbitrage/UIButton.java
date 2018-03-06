package com.spacecat.arbitrage;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Nix on 2018-03-06.
 */

public class UIButton extends UIBase
{
	protected UIPanel panel;
	protected UILabel label;

	public UIPanel getPanel() { return panel; }
	public UILabel getLabel() { return label; }

	public UIButton( Rect bounds, String text )
	{
		panel = new UIPanel( bounds );
		label = new UILabel( bounds, text );
	}

	@Override
	public void initialize( Resources resources )
	{
		panel.initialize( resources );
		label.initialize( resources );
	}

	@Override
	public void draw( Canvas canvas )
	{
		panel.draw( canvas );
		label.draw( canvas );
	}
}
