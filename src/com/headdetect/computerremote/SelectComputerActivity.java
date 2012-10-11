package com.headdetect.computerremote;

import java.net.Socket;

import android.app.Activity;
import android.content.Context;
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

public class SelectComputerActivity extends Activity {

	private ListView mList;
	private ArrayAdapter<Computer> mAdapter;

	private TextView mLabel;
	private ProgressBar mProg;

	private ComputerList mLoader;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_select_computer);
		mList = (ListView) findViewById(R.id.lstComputers);
		mAdapter = new ArrayAdapter<Computer>(this, android.R.layout.simple_list_item_1);
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
	

	protected void connect(Computer item) {
		mLoader.cancel(true);
		try {
			//Socket mSocket = new Socket(item.IP.getHostAddress(), 45903);
			
			//TODO: stuff
		} catch (Exception e) {
			makeToast("Error connecting to computer");
		}
	}

	private void makeToast(final String message) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(SelectComputerActivity.this, message, Toast.LENGTH_LONG).show();
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu_select_computer, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.itmManual:
	            return true;
	        case R.id.itmRefresh:
	        	if(mProg.getVisibility() == View.VISIBLE)
	        		return true;
	        	mLabel.setText("Looking for servers...");
	        	mProg.setVisibility(View.VISIBLE);
	        	mAdapter.clear();
	        	
	        	if(mLoader != null){
	        		mLoader.cancel(true);
	        	}
	    		mLoader = new ComputerList();
	    		mLoader.execute();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	private class ComputerList extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... arg0) {

			WifiManager mWifi = (WifiManager) SelectComputerActivity.this.getSystemService(Context.WIFI_SERVICE);

			if (!mWifi.isWifiEnabled()) {
				makeToast("Wifi must be enabled!");
				return null;
			}

			ServerUtils.mListener = new DiscoverComputers() {

				@Override
				public void OnDiscover(final Computer c) {
					SelectComputerActivity.this.runOnUiThread(new Runnable() {

						@Override
						public void run() {
							mAdapter.add(c);
						}
					});

				}
			};
			ServerUtils.getComputersOnNetwork(mWifi);

			return null;
		}

		@Override
		protected void onPostExecute(Void parms) {

			mLabel.setText(mAdapter.isEmpty() ? " No servers found :(" : "");
			mProg.setVisibility(View.GONE);

		}
	}

}
