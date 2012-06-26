package com.headdetect.computerremote;

import java.net.Socket;
import com.headdetect.computerremote.Networking.PacketHandler;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class ComputerRemoteActivity extends Activity {

	private Socket mSocket;

	private PacketHandler mHandler;

	private boolean connected;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		((Button) findViewById(R.id.btnPower)).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (!connected)
					return;

			}
		});
	}

}