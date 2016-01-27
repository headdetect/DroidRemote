package me.mrlopez.droidremote.tv.networking;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import me.mrlopez.droidremote.core.TCPClient;
import me.mrlopez.droidremote.core.Packet;
import me.mrlopez.droidremote.core.PacketQue;

public class TVClient extends TCPClient {


	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================
	
	private static OnVideoListener mListener;

	// ===========================================================
	// Constructors
	// ===========================================================
	
	public TVClient(Socket s) throws IOException {
		super(s);
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public static void setOnVideoReceivedListener(OnVideoListener mListener){
		TVClient.mListener = mListener;
	}
	
	public PacketQue getPacketQueue() {
		return mPacketQueue;
	}
	
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	
	@Override
	protected void onReceivePacket(Packet packet) {
		if(packet.id == 0x09){
			PacketVideo video = (PacketVideo) packet;
			
			if(mListener != null){
				mListener.onVideoReceived(this, video.getVideo(), video.getVideoLength());
			}
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
	public static TVClient connect(String address) throws IOException {
		InetAddress localAddress = InetAddress.getByName(address);
		InetSocketAddress localSocketAddress = new InetSocketAddress(localAddress, 45903);

		Socket socket = new Socket();
		socket.connect(localSocketAddress, 5000);

		return new TVClient(socket);
	}
	
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
	
	public interface OnVideoListener {
		
		void onVideoReceived(TVClient client, String name, String length);
	}


}


