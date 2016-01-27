package me.mrlopez.droidremote.tv.networking;

import java.io.DataInputStream;
import java.io.IOException;

import me.mrlopez.droidremote.core.Packet;
import me.mrlopez.droidremote.core.utils.MiscUtils;

public class PacketControl extends Packet {

	// ===========================================================
	// Constants
	// ===========================================================

	public static final int ID = 0x0a;

	// ===========================================================
	// Fields
	// ===========================================================

	private ControlType ctrl;
	
	private String uri;

	// ===========================================================
	// Constructors
	// ===========================================================

	public PacketControl(ControlType ctrl) {
		super(ID);

		this.ctrl = ctrl;
	}
	
	public PacketControl(ControlType ctrl, String uri) {
		super(ID);

		this.ctrl = ctrl;
		this.uri = uri;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public void loadFromStream(DataInputStream stream) throws IOException {
		throw new IOException("Is write only packet");
		// For now
	}

	@Override
	public byte[] writeData() throws IOException {
		return MiscUtils.concatAll(Packet.getShort((short) ctrl.ordinal()), Packet.getString(uri));
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
