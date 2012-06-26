package com.headdetect.computerremote.Networking;

public class PacketHandler extends Thread {

	private PacketQue mQue;

	private PingHandler mPinger;
	
	private boolean canRead;
	public PacketHandler(PacketQue mQue) {
		this.mQue = mQue;
		mPinger = new PingHandler(this);
		canRead = true;
	}

	@Override
	public void start() {
		super.start();

		try {

			while (canRead) {
				Packet p = mQue.getNextPacket();
				if (p != null) {
					p.handleSelf(this);
				} else {
					sleep(5);
				}
			}
		} catch (Exception e) {
			disconnect("An error has occurred while trying to get info from computer");
		}
	}

	public void disconnect(String args) {
		canRead = false;
		mQue.close();
		
		//Do stuff
		
		
	}

	
	public void handleCommand(String cmd){
		
	}
	
	public void handleMessage(String message){
		
	}
	
	public void handlePing(){
		mPinger.update();
	}
	
	public void handleHandShake(boolean allowed){
		
	}
	
	
	
	
}
