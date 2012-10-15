/*

﻿ *    Copyright 2012 Brayden (headdetect) Lopez
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

package com.headdetect.chat.Networking;

import static com.headdetect.chat.Utilities.Constants.PORT;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import com.headdetect.chat.Listeners.ChatListener;
import com.headdetect.chat.Listeners.ConnectionListener;
import com.headdetect.computerremote.Networking.PacketQue;
import com.headdetect.computerremote.Networking.packets.PacketMessage;

/**
 * The Class Client.
 */
public class Client implements Runnable {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private PacketQue mPacketQueue;

	/** The disconnecting. */
	private boolean disconnecting;

	/** The name. */
	private String name;

	/** The chat listener. */
	private static ChatListener chatListener;

	/** The connection listener. */
	private static ConnectionListener connectionListener;

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
	public Client(Socket s) throws IOException {
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
	 * Gets the client name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the client name.
	 * 
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the chat listener.
	 * 
	 * @param listener
	 *            the new on chat listener
	 */
	public static void setOnChatListener(ChatListener listener) {
		chatListener = listener;
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

		try {

			while (!disconnecting) {

				PacketMessage msg = (PacketMessage) mPacketQueue.getNextPacket();
				chatListener.onChat(msg.getMessage());
			}

		} catch (IOException e) {
			disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			disconnect();
		}
	}

	// ===========================================================
	// Methods
	// ===========================================================

	/**
	 * Connect to the specified address, and returns a client.
	 * 
	 * @param address
	 *            the address
	 * @return the connected client
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static Client connect(String address) throws IOException {
		InetAddress localAddress = InetAddress.getByName(address);
		InetSocketAddress localSocketAddress = new InetSocketAddress(localAddress, PORT);

		Socket socket = new Socket();
		socket.connect(localSocketAddress, 5000);
		Client client = new Client(socket);

		return client;
	}

	/**
	 * Send a message.
	 * 
	 * @param message
	 *            the message
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws Exception
	 *             the exception
	 */
	public void sendMessage(String message) throws IOException, Exception {
		if (message == null || message.isEmpty())
			return;

		mPacketQueue.sendPacket(new PacketMessage(message));
	}

	/**
	 * Disconnects the client.
	 */
	public void disconnect() {

		if (connectionListener != null) {
			connectionListener.onDisconnect(this);
		}
		
		mPacketQueue.close();

	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

}
