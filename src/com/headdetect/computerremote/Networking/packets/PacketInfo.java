package com.headdetect.computerremote.Networking.packets;

import java.io.DataInputStream;
import java.io.IOException;

import com.headdetect.computerremote.Networking.Packet;

public class PacketInfo extends Packet {

	public static final int ID = 5;
	
	private String compName = "";
	
	public PacketInfo() {
		super(ID);
	}


	public String getCompName() {
		return compName;
	}



	@Override
	public void loadFromStream(DataInputStream stream) throws IOException {
		compName = Packet.readString(stream);
	}


	@Override
	public byte[] writeData() throws IOException {
		return new byte[0]; //Request Only
	}


}
