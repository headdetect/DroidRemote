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

package com.headdetect.computerremote.chat;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.headdetect.computerremote.R;
import com.headdetect.computerremote.Networking.Client;
import com.headdetect.computerremote.chat.ChatItem.FloatDirection;
import com.headdetect.computerremote.chat.Listeners.ChatListener;
import com.headdetect.computerremote.chat.Listeners.ConnectionListener;

/**
 * The Class ChatClientActivity.
 */
public class ChatClientActivity extends Activity {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private ChatListAdapter chatAdapter;

	private Button btnSendMessage;

	private EditText txtMessage;

	private ListView lstMessages;

	private Client mClient;

	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);

		chatAdapter = new ChatListAdapter(this, new ArrayList<ChatItem>());

		lstMessages = (ListView) findViewById(R.id.lstChat);
		lstMessages.setAdapter(chatAdapter);

		txtMessage = (EditText) findViewById(R.id.txtSay);
		txtMessage.setOnEditorActionListener(txtMessageEditorActionListener);

		btnSendMessage = (Button) findViewById(R.id.btnSend);
		btnSendMessage.setOnClickListener(btnSendMessageClickListener);

		Client.setOnChatListener(chatListener);
		Client.setOnConnectionListener(connectionListener);

		final Intent intent = getIntent();

		if (intent != null) {

			String address = intent.getStringExtra("IP");

			if (address != null) {
				new SetupChat().execute(address);
			}
		} else {
			Toast.makeText(this, "Critical Error occurred while setting up", Toast.LENGTH_LONG).show();
		}

	}

	// ===========================================================
	// Methods
	// ===========================================================

	private final OnClickListener btnSendMessageClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			sendMessage();
		}
	};

	private final OnEditorActionListener txtMessageEditorActionListener = new OnEditorActionListener() {

		@Override
		public boolean onEditorAction(TextView v, int id, KeyEvent event) {
			if (id == EditorInfo.IME_ACTION_NEXT || id == EditorInfo.IME_ACTION_DONE)
				sendMessage();
			return true;
		}
	};

	/** The chat listener. */
	private final ChatListener chatListener = new ChatListener() {

		@Override
		public void onChat(String message) {
			chatAdapter.addItem(new ChatItem("<html>" + message + "</html>", "Computer", FloatDirection.Right));
		}
	};

	/** The connection listener. */
	private final ConnectionListener connectionListener = new ConnectionListener() {

		@Override
		public void onDisconnect(Client client) {
			chatAdapter.addItem(new ChatItem("<html><i>You disconnected from the computer.</i></html>", ""));
		}

		@Override
		public void onJoin(Client client) {
			chatAdapter.addItem(new ChatItem("<html><i>You connected to the computer.</i></html>", ""));
		}
	};

	/**
	 * Send the message.
	 */
	public void sendMessage() {

		String message = txtMessage.getText().toString();

		if (message == null || message.isEmpty())
			return;

		message = message.replace(">", "&gt;");
		message = message.replace("<", "&lt;");

		try {
			if (mClient != null) {
				mClient.sendMessage(message);
				chatAdapter.addItem(new ChatItem(message, "You"));
			} else {
				return;
			}
		} catch (Exception e) {
			chatAdapter.addItem(new ChatItem(e.getMessage(), "<font color='red'>Error</font>"));
			return;
		}

		txtMessage.setText("");
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

	/**
	 * The Class SetupChat.
	 */
	private class SetupChat extends AsyncTask<String, Void, Boolean> {

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected Boolean doInBackground(String... arg0) {

			try {

				mClient = Client.connect(arg0[0].substring(1, arg0[0].length()));
				if (mClient != null) {
					new Thread(mClient).run();
				}
				
			} catch (Exception e) {
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