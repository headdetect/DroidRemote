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
package com.remotedroid.app.networking;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import com.remotedroid.core.TCPClient;
import com.remotedroid.core.Packet;


/**
 * The Class CPClient.
 */
public class CommandingClient extends TCPClient {



	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	// ===========================================================
	// Constructors
	// ===========================================================
	
	/**
	 * Instantiates a new Control Panel type client.
	 *
	 * @param s the socket it connects from
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public CommandingClient(Socket s) throws IOException {
		super(s);
	}
	
	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	
	/* (non-Javadoc)
	 * @see com.headdetect.computerremote.Networking.Client#onRecievePacket(com.headdetect.computerremote.Networking.Packet)
	 */
	@Override
	protected void onRecievePacket(Packet packet) {
		// TODO Auto-generated method stub

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
	public static CommandingClient connect(String address) throws IOException {
		InetAddress localAddress = InetAddress.getByName(address);
		InetSocketAddress localSocketAddress = new InetSocketAddress(localAddress, 45903);

		Socket socket = new Socket();
		socket.connect(localSocketAddress, 5000);
		CommandingClient client = new CommandingClient(socket);

		return client;
	}
	
	/**
	 * Send a command.
	 * 
	 * @param message
	 *            the message
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws Exception
	 *             the exception
	 */
	public void sendCommand(String cmd) throws IOException, Exception {
		if (cmd == null || cmd.isEmpty() || !mPacketQueue.isRunning())
			return;

		mPacketQueue.sendPacket(new PacketCommand(cmd));
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
