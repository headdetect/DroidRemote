package com.headdetect.computerremote.Networking;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class PacketWriter
{
	private DataOutputStream	out;

	public PacketWriter(OutputStream out) throws IOException
	{
		this.out = new DataOutputStream(out);
	}

	/**
	 * Sends the packet provided
	 * 
	 * @param packet
	 *            Packet to send
	 * @throws IOException
	 *             If there's an error writing to the socket
	 */
	public void sendPacket(Packet packet) throws IOException
	{
		out.writeByte(packet.id);
		PacketData data = packet.writeToPacketData();
		byte[] buff = data.getBytes();
		out.writeInt(buff.length);
		out.write(buff);
		out.flush();
	}
}
