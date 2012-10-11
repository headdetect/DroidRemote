package com.headdetect.computerremote.Networking;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class PacketReader {
	private DataInputStream in;

	public PacketReader(InputStream in) throws IOException {
		this.in = new DataInputStream(in);
	}

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
}
