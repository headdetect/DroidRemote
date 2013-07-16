package com.remotedroid.tv;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.remotedroid.tv.networking.ControlType;
import com.remotedroid.tv.networking.PacketControl;
import com.remotedroid.tv.networking.PacketVideo;
import com.remotedroid.tv.networking.TVClient;
import com.remotedroid.tv.networking.TVClient.OnVideoListener;

public class TVActivity extends Activity {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private ListView mList;

	private TVClient mClient;

	private TVListAdapter mListAdapter;

	private OnVideoListener mListener = new OnVideoListener() {

		@Override
		public void onVideoRecieved(TVClient client, String name, String length) {
			if (mListAdapter != null) {
				mListAdapter.addItem(new TVItem(name, length));
			}
		}

	};
	
	
	private OnItemClickListener mClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			String uri = mListAdapter.getItem(arg2).getFile();
			
			if(mClient != null){
				try {
					mClient.getPacketQueue().sendPacket(new PacketControl(ControlType.Play, uri));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}
		
	};

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
		mListAdapter = new TVListAdapter(this, new ArrayList<TVItem>());
		
		mList.setAdapter(mListAdapter);
		mList.setOnItemClickListener(mClickListener);
		this.setContentView(mList);

		
		TVClient.setOnVideoRecievedListener(mListener);
		
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

				mClient = TVClient.connect(arg0[0].substring(1, arg0[0].length()));
				if (mClient != null) {
					new Thread(mClient).start();
				}

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
						mClient.getPacketQueue().sendPacket(new PacketVideo());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		}
	}

}
