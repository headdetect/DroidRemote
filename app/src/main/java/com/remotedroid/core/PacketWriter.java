/*

﻿ *    Copyright 2012 Brayden (headdetect) Lopez
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
package com.remotedroid.core;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class PacketWriter {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private DataOutputStream out;

	// ===========================================================
	// Constructors
	// ===========================================================

	/**
	 * Instantiates a new packet writer.
	 * 
	 * @param out
	 *            the out
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public PacketWriter(OutputStream out) throws IOException {
		this.out = new DataOutputStream(out);
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
	 * Sends the packet provided.
	 * 
	 * @param packet
	 *            Packet to send
	 * @throws IOException
	 *             If there's an error writing to the socket
	 */
	public void sendPacket(Packet packet) throws IOException {
		out.writeByte(packet.id);
		out.write(packet.writeData());
		out.flush();
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

}
