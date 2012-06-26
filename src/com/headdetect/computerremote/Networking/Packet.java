package com.headdetect.computerremote.Networking;

import java.io.DataInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.util.HashMap;

import com.headdetect.computerremote.Networking.packets.*;

public abstract class Packet {
	static {
		packetMap = new HashMap<Integer, Class<? extends Packet>>();
		registerPacket(PacketLogin.ID, PacketLogin.class);
		registerPacket(PacketHandShake.ID, PacketHandShake.class);
		registerPacket(PacketMessage.ID, PacketMessage.class);
		registerPacket(PacketCommand.ID, PacketCommand.class);
		registerPacket(PacketPing.ID, PacketPing.class);
		registerPacket(PacketDisconnect.ID, PacketDisconnect.class);
		registerPacket(PacketGetInfo.ID, PacketGetInfo.class);
		registerPacket(PacketSendInfo.ID, PacketSendInfo.class);
	}

	public final int id;
	private static HashMap<Integer, Class<? extends Packet>> packetMap;

	public static void registerPacket(int id, Class<? extends Packet> packet) {
		if (packetMap.get(id) != null) {
			throw new RuntimeException("Duplicate packet for ID " + id + "! (Attempting to register " + packet.getSimpleName() + " over " + packetMap.get(id).getSimpleName() + ")");
		}
		packetMap.put(id, packet);
	}

	public Packet(int id) {
		this.id = id;
	}
	
	

	public abstract void loadFromPacketData(PacketData data) throws IOException;

	public abstract PacketData writeToPacketData() throws IOException;

	public abstract void handleSelf(PacketHandler packetHandler);
	
	

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
		sd.getOutputStream().write(p.writeToPacketData().getBytes());
	}

	public static Packet QuickRead(Socket sd, int id) throws IOException {
		DataInputStream mStream = new DataInputStream(sd.getInputStream());
		byte test;
		while((test = mStream.readByte()) != id || test == -1) ;
		
		if(test == -1)
			return null;
		
		int packetLen = mStream.readInt();
		byte[] ar = new byte[packetLen];
		mStream.read(ar);
		PacketData p = new PacketData(ar);
		
		Packet e = getPacket(id);
		e.loadFromPacketData(p);
		return e;
		
	}

}
