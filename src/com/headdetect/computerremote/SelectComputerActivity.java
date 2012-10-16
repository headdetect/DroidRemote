/*

﻿ *    Copyright 2012 Brayden (headdetect)
 *    
 *    Dual-licensed under the Educational Community License, Version 2.0 and
 *	the GNU General Public License Version 3 (the "Licenses"); you may
 *	not use this file except in compliance with the Licenses. You may
 *	obtain a copy of the Licenses at
 *
 *		http://www.opensource.org/licenses/ecl2.php
 *		http://www.gnu.org/licenses/gpl-3.0.html
 *
 *		Unless required by applicable law or agreed to in writing
 *	software distributed under the Licenses are distributed on an "AS IS"
 *	BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 *	or implied. See the Licenses for the specific language governing
 *	permissions and limitations under the Licenses.
 * 
 */
package com.headdetect.computerremote;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.headdetect.computerremote.Utils.Computer;
import com.headdetect.computerremote.Utils.ServerUtils;
import com.headdetect.computerremote.Utils.ServerUtils.DiscoverComputers;
import com.headdetect.computerremote.chat.ChatClientActivity;

/**
 * The Class SelectComputerActivity.
 */
public class SelectComputerActivity extends Activity {
	
	// ===========================================================
	// Constants
	// ===========================================================
	
	// ===========================================================
	// Fields
	// ===========================================================
	
	private ListView mList;
	
	private ArrayAdapter<Computer> mAdapter;

	private TextView mLabel;
	
	private ProgressBar mProg;

	private ComputerList mLoader;

	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	
	/**
	 * On create.
	 *
	 * @param savedInstanceState the saved instance state
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_computer_browser);
		
		mAdapter = new ArrayAdapter<Computer>(this, android.R.layout.simple_list_item_1);
		
		mList = (ListView) findViewById(R.id.lstComputers);
		mList.setAdapter(mAdapter);
		mList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				connect(mAdapter.getItem(arg2));
			}
		});

		mLabel = (TextView) findViewById(R.id.textView1);
		mProg = (ProgressBar) findViewById(R.id.progressBar1);

		mLoader = new ComputerList();
		mLoader.execute();

	}
	
	/**
	 * On create options menu.
	 *
	 * @param menu the menu
	 * @return true, if successful
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_select_computer, menu);
		return true;
	}

	/**
	 * On options item selected.
	 *
	 * @param item the item
	 * @return true, if successful
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.itmManual:
				
				//TODO: Dialog, then manual connectivity.
				
				return true;
			case R.id.itmRefresh:
				if (mProg.getVisibility() == View.VISIBLE)
					return true;
				mLabel.setText("Looking for servers...");
				mProg.setVisibility(View.VISIBLE);
				mAdapter.clear();

				if (mLoader != null) {
					mLoader.cancel(true);
				}
				mLoader = new ComputerList();
				mLoader.execute();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	// ===========================================================
	// Methods
	// ===========================================================
	
	/**
	 * Connect to the specified computer
	 *
	 * @param item
	 *            the computer to connect to
	 */
	public void connect(Computer item) {
		
		try {

			ServerUtils.stopSearch();
			mLoader.cancel(true);

			Intent sillyIntent = new Intent(SelectComputerActivity.this, ChatClientActivity.class);
			sillyIntent.putExtra("IP", item.getIp().toString());
			startActivity(sillyIntent);
			
			finish();
			
		} catch (Exception e) {
			e.printStackTrace();
			makeToast("Error connecting to computer");
		}
	}

	/**
	 * A quick method to show a toast notification. All toasts are shown for Toast.LENGTH_LONG
	 * @param message
	 *               The message to show
	 */
	public void makeToast(final String message) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(SelectComputerActivity.this, message, Toast.LENGTH_LONG).show();
			}
		});
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

	/**
	 * The Class ComputerList.
	 */
	private class ComputerList extends AsyncTask<Void, Void, Void> {

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected Void doInBackground(Void... arg0) {

			WifiManager mWifi = (WifiManager) SelectComputerActivity.this.getSystemService(Context.WIFI_SERVICE);

			if (!mWifi.isWifiEnabled()) {
				makeToast("Wifi must be enabled!");
				return null;
			}

			ServerUtils.setListener( new DiscoverComputers() {

				@Override
				public void OnDiscover(final Computer c) {
					
					for (int i = 0; i < mAdapter.getCount(); i++) {
						if (c.getName().equals(mAdapter.getItem(i).getName())) {
							return;
						}
					}
					
					SelectComputerActivity.this.runOnUiThread(new Runnable() {

						@Override
						public void run() {
							
							mAdapter.add(c);
							
						}
						
					});

				}
			} );
			ServerUtils.getComputersOnNetwork();

			return null;
		}

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(Void parms) {

			mLabel.setText(mAdapter.isEmpty() ? " No servers found :(" : "");
			mProg.setVisibility(View.GONE);
		}

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onCancelled()
		 */
		@Override
		protected void onCancelled() {
			ServerUtils.stopSearch();
		}
	}

	
}
