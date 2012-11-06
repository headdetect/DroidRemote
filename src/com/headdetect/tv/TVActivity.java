package com.headdetect.tv;

import java.io.IOException;
import java.net.UnknownHostException;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.headdetect.chat.Networking.Client;

public class TVActivity extends Activity {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================
	
	private ListView mList;
	
	private Client mClient;
	
	private TVListAdapter mListAdapter;

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
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		
		mList = new ListView(this);
		mList.setAdapter(mListAdapter);
		this.setContentView(mList);
		
		
		final Intent intent = getIntent();

		if (intent != null) {

			String address = intent.getStringExtra("IP");

			if (address != null) {
				new SetupTV().execute(address);
			}
		} else {
			Toast.makeText(this, "Critical Error occurred while setting up", Toast.LENGTH_LONG).show();
		}
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
	private class SetupTV extends AsyncTask<String, Void, Boolean> {

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected Boolean doInBackground(String... arg0) {

			try {

				mClient = Client.connect(arg0[0].substring(1, arg0[0].length()));
				new Thread(mClient).run();

			} catch (UnknownHostException e) {
				e.printStackTrace();
				return true;
			} catch (IOException e) {
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
		protected void onPostExecute(Boolean errors) {

			if (errors) {
				Toast.makeText(getApplicationContext(), "Something went wrong while trying to set up chat", Toast.LENGTH_LONG).show();
				finish();
			} else {
				if (mClient != null) {
					try {
						mClient.sendMessage("hi Computer");
					} catch (IOException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		}
	}

}
