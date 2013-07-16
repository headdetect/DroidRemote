package com.remotedroid.tv.networking;

import java.io.DataInputStream;
import java.io.IOException;

import com.remotedroid.core.Packet;

public class PacketVideo extends Packet {

	// ===========================================================
	// Constants
	// ===========================================================

	private static final int ID = 0x09;

	// ===========================================================
	// Fields
	// ===========================================================

	private String mVideo, mVideoLength;

	private int vidID;

	// ===========================================================
	// Constructors
	// ===========================================================

	public PacketVideo() {
		super(ID);
		vidID = -1;
	}

	public PacketVideo(int vidId) {
		super(ID);
		this.vidID = vidId;
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
		return Packet.getInt(vidID);
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
