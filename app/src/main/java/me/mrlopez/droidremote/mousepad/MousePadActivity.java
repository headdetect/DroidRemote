package me.mrlopez.droidremote.mousepad;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Toast;

import me.mrlopez.droidremote.R;
import me.mrlopez.droidremote.mousepad.MouseButton.ButtonPosition;
import me.mrlopez.droidremote.mousepad.networking.MouseClient;

public class MousePadActivity extends Activity implements OnClickListener , OnTouchListener {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private MouseButton mButtonLeft , mButtonRight;
	private View mousepad;

	private MouseClient mClient;

	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public void onCreate( Bundle bundle ) {
		super.onCreate( bundle );
		this.setContentView( R.layout.activity_mousepad );

		final Intent intent = getIntent();

		if ( intent != null ) {

			String address = intent.getStringExtra( "IP" );

			if ( address != null ) {
				new SetupMouse().execute( address );
			}
		} else {
			Toast.makeText( this , "Critical Error occurred while setting up" , Toast.LENGTH_LONG ).show();
		}

		mButtonLeft = (MouseButton) findViewById( R.id.btnMouseLeft );
		mButtonRight = (MouseButton) findViewById( R.id.btnMouseRight );

		mButtonLeft.buttonPosition = MouseButton.ButtonPosition.Left;
		mButtonLeft.setOnClickListener( this );

		mButtonRight.buttonPosition = MouseButton.ButtonPosition.Right;
		mButtonRight.setOnClickListener( this );

		mousepad = findViewById( R.id.mousepad );
		mousepad.setOnTouchListener( this );
	}

	@Override
	public void onClick( View view ) {
		if ( view.getId() == R.id.btnMouseLeft || view.getId() == R.id.mousepad ) {
			mClient.sendClick( ButtonPosition.Left );
		} else if ( view.getId() == R.id.btnMouseRight ) {
			mClient.sendClick( ButtonPosition.Right );
		}
	}

	private long lastUpdate;

	@Override
	public boolean onTouch( View view , MotionEvent event ) {
		if ( lastUpdate == 0 )
			lastUpdate = System.currentTimeMillis();

		if ( event.getAction() == MotionEvent.ACTION_MOVE ) {
			if ( System.currentTimeMillis() - lastUpdate < 10 )
				return true;

			float x = event.getX( 0 );
			float y = event.getY( 0 );

			mClient.updatePosition( x , y );
		}
		
		if(event.getAction() == MotionEvent.ACTION_UP){
			mClient.mouseUp();
		}
		return true;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

	/**
	 * The Class SetupChat.
	 */
	private class SetupMouse extends AsyncTask< String , Void , Boolean > {

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected Boolean doInBackground( String... strings ) {

			try {
				DatagramSocket clientSocket = new DatagramSocket();
				InetAddress address = InetAddress.getByName( strings[0].substring(1, strings[0].length()) );
				mClient = new MouseClient( clientSocket , address );
				if ( mClient != null ) {
					new Thread( mClient ).start();
				}

			} catch ( UnknownHostException e ) {
				e.printStackTrace();
				return true;
			} catch ( IOException e ) {
				e.printStackTrace();
				return true;
			}

			return false;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute( Boolean errors ) {

			if ( errors ) {
				Toast.makeText( getApplicationContext() , "Something went wrong while trying to set up the mouse" , Toast.LENGTH_LONG ).show();
				finish();
			}

		}
	}

}
