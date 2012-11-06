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
package com.headdetect.computerremote.Networking.packets;

import java.io.DataInputStream;
import java.io.IOException;

import com.headdetect.computerremote.Networking.Packet;

public class PacketCommand extends Packet {

	// ===========================================================
	// Constants
	// ===========================================================

	public static final int ID = 0x05;

	// ===========================================================
	// Fields
	// ===========================================================
	
	private String mCommand;

	private String mResult;
	
	// ===========================================================
	// Constructors
	// ===========================================================
	
	/**
	 * Instantiates a new packet command.
	 *
	 * @param command the command to send
	 */
	public PacketCommand(String command) {
		super(ID);
		
		this.mCommand = command;
	}
	
	public PacketCommand () {
		super(ID);
		
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	
	public String getResult(){
		return mResult;
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public void loadFromStream(DataInputStream mStream) throws IOException {
		mResult = Packet.readString(mStream);
	}

	@Override
	public byte[] writeData() throws IOException {
		return Packet.getString(mCommand);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
