package com.headdetect.computerremote.Networking.packets;

import java.io.IOException;

import com.headdetect.computerremote.Networking.Packet;
import com.headdetect.computerremote.Networking.PacketData;
import com.headdetect.computerremote.Networking.PacketHandler;

public class PacketCommand extends Packet {
	
	public static final int ID = 3;
	
	private String command;
	
	public PacketCommand(String command){
		super (ID);
		this.setCommand(command);
	}
	
	public PacketCommand(){
		super (ID);
		this.setCommand("");
	}

	@Override
	public void loadFromPacketData(PacketData data) throws IOException {
		command = data.readString();
	}

	@Override
	public PacketData writeToPacketData() throws IOException {
		PacketData mData = new PacketData();
		mData.writeString(getCommand());
		return mData;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	@Override
	public void handleSelf(PacketHandler packetHandler) {
		packetHandler.handleCommand(getCommand());
	}

}
