package com.headdetect.computerremote.Networking.packets;

import java.io.IOException;

import com.headdetect.computerremote.Networking.Packet;
import com.headdetect.computerremote.Networking.PacketData;
import com.headdetect.computerremote.Networking.PacketHandler;

public class PacketLogin extends Packet {
	
	public static final int ID = 0;
	
	private String username, password;

	public PacketLogin(String username, String password){
		super(ID);
	}
	
	@Override
	public void loadFromPacketData(PacketData data) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public PacketData writeToPacketData() throws IOException {
		PacketData mData = new PacketData();
		mData.writeString(username);
		mData.writeString(password);
		return mData;
	}

	@Override
	public void handleSelf(PacketHandler packetHandler) {
		throw new RuntimeException("This packet cannot be recieved : Login");
	}

}
