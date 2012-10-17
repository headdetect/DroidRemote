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
package com.headdetect.computerremote.Utils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * The Class ServerUtils.
 */
public class ServerUtils {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	/** The m listener. */
	private static DiscoverComputers mListener;

	/** The keep searching. */
	private static boolean keepSearching = true;

	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	
	/**
	 * Gets the listener.
	 *
	 * @return the listener
	 */
	public static DiscoverComputers getListener() {
		return mListener;
	}

	/**
	 * Sets the listener.
	 *
	 * @param mListener the new listener
	 */
	public static void setListener(DiscoverComputers mListener) {
		ServerUtils.mListener = mListener;
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================
	

	/**
	 * Gets the computers on network. Using the network protocol made by
	 * headdetect.
	 * 
	 * @return the computers on network
	 */
	public static void getComputersOnNetwork() {
		InetAddress ia = null;
		byte[] buffer = new byte[0xFF]; // Just incase the name of the computer
										// is 255 chars long ... (pokerface)
		DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
		int port = 5000;

		try {
			ia = InetAddress.getByName("224.0.2.60");
		} catch (UnknownHostException e) {
			Logger.log(e);
		}

		try {
			MulticastSocket ms = new MulticastSocket(port);
			ms.joinGroup(ia);

			while (keepSearching) {
				ms.receive(dp);
				byte[] data = dp.getData();

				String concat = new String(data, "UTF8").trim();

				mListener.OnDiscover(new Computer(concat, ((InetSocketAddress) dp.getSocketAddress()).getAddress()));
			}
			
			ms.leaveGroup(ia);

		} catch (SocketException se) {
			Logger.log(se);
		} catch (IOException ie) {
			Logger.log(ie);
		}

	}

	/**
	 * If getComputersOnNetwork() was called, it will keep running until this method is called.
	 */
	public static void stopSearch() {
		keepSearching = false;
	}

	/**
	 * Ip to bytes.
	 *
	 * @param i the i
	 * @return the byte[]
	 */
	public static byte[] ipToBytes(int i) {
		return new byte[] { (byte) (i & 0xFF), (byte) ((i >> 8) & 0xFF), (byte) ((i >> 16) & 0xFF), (byte) ((i >> 24) & 0xFF) };
	}

	/**
	 * Ip to string.
	 *
	 * @param i the i
	 * @return the string
	 */
	public static String ipToString(byte[] i) {
		String d = "";
		for (int j = 0; j < i.length; j++)
			d += (i[j] & 0xFF) + (j == i.length - 1 ? "" : ".");
		return d;
	}

	/**
	 * Checks if is using wifi.
	 *
	 * @param c the c
	 * @return true, if is using wifi
	 */
	public static boolean isUsingWifi(Context c) {
		ConnectivityManager connManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		return mWifi.isConnected();
	}
	
	/**
	 * Gets the local ip.
	 *
	 * @param c the c
	 * @return the local ip
	 */
	public static String getLocalIp(Context c){
		if(!isUsingWifi(c)){
			throw new NullPointerException("Wifi must be enabled");
		}
		
		WifiManager wifi = (WifiManager)c.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wInfo = wifi.getConnectionInfo();
		return ipToString(ipToBytes(wInfo.getIpAddress()));
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

	/**
	 * The Interface DiscoverComputers. Used when a computer object is discovered
	 */
	public interface DiscoverComputers {

		/**
		 * On discover.
		 * 
		 * @param c
		 *            the computer that was found
		 */
		void OnDiscover(Computer c);
	}

}
