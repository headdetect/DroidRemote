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
package com.headdetect.computerremote.Networking;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * The Class PacketReader.
 */
public class PacketReader {
	
	// ===========================================================
	// Constants
	// ===========================================================
	
	// ===========================================================
	// Fields
	// ===========================================================
	
	/** The input stream. */
	private DataInputStream in;

	// ===========================================================
	// Constructors
	// ===========================================================
	
	/**
	 * Instantiates a new packet reader.
	 *
	 * @param in the in
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public PacketReader(InputStream in) throws IOException {
		this.in = new DataInputStream(in);
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================
	
	/**
	 * Read a packet from specified stream.
	 *
	 * @return the packet
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public Packet readPacket() throws IOException, IllegalArgumentException {
		Packet packet;
		int id = in.readByte();
		
		try {
			packet = Packet.getPacket(id);
		} catch (IllegalArgumentException e) {
			throw e;
		}
		
		packet.loadFromStream(in);
		return packet;
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
	

	


}
