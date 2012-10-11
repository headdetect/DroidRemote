package com.headdetect.computerremote.Utils;

import java.net.InetAddress;
import java.net.Socket;

import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.headdetect.computerremote.Networking.Packet;
import com.headdetect.computerremote.Networking.packets.PacketInfo;

public class ServerUtils {

	public static DiscoverComputers mListener;

	public static void getComputersOnNetwork(WifiManager mWifi) {
		try {
			byte[] ip = ipToBytes(mWifi.getDhcpInfo().gateway);
			for (int i = 2; i < 255; i++) {
				ip[3] = (byte)i;
				Log.d("Remote", "Pinging: " + ipToString(ip));
				InetAddress add = InetAddress.getByAddress(ip);
				if (add.isReachable(400)) {
					if (mListener != null) {
						String name = getServerName(add);
						if (name != null)
							mListener.OnDiscover(new Computer(name, add));
					}
					Log.d("Remote", ipToString(ip) + " was a success");
				}
				if(i == 255)
					Log.d("WTF", "ITS 255");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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

	public static String getServerName(InetAddress s) {
		try {
			Socket sd = new Socket(s.getHostAddress(), 45903);
			sd.setSoTimeout(1000);
			Packet.QuickSend(sd, new PacketInfo());
			PacketInfo info = (PacketInfo) Packet.QuickRead(sd, 5);
			return info.getCompName();
		} catch (Exception e) {
			return null;
		}
	}

	public interface DiscoverComputers {
		void OnDiscover(Computer c);
	}

}
