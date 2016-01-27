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
package me.mrlopez.droidremote.core.utils;

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
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;


/**
 * The Class ServerUtils.
 */
public class ServerUtils {

	// ===========================================================
	// Constants
	// ===========================================================

    // Remote Lib Protocol & TCP //
    private static final String DNS_SERVICE = "_workstation._tcp.local.";

	// ===========================================================
	// Fields
	// ===========================================================

	/** The m listener. */
	private static DiscoverComputers mListener;

	/** The keep searching. */
	private static boolean keepSearching = true;

    //private static JmDNS mJmDNS;



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
	 * headdetect. Or something like that
	 * 
	 * @return the computers on network
	 */
	public static void getComputersOnNetwork() {
		/*// Create the NsdServiceInfo object, and populate it.
		NsdServiceInfo serviceInfo  = new NsdServiceInfo();

		// The name is subject to change based on conflicts
		// with other services advertised on the same network.
		serviceInfo.setServiceName("NsdChat");
		serviceInfo.setServiceType("_http._tcp.");
		serviceInfo.setPort(port);

        WifiManager.MulticastLock multicastLock  = mWifi.createMulticastLock("Log_Tag");
        multicastLock.setReferenceCounted(true);
        multicastLock.acquire();
        ServiceListener listener = null;
        try {
            mJmDNS = JmDNS.create();
            mJmDNS.addServiceListener(DNS_SERVICE, listener = new ServiceListener() {
                @Override
                public void serviceAdded(ServiceEvent event) {
                    // Required to force serviceResolved to be called again
                    // (after the first search)
                    mJmDNS.requestServiceInfo(event.getType(), event.getName(), 1);
                }

                @Override
                public void serviceRemoved(ServiceEvent event) {

                }

                @Override
                public void serviceResolved(ServiceEvent event) {
                    mListener.OnDiscover(new Computer(event.getName(), event.getInfo().getInetAddresses()[0]));
                }
            });

			mRegistrationListener = new NsdManager.RegistrationListener() {

				@Override
				public void onServiceRegistered(NsdServiceInfo NsdServiceInfo) {
					// Save the service name.  Android may have changed it in order to
					// resolve a conflict, so update the name you initially requested
					// with the name Android actually used.
					mServiceName = NsdServiceInfo.getServiceName();
				}

				@Override
				public void onRegistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {
					// Registration failed!  Put debugging code here to determine why.
				}

				@Override
				public void onServiceUnregistered(NsdServiceInfo arg0) {
					// Service has been unregistered.  This only happens when you call
					// NsdManager.unregisterService() and pass in this listener.
				}

				@Override
				public void onUnregistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {
					// Unregistration failed.  Put debugging code here to determine why.
				}
			};

		} catch (IOException e) {
            e.printStackTrace();
        }


        ServiceInfo serviceInfo = ServiceInfo.create("_test._tcp.local.", "AndroidTest", 0, "plain test service from android");
        try {
            mJmDNS.registerService(serviceInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (keepSearching) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        multicastLock.release();
        mJmDNS.removeServiceListener(DNS_SERVICE, listener);
        try {
            mJmDNS.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
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
			d += (i[j] & 0xFF) + (j == i.length - 1 ? "" : "");
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
