package com.headdetect.computerremote.Networking.packets;

import java.io.IOException;

import com.headdetect.computerremote.Networking.Packet;
import com.headdetect.computerremote.Networking.PacketData;
import com.headdetect.computerremote.Networking.PacketHandler;

public class PacketMessage extends Packet {
	public static final int ID = 2;
	private String message;

	public PacketMessage() {
		super(ID);
	}

	public PacketMessage(String message) {
		super(ID);
		this.message = message;
	}

	@Override
	public void loadFromPacketData(PacketData data) throws IOException {
		message = data.readString();
	}

	@Override
	public PacketData writeToPacketData() throws IOException {
		PacketData data = new PacketData();
		data.writeString(message);
		return data;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public void handleSelf(PacketHandler packetHandler) {
		packetHandler.handleMessage(getMessage());
	}
}
