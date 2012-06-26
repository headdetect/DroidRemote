package com.headdetect.computerremote.Networking;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketData
{
	private DataInputStream			in;
	private DataOutputStream		out;
	private ByteArrayOutputStream	outBytes;

	public PacketData(byte[] data)
	{
		this.in = new DataInputStream(new ByteArrayInputStream(data));
	}

	public PacketData()
	{
		outBytes = new ByteArrayOutputStream();
		out = new DataOutputStream(outBytes);
	}

	private void canOutput() throws IOException
	{
		if (out == null) throw new IOException("This is an input PacketData!");
	}

	private void canInput() throws IOException
	{
		if (in == null) throw new IOException("This is an output PacketData!");
	}

	public int readInt() throws IOException
	{
		canInput();
		return in.readInt();
	}

	public void writeInt(int i) throws IOException
	{
		canOutput();
		out.writeInt(i);
	}

	public short readShort() throws IOException
	{
		canInput();
		return in.readShort();
	}

	public void writeShort(short s) throws IOException
	{
		canOutput();
		out.writeShort(s);
	}

	public byte readByte() throws IOException
	{
		canInput();
		return in.readByte();
	}

	public float readFloat() throws IOException
	{
		canInput();
		return in.readFloat();
	}

	public void writeFloat(float i) throws IOException
	{
		canOutput();
		out.writeFloat(i);
	}

	public void writeByte(byte b) throws IOException
	{
		canOutput();
		out.writeByte(b);
	}

	public boolean readBoolean() throws IOException
	{
		canInput();
		return in.readBoolean();
	}

	public void writeBoolean(boolean bool) throws IOException
	{
		canOutput();
		out.writeBoolean(bool);
	}

	public byte[] getBytes() throws IOException
	{
		canOutput();
		out.flush();
		return outBytes.toByteArray();
	}
	
	public String readString() throws IOException
	{
		canInput();
		return in.readUTF();
	}

	public void writeString(String str) throws IOException
	{
		canOutput();
		out.writeUTF(str);
	}

	public byte[] readBytes(int numBytes) throws IOException
	{
		canInput();
		byte[] buffer = new byte[numBytes];
		in.read(buffer);
		return buffer;
	}

	public void writeBytes(byte[] buff) throws IOException
	{
		writeBytes(buff, 0, buff.length);
	}

	public void writeBytes(byte[] buff, int off, int len) throws IOException
	{
		canOutput();
		out.write(buff, off, len);
	}
}
