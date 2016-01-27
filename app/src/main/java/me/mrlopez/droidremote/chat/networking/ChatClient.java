package me.mrlopez.droidremote.chat.networking;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import me.mrlopez.droidremote.app.networking.PacketMessage;
import me.mrlopez.droidremote.chat.ChatListener;
import me.mrlopez.droidremote.core.TCPClient;
import me.mrlopez.droidremote.core.Packet;

public class ChatClient extends TCPClient {


	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	/** The chat listener. */
	private static ChatListener chatListener;
	
	// ===========================================================
	// Constructors
	// ===========================================================	
	
	public ChatClient(Socket s) throws IOException {
		super(s);
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	

	/**
	 * Sets the chat listener.
	 * 
	 * @param listener
	 *            the new on chat listener
	 */
	public static void setOnChatListener(ChatListener listener) {
		chatListener = listener;
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	
	@Override
	protected void onReceivePacket(Packet packet) {
		if(packet.getClass().isAssignableFrom(PacketMessage.class)){
		
			PacketMessage msg = (PacketMessage)packet;
			if(chatListener != null){
				chatListener.onChat(msg.getMessage());
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
	public static ChatClient connect(String address) throws IOException {
		InetAddress localAddress = InetAddress.getByName(address);
		InetSocketAddress localSocketAddress = new InetSocketAddress(localAddress, 45903);

		Socket socket = new Socket();
		socket.connect(localSocketAddress, 5000);
		ChatClient client = new ChatClient(socket);

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
		if (message == null || message.isEmpty() || !mPacketQueue.isRunning())
			return;

		mPacketQueue.sendPacket(new PacketMessage(message));
	}


	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}


