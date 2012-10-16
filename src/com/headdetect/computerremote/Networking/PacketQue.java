/*

﻿ *    Copyright 2012 Brayden (headdetect)
 *    
 *    Dual-licensed under the Educational Community License, Version 2.0 and
 *	the GNU General Public License Version 3 (the "Licenses"); you may
 *	not use this file except in compliance with the Licenses. You may
 *	obtain a copy of the Licenses at
 *
 *		http://www.opensource.org/licenses/ecl2.php
 *		http://www.gnu.org/licenses/gpl-3.0.html
 *
 *		Unless required by applicable law or agreed to in writing
 *	software distributed under the Licenses are distributed on an "AS IS"
 *	BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 *	or implied. See the Licenses for the specific language governing
 *	permissions and limitations under the Licenses.
 * 
 */
package com.headdetect.computerremote.Networking;

import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

public class PacketQue {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private Queue<Packet> inQue;

	private Queue<Packet> outQue;

	private PacketReader packetReader;

	private boolean running;

	private Socket socket;

	private PacketWriter packetWriter;

	private Exception error;	

	// ===========================================================
	// Constructors
	// ===========================================================

	/**
	 * Instantiates a new packet que.
	 * 
	 * @param socket
	 *            the socket
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public PacketQue(Socket socket) throws IOException {
		this.packetReader = new PacketReader(socket.getInputStream());
		this.packetWriter = new PacketWriter(socket.getOutputStream());
		inQue = new LinkedList<Packet>();
		outQue = new LinkedList<Packet>();
		running = true;
		this.socket = socket;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	/**
	 * Inits an error.
	 * 
	 * @param ex
	 *            the ex
	 */
	private void error(Exception ex) {
		this.error = ex;
		if (this.error == null) {
			this.error = new Exception("ReadPacket returned null!");
		}
	}

	/**
	 * Gets the error (if there is one).
	 * 
	 * @return the error
	 */
	public Exception getError() {
		return error;
	}

	/**
	 * Returns and removes the next packet on the queue. Null if there is no
	 * packet to return
	 * 
	 * @return Next packet on the queue
	 * @throws Exception
	 *             If a network error occurred
	 */
	public Packet getNextPacket() throws Exception {
		if (error != null)
			throw error;
		synchronized (inQue) {
			return inQue.poll();
		}
	}

	/**
	 * Send packet.
	 * 
	 * @param packet
	 *            the packet
	 * @throws Exception
	 *             the exception
	 */
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
	
	public void start(){

		new RecvThread().start();
		new SendThread().start();
	}
	
	public boolean isRunning() {
		return running;
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

	/**
	 * The Class SendThread.
	 */
	private class SendThread extends Thread {

		/**
		 * Instantiates a new send thread.
		 */
		public SendThread() {
			super("Packet sending thread for socket " + socket);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Thread#run()
		 */
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

	/**
	 * The Class RecvThread.
	 */
	private class RecvThread extends Thread {

		/**
		 * Instantiates a new recv thread.
		 */
		public RecvThread() {
			super("Packet reading thread for socket " + socket);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Thread#run()
		 */
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
