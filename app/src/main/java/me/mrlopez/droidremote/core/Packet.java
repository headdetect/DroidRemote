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
package me.mrlopez.droidremote.core;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import android.util.SparseArray;

import me.mrlopez.droidremote.app.networking.PacketBeep;
import me.mrlopez.droidremote.app.networking.PacketCommand;
import me.mrlopez.droidremote.app.networking.PacketMessage;
import me.mrlopez.droidremote.core.utils.ByteSwapper;
import me.mrlopez.droidremote.tv.networking.PacketControl;
import me.mrlopez.droidremote.tv.networking.PacketVideo;

/**
 * The Class Packet. Contains tools and abstract data to construct a Packet.
 */
public abstract class Packet {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	/** The id of the packet. */
	public final int id;

	/** The packet map. */
	private static SparseArray<Class<? extends Packet>> packetMap;

	// ===========================================================
	// Constructors
	// ===========================================================

	static {
		packetMap = new SparseArray<Class<? extends Packet>>();
		registerPacket(0x04, PacketMessage.class);
		registerPacket(0x05, PacketCommand.class);
		registerPacket(0x06, PacketBeep.class);
		
		registerPacket(0x09, PacketVideo.class);
		registerPacket(0x0a, PacketControl.class); 
	}

	/**
	 * Instantiates a new packet.
	 * 
	 * @param id
	 *            the id
	 */
	public Packet(int id) {
		this.id = id;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	/**
	 * Load data from stream.
	 * 
	 * @param stream
	 *            the stream
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public abstract void loadFromStream(DataInputStream stream) throws IOException;

	/**
	 * Writes the data to a byte array.
	 * 
	 * @return the byte[]
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public abstract byte[] writeData() throws IOException;

	// ===========================================================
	// Methods
	// ===========================================================

	/**
	 * Registers a packet.
	 * 
	 * @param id
	 *            the id
	 * @param packet
	 *            the packet
	 */
	public static void registerPacket(int id, Class<? extends Packet> packet) {
		if (packetMap.get(id) != null) {
			throw new RuntimeException("Duplicate packet for ID " + id + "! (Attempting to register " + packet.getSimpleName() + " over " + packetMap.get(id).getSimpleName() + ")");
		}
		packetMap.put(id, packet);
	}

	/**
	 * Gets an empty packet to be filled with data of sorts.
	 * 
	 * @param id
	 *            the id
	 * @return the packet
	 * @throws IllegalArgumentException
	 *             the illegal argument exception
	 */
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
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Sends a packet right now. No waiting.
	 * 
	 * @param sd
	 *            the sd
	 * @param p
	 *            the p
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void QuickSend(Socket sd, Packet p) throws IOException {
		sd.getOutputStream().write(p.id);
		sd.getOutputStream().write(p.writeData());
	}

	/**
	 * Reads the next packet. May cause problems if another object is reading
	 * from the same Stream.
	 * 
	 * @param sd
	 *            the sd
	 * @param id
	 *            the id
	 * @return the packet
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static Packet QuickRead(Socket sd, int id) throws IOException {
		DataInputStream mStream = new DataInputStream(sd.getInputStream());

		Packet e = getPacket(id);
		e.loadFromStream(mStream);
		return e;

	}

	// ---------------------------------------------------------------
	// - Tools -
	// ---------------------------------------------------------------

	/**
	 * Read a string.
	 * 
	 * @param mStream
	 *            the stream
	 * @return the string
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static String readString(DataInputStream mStream) throws IOException {
		int len = readInt(mStream);
		byte[] data = new byte[len];

		mStream.read(data);

		return new String(data, "UTF8");
	}

	/**
	 * Read an int.
	 * 
	 * @param mStream
	 *            the stream
	 * @return the int
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static int readInt(DataInputStream mStream) throws IOException {
		if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN)
			return ByteSwapper.swap(mStream.readInt());
		return mStream.readInt();
	}

	/**
	 * Read short.
	 * 
	 * @param mStream
	 *            the stream
	 * @return the short that was read
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static short readShort(DataInputStream mStream) throws IOException {
		if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN)
			return ByteSwapper.swap(mStream.readShort());
		return mStream.readShort();
	}

	/**
	 * Gets the string in byte array form.
	 * 
	 * @param string
	 *            the string to convert into a byte array
	 * @return the string
	 * @throws UnsupportedEncodingException
	 *             the unsupported encoding exception
	 */
	public static byte[] getString(String string) throws UnsupportedEncodingException {
		byte[] bytes = new byte[4 + string.length()];
		byte[] shortBytes = getInt(string.length());
		byte[] stringBytes = string.getBytes("UTF8");

		System.arraycopy(shortBytes, 0, bytes, 0, shortBytes.length);
		System.arraycopy(stringBytes, 0, bytes, 4, stringBytes.length);

		return bytes;
	}

	/**
	 * Gets an int in byte array form.
	 * 
	 * @param integer
	 *            the number to convert
	 * @return the int
	 */
	public static byte[] getInt(int integer) {
		return ByteBuffer.allocate(4).putInt(ByteSwapper.swap(integer)).array();
	}

	/**
	 * Gets the short in byte array form.
	 * 
	 * @param shawty
	 *            the number to convert
	 * @return the short
	 */
	public static byte[] getShort(short shawty) {
		return ByteBuffer.allocate(2).putShort(ByteSwapper.swap(shawty)).array();
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

}
