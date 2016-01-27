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
package me.mrlopez.droidremote.app.networking;

import java.io.DataInputStream;
import java.io.IOException;

import me.mrlopez.droidremote.core.Packet;


// TODO: Auto-generated Javadoc
/**
 * The Class PacketMessage.
 */
public class PacketMessage extends Packet {
	
	// ===========================================================
	// Constants
	// ===========================================================

	/** The Constant ID = 0x04. */
	public static final int ID = 4;
	
	// ===========================================================
	// Fields
	// ===========================================================
	
	/** The message. */
	private String message;

	// ===========================================================
	// Constructors
	// ===========================================================
	
	/**
	 * Instantiates a new empty packet message. Usually to be filled later.
	 */
	public PacketMessage() {
		super(ID);
		
		message = "";
	}

	/**
	 * Instantiates a new packet message.
	 *
	 * @param message the message
	 */
	public PacketMessage(String message) {
		super(ID);
		this.message = message;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	
	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * Sets the message.
	 *
	 * @param msg the new message
	 */
	public void setMessage(String msg){
		message = msg;
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	
	/* (non-Javadoc)
	 * @see com.headdetect.computerremote.Networking.Packet#loadFromStream(java.io.DataInputStream)
	 */
	@Override
	public void loadFromStream(DataInputStream stream) throws IOException {
		message = readString(stream);
	}

	/* (non-Javadoc)
	 * @see com.headdetect.computerremote.Networking.Packet#writeData()
	 */
	@Override
	public byte[] writeData() throws IOException {
		return getString(message);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
	
	
}
