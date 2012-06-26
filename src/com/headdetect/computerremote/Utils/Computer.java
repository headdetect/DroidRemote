package com.headdetect.computerremote.Utils;

import java.net.InetAddress;

public class Computer {

	public String Name;
	public InetAddress IP;

	public Computer(String name, InetAddress address) {
		IP = address;
		Name = name;
	}
	
	@Override
	public String toString(){
		return Name + " (" + IP + ")";
	}
}