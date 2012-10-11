/*package com.headdetect.computerremote.Networking;

public class PingHandler implements Runnable {

	private PacketHandler mHandle;
	private int time;

	public PingHandler(PacketHandler mHandle) {
		this.mHandle = mHandle;
	}

	
	public void update(){
		time = 5;
	}

	@Override
	public void run() {
		time = 5;
		
		try {
			do {
				Thread.sleep(1000);
				time--;
			} while (time > 0);
		} catch (InterruptedException e) {
		}
		mHandle.disconnect("You timed out from the server");
		
	}
}*/
