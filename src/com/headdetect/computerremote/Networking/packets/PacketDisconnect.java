package com.headdetect.computerremote.Networking.packets;

import java.io.IOException;

import com.headdetect.computerremote.Networking.Packet;
import com.headdetect.computerremote.Networking.PacketData;
import com.headdetect.computerremote.Networking.PacketHandler;

public class PacketDisconnect extends Packet {
	public static final int ID = 5;
	private String message;

	public PacketDisconnect() {
		this("You disconnected from the server");
	}

	public PacketDisconnect(String message) {
		super(ID);
		this.message = message;
	}

	@Override
	public void loadFromPacketData(PacketData data) throws IOException {
		message = data.readString();
	}

	@Override
	public PacketData writeToPacketData() {
		PacketData data = new PacketData();
		try {
			data.writeString(message);
		} catch (IOException e) {
			// This really can't happen...
			e.printStackTrace();
		}
		return data;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public void handleSelf(PacketHandler packetHandler) {
		packetHandler.disconnect(getMessage());
	}
}
