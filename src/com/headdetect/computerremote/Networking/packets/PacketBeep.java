package com.headdetect.computerremote.Networking.packets;

import java.io.DataInputStream;
import java.io.IOException;

import com.headdetect.computerremote.Networking.Packet;

public class PacketBeep extends Packet {
	
	public static final int ID = 0x06;

	public PacketBeep() {
		super(ID);
	}

	@Override
	public void loadFromStream(DataInputStream stream) throws IOException {
		throw new IOException("Is readonly packet");
	}

	@Override
	public byte[] writeData() throws IOException {
		return new byte[0];
	}

}
