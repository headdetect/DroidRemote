package com.headdetect.computerremote.Networking.packets;

import java.io.DataInputStream;
import java.io.IOException;

import com.headdetect.computerremote.Networking.Packet;

public class PacketMessage extends Packet {
	public static final int ID = 4;
	private String message;

	public PacketMessage() {
		super(ID);
		
		message = "";
	}

	public PacketMessage(String message) {
		super(ID);
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}

	@Override
	public void loadFromStream(DataInputStream stream) throws IOException {
		message = Packet.readString(stream);
	}

	@Override
	public byte[] writeData() throws IOException {
		return Packet.getString(message);
	}
}
