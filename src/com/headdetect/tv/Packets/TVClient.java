package com.headdetect.tv.Packets;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import com.headdetect.computerremote.Networking.Client;
import com.headdetect.computerremote.Networking.Packet;
import com.headdetect.computerremote.Networking.PacketQue;

public class TVClient extends Client {


	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================
	
	private OnVideoListener mListener;

	// ===========================================================
	// Constructors
	// ===========================================================
	
	public TVClient(Socket s) throws IOException {
		super(s);
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public void setOnVideoRecievedListener(OnVideoListener mListener){
		this.mListener = mListener;
	}
	
	public PacketQue getPacketQueue() {
		return mPacketQueue;
	}
	
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	
	@Override
	protected void onRecievePacket(Packet packet) {
		if(packet.id == 0x09){
			PacketVideo video = (PacketVideo) packet;
			
			if(mListener != null){
				mListener.onVideoRecieved(video.getVideo(), video.getVideoLength());
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
		TVClient client = new TVClient(socket);

		return client;
	}
	
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
	
	public interface OnVideoListener {
		
		void onVideoRecieved(String name, String length);
	}


}


