package com.headdetect.computerremote.Networking.packets;

import java.io.IOException;

import com.headdetect.computerremote.Networking.Packet;
import com.headdetect.computerremote.Networking.PacketData;
import com.headdetect.computerremote.Networking.PacketHandler;

public class PacketSendInfo extends Packet {

	public static final int ID = 7;
	
	private String compName = "";
	
	public PacketSendInfo() {
		super(ID);
	}

	@Override
	public void loadFromPacketData(PacketData data) throws IOException {
		compName = data.readString();

	}

	@Override
	public PacketData writeToPacketData() throws IOException {
		return null;
	}

	@Override
	public void handleSelf(PacketHandler packetHandler) {
	}

	public String getCompName() {
		return compName;
	}


}
