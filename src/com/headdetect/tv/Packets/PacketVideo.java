package com.headdetect.tv.Packets;

import java.io.DataInputStream;
import java.io.IOException;

import com.headdetect.computerremote.Networking.Packet;

public class PacketVideo extends Packet {


	// ===========================================================
	// Constants
	// ===========================================================
	
	private static final int ID = 0x09;

	// ===========================================================
	// Fields
	// ===========================================================
	
	private String mVideo, mVideoLength;

	// ===========================================================
	// Constructors
	// ===========================================================
	
	public PacketVideo(){
		super(ID);
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================


	@Override
	public void loadFromStream(DataInputStream stream) throws IOException {
		mVideo = Packet.readString(stream);
		mVideoLength = Packet.readString(stream);
	}

	@Override
	public byte[] writeData() throws IOException {
		throw new IOException("Is read-only packet");
	}
	
	// ===========================================================
	// Methods
	// ===========================================================
	
	public String getVideo() {
		return mVideo;
	}

	public String getVideoLength() {
		return mVideoLength;
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}


