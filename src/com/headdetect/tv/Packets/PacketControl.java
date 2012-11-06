package com.headdetect.tv.Packets;

import java.io.DataInputStream;
import java.io.IOException;

import com.headdetect.computerremote.Networking.Packet;

public class PacketControl extends Packet {

	// ===========================================================
	// Constants
	// ===========================================================

	public static final int ID = 0x0a;

	// ===========================================================
	// Fields
	// ===========================================================

	private ControlType ctrl;

	// ===========================================================
	// Constructors
	// ===========================================================

	public PacketControl(ControlType ctrl) {
		super(ID);

		this.ctrl = ctrl;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public void loadFromStream(DataInputStream stream) throws IOException {
		throw new IOException("Is write only packet");
		// For now
	}

	@Override
	public byte[] writeData() throws IOException {
		return Packet.getShort((short) ctrl.ordinal());
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
