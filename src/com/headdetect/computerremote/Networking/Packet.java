package com.headdetect.computerremote.Networking;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.nio.ByteBuffer;

import android.util.SparseArray;

import com.headdetect.computerremote.Networking.packets.PacketInfo;
import com.headdetect.computerremote.Networking.packets.PacketMessage;
import com.headdetect.computerremote.Networking.packets.PacketPing;
import com.headdetect.computerremote.Utils.ByteSwapper;

public abstract class Packet {

	static {
		packetMap = new SparseArray<Class<? extends Packet>>();
		registerPacket(PacketMessage.ID, PacketMessage.class);
		registerPacket(PacketPing.ID, PacketPing.class);
		registerPacket(PacketInfo.ID, PacketInfo.class);
	}

	public final int id;
	private static SparseArray<Class<? extends Packet>> packetMap;

	public static void registerPacket(int id, Class<? extends Packet> packet) {
		if (packetMap.get(id) != null) {
			throw new RuntimeException("Duplicate packet for ID " + id + "! (Attempting to register " + packet.getSimpleName() + " over " + packetMap.get(id).getSimpleName() + ")");
		}
		packetMap.put(id, packet);
	}

	public Packet(int id) {
		this.id = id;
	}

	public abstract void loadFromStream(DataInputStream stream) throws IOException;

	public abstract byte[] writeData() throws IOException;

	public static Packet getPacket(int id) throws IllegalArgumentException {
		Class<? extends Packet> packetClass = packetMap.get(id);
		if (packetClass == null)
			throw new IllegalArgumentException("Invalid packet ID: " + id);
		Constructor<? extends Packet> cons;
		Packet packet;
		try {
			cons = packetClass.getDeclaredConstructor(); // Default constructor
			packet = cons.newInstance();
			return packet;
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void QuickSend(Socket sd, Packet p) throws IOException {
		sd.getOutputStream().write(p.id);
		sd.getOutputStream().write(p.writeData());
	}

	public static Packet QuickRead(Socket sd, int id) throws IOException {
		DataInputStream mStream = new DataInputStream(sd.getInputStream());

		Packet e = getPacket(id);
		e.loadFromStream(mStream);
		return e;

	}

	// ---------------------------------------------------------------
	// - Tools -
	// ---------------------------------------------------------------

	public static String readString(DataInputStream mStream) throws IOException{
		short len = readShort(mStream);
		byte[] data = new byte[len];
		
		mStream.read(data);
		
		return new String(data, "ASCII");
	}
	
	public static int readInt(DataInputStream mStream) throws IOException{
		return ByteSwapper.swap(mStream.readInt());
	}
	
	public static short readShort(DataInputStream mStream) throws IOException{
		return ByteSwapper.swap(mStream.readShort());
	}
	
	
	
	public static byte[] getString(String string) throws UnsupportedEncodingException{
		byte[] bytes = new byte[2 + string.length()];
		byte[] shortBytes = getShort((short)string.length());
		byte[] stringBytes = string.getBytes("ASCII");
		
		System.arraycopy(bytes, 0, shortBytes, 0, shortBytes.length);
		System.arraycopy(bytes, 2, stringBytes, 0, stringBytes.length);
		
		return bytes;
	}
	
	public static byte[] getInt(int string){
		return ByteBuffer.allocate(4).putInt(string).array();
	}
	
	public static byte[] getShort(short string){
		return ByteBuffer.allocate(2).putShort(string).array();
	}
	
	

}
