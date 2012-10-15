package com.headdetect.computerremote.Utils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

public class ServerUtils {

	public static DiscoverComputers mListener;
	
	private static boolean search = true;

	public static void getComputersOnNetwork(WifiManager mWifi) {
		InetAddress ia = null;
		byte[] buffer = new byte[0xFF];
		DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
		int port = 5000;

		try {
			ia = InetAddress.getByName("224.0.2.60");
		} catch (UnknownHostException e) {
			System.err.println(e);
		}

		try {
			MulticastSocket ms = new MulticastSocket(port);
			ms.joinGroup(ia);

			while (search) {
				ms.receive(dp);
				byte[] data = dp.getData();

				String concat = new String(data, "ASCII");

				mListener.OnDiscover(new Computer(concat, ((InetSocketAddress)dp.getSocketAddress()).getAddress()));
			}

		} catch (SocketException se) {
			System.err.println(se);
		} catch (IOException ie) {
			System.err.println(ie);
		}

	}
	
	public static void stopSearch(){
		search = false;
		
	}

	public static byte[] ipToBytes(int i) {
		return new byte[] { (byte) (i & 0xFF), (byte) ((i >> 8) & 0xFF), (byte) ((i >> 16) & 0xFF), (byte) ((i >> 24) & 0xFF) };
	}

	public static String ipToString(byte[] i) {
		String d = "";
		for (int j = 0; j < i.length; j++)
			d += (i[j] & 0xFF) + (j == i.length - 1 ? "" : ".");
		return d;
	}

	public static boolean isUsingWifi(Context c) {
		ConnectivityManager connManager = (ConnectivityManager) c.getSystemService(Service.CONNECTIVITY_SERVICE);
		NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		return mWifi.isConnected();
	}


	public interface DiscoverComputers {
		void OnDiscover(Computer c);
	}

}
