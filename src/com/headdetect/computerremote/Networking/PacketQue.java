package com.headdetect.computerremote.Networking;

import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

public class PacketQue {
	private Queue<Packet> inQue;
	private Queue<Packet> outQue;
	private PacketReader packetReader;
	private boolean running;
	private Socket socket;
	private PacketWriter packetWriter;
	private Exception error;

	public PacketQue(Socket socket) throws IOException {
		this.packetReader = new PacketReader(socket.getInputStream());
		this.packetWriter = new PacketWriter(socket.getOutputStream());
		inQue = new LinkedList<Packet>();
		outQue = new LinkedList<Packet>();
		running = true;
		this.socket = socket;
		new RecvThread().start();
		new SendThread().start();
	}

	private void error(Exception ex) {
		this.error = ex;
		if (this.error == null) {
			this.error = new Exception("ReadPacket returned null!");
		}
	}

	public Exception getError() {
		return error;
	}

	/**
	 * Returns and removes the next packet on the que.
	 * 
	 * @return Next packet on the que
	 * @throws Exception
	 *             If a network error occured
	 */
	public Packet getNextPacket() throws Exception {
		if (error != null)
			throw error;
		synchronized (inQue) {
			return inQue.poll();
		}
	}

	public void sendPacket(Packet packet) throws Exception {
		if (error != null)
			throw error;
		synchronized (outQue) {
			outQue.offer(packet);
		}
	}

	/**
	 * Kills the packet que (closes threads to prevent leaks). Leaves socket
	 * open!
	 */
	public void close() {
		running = false;
	}

	private class SendThread extends Thread {
		public SendThread() {
			super("Packet sending thread for socket " + socket);
		}

		@Override
		public void run() {
			while (running) {
				Packet packet = null;
				synchronized (outQue) {
					packet = outQue.poll();
				}
				if (packet != null) {
					try {
						packetWriter.sendPacket(packet);
					} catch (IOException e) {
						error(e);
					}
				} else {
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						// Don't care
					}
				}
			}
		};
	}

	private class RecvThread extends Thread {
		public RecvThread() {
			super("Packet reading thread for socket " + socket);
		}

		@Override
		public void run() {
			while (running) {
				Packet packet = null;
				Exception ex = null;
				try {
					packet = packetReader.readPacket();
				} catch (IllegalArgumentException e) {
					ex = e;
				} catch (IOException e) {
					ex = e;
				} catch (Exception e) {
					ex = e;
				}
				if (packet == null) {
					error(ex);
					continue;
				} else {
					synchronized (inQue) {
						inQue.add(packet);
					}
				}
			}
		};
	}
}
