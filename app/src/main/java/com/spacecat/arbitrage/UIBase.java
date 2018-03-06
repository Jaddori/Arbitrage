package com.spacecat.arbitrage;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Nix on 2018-03-04.
 */

public abstract class UIBase
{
	public abstract void initialize( Resources resources );
	public abstract void draw( Canvas canvas );
}
