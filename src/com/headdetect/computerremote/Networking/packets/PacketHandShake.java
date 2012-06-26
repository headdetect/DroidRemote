package com.headdetect.computerremote.Networking.packets;

import java.io.IOException;

import com.headdetect.computerremote.Networking.Packet;
import com.headdetect.computerremote.Networking.PacketData;
import com.headdetect.computerremote.Networking.PacketHandler;

public class PacketHandShake extends Packet {
	public static final int ID = 1;
	private boolean allowed;

	public PacketHandShake() {
		super(ID);
	}

	@Override
	public void loadFromPacketData(PacketData data) throws IOException {
		allowed = data.readBoolean();
	}

	@Override
	public PacketData writeToPacketData() {
		return new PacketData();
	}

	public boolean isAllowed() {
		return allowed;
	}

	@Override
	public void handleSelf(PacketHandler packetHandler) {
		packetHandler.handleHandShake(isAllowed());		
	}


}
