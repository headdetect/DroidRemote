package me.mrlopez.droidremote.mousepad;

import me.mrlopez.droidremote.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MouseButton extends View {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	public ButtonPosition buttonPosition;

	private final OnTouchListener mListener = new OnTouchListener() {

		@Override
		public boolean onTouch( View mView , MotionEvent event ) {
			if ( event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE ) {
				switch ( buttonPosition ) {
					case Left:
						mView.setBackgroundResource( R.drawable.btn_mouse_left_down );
						break;
					case Right:
						mView.setBackgroundResource( R.drawable.btn_mouse_right_down );
						break;
				}
			} else {
				switch ( buttonPosition ) {
					case Left:
						mView.setBackgroundResource( R.drawable.btn_mouse_left );
						break;
					case Right:
						mView.setBackgroundResource( R.drawable.btn_mouse_right );
						break;
				}
			}

			return true;
		}
	};

	// ===========================================================
	// Constructors
	// ===========================================================

	public MouseButton( Context context , AttributeSet attrs , int defStyle ) {
		super( context , attrs , defStyle );
		this.setOnTouchListener( mListener );
	}

	public MouseButton( Context context , AttributeSet attrs ) {
		super( context , attrs );
		this.setOnTouchListener( mListener );
	}

	public MouseButton( Context context ) {
		super( context );
		this.setOnTouchListener( mListener );
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

	public enum ButtonPosition {
		Left , Right
		// Center

	}

}
