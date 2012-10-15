package com.headdetect.computerremote.Networking.packets;

import java.io.DataInputStream;
import java.io.IOException;

import com.headdetect.computerremote.Networking.Packet;

public class PacketPing extends Packet {

	public static final int ID = 0x00;

	public PacketPing() {
		super(ID);
	}

	@Override
	public void loadFromStream(DataInputStream stream) throws IOException {
	}

	@Override
	public byte[] writeData() throws IOException {
		return new byte[0];
	}

}
