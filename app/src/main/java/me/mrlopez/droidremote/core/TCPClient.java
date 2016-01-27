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

package me.mrlopez.droidremote.core;

import java.io.IOException;
import java.net.Socket;

import me.mrlopez.droidremote.chat.ConnectionListener;

/**
 * The Class Client.
 */
public abstract class TCPClient implements Runnable {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	protected PacketQue mPacketQueue;

	/** The disconnecting. */
	protected boolean disconnecting;

	/** The connection listener. */
	private static ConnectionListener connectionListener;
	
	private Socket mSocket;

	// ===========================================================
	// Constructors
	// ===========================================================

	/**
	 * Instantiates a new client.
	 * 
	 * @param s
	 *            the socket
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public TCPClient(Socket s) throws IOException {
		this.mSocket = s;
		
		try {
			mPacketQueue = new PacketQue(s);
		} catch (IOException e) {
			disconnect();
			return;
		}

	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	
	/**
	 * Gets the socket.
	 *
	 * @return the socket
	 */
	public Socket getSocket(){
		return mSocket;
	}

	/**
	 * Sets the connection listener.
	 * 
	 * @param listener
	 *            the new on connection listener
	 */
	public static void setOnConnectionListener(ConnectionListener listener) {
		connectionListener = listener;
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		if (connectionListener != null) {
			connectionListener.onJoin(this);
		}
		
		mPacketQueue.start();

		try {

			while (!disconnecting) {

				Packet pack = mPacketQueue.getNextPacket();
				if (pack == null) {
					Thread.sleep(100);
					continue;
				}

				onRecievePacket(pack);
				
			}

		} catch (IOException e) {
			disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			disconnect();
		}
	}
	
	protected abstract void onRecievePacket(Packet packet);
	

	// ===========================================================
	// Methods
	// ===========================================================


	/**
	 * Disconnects the client.
	 */
	public void disconnect() {

		if (connectionListener != null) {
			connectionListener.onDisconnect(this);
		}

		mPacketQueue.close();
		
		try {
			mSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

}
