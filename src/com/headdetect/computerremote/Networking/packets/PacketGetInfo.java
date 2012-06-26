package com.headdetect.computerremote.Networking.packets;

import java.io.IOException;

import com.headdetect.computerremote.Networking.Packet;
import com.headdetect.computerremote.Networking.PacketData;
import com.headdetect.computerremote.Networking.PacketHandler;

public class PacketGetInfo extends Packet {

	public static final int ID = 6;
	
	public PacketGetInfo() {
		super(ID);
	}

	@Override
	public void loadFromPacketData(PacketData data) throws IOException {
	}

	@Override
	public PacketData writeToPacketData() throws IOException {
		return new PacketData();
	}

	@Override
	public void handleSelf(PacketHandler packetHandler) {

	}

}
