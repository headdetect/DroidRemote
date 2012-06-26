package com.headdetect.computerremote.Networking;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class PacketReader
{
	private DataInputStream	in;

	public PacketReader(InputStream in) throws IOException
	{
		this.in = new DataInputStream(in);
	}

	public Packet readPacket() throws IOException, IllegalArgumentException
	{
		Packet packet;
		int id = in.readShort();
		int len = in.readInt();
		byte[] data = new byte[len];
		in.readFully(data);
		// if (read != len)
		// {
		// System.err.println("Failed to read entire packet! Given size: " + len + ", read size: " + read);
		// return null;
		// }
		PacketData packetData = new PacketData(data);
		try
		{
			packet = Packet.getPacket(id);
		}
		catch (IllegalArgumentException e)
		{
			throw e;
		}
		packet.loadFromPacketData(packetData);
		return packet;
	}
}
