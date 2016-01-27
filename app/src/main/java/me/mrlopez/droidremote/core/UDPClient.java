/*
 *	Copyright 2013 Brayden (headdetect)
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
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * The Class Client.
 */
public abstract class UDPClient implements Runnable {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	/** The disconnecting. */
	protected boolean disconnecting;

	private InetAddress mAddress;
	
	private DatagramSocket mSocket;

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
	public UDPClient( DatagramSocket socket, InetAddress address ) throws IOException {
		mSocket = socket;
		mAddress = address;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	
	/**
	 * @return the mSocket
	 */
	public DatagramSocket getSocket() {
		return mSocket;
	}

	/**
	 * @param mSocket the mSocket to set
	 */
	public void setSocket( DatagramSocket mSocket ) {
		this.mSocket = mSocket;
	}
	
	public InetAddress getInetAddress() {
		return mAddress;
	}

	public void setInetAddress( InetAddress mAddress ) {
		this.mAddress = mAddress;
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	/**
	 * @see java.lang.Runnable#run().
	 * 
	 *      When looping make sure to check for connected as well as other
	 *      prereqs (if any).
	 * 
	 *      for example <b>Default:</b> <code>
	 * while(!disconnecting) 
	 * {
	 * 	// Insert packet code here 
	 * }
	 * </code> <b>Extended</b> <code>
	 * boolean allowRead = true;
	 * while(!disconnecting && allowRead)
	 * {
	 * 	// Insert packet code here
	 * 	if(somethingHappenedThatRequiresDisconnection && !disconnecting)
	 * 	{
	 * 		disconnect();
	 * 		break;
	 * 	}
	 * }
	 * </code>
	 * 
	 */
	@Override
	public abstract void run();

	// ===========================================================
	// Methods
	// ===========================================================

	/**
	 * Disconnects the client.
	 */
	public void disconnect() {
		disconnecting = true;
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

}
