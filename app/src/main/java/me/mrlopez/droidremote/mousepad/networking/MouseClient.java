package me.mrlopez.droidremote.mousepad.networking;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.LinkedList;
import java.util.Queue;

import me.mrlopez.droidremote.core.UDPClient;
import me.mrlopez.droidremote.core.utils.ByteConverter;
import me.mrlopez.droidremote.core.utils.ByteSwapper;
import me.mrlopez.droidremote.mousepad.MouseButton;

public class MouseClient extends UDPClient {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	public int waitTime = 2;

	private Queue< byte[] > mPacketQueue;

	// ===========================================================
	// Constructors
	// ===========================================================

	public MouseClient( DatagramSocket socket , InetAddress address ) throws IOException {
		super( socket , address );

		mPacketQueue = new LinkedList< byte[] >();
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public void run() {
		while ( !disconnecting ) {

			try {

				if ( mPacketQueue.size() == 0 ) {
					Thread.sleep( waitTime );
					continue;
				}

				byte[] data = mPacketQueue.poll();

				DatagramPacket mPack = new DatagramPacket( data , data.length , getInetAddress() , 5051 );
				getSocket().send( mPack );

				Thread.sleep( waitTime );

			} catch ( IOException e ) {
				e.printStackTrace();
				disconnect();
			} catch ( InterruptedException e ) {
				e.printStackTrace();
			}
		}
	}

	// ===========================================================
	// Methods
	// ===========================================================

	public void sendClick( MouseButton.ButtonPosition mPos ) {
		byte header = 0x02;
		byte payload = (byte) ( mPos == MouseButton.ButtonPosition.Left ? 0x00 : 0x01 );

		mPacketQueue.add( pad( combine( header , payload ) , 9 ) );
	}

	public void updatePosition( float x , float y ) {
		byte header = 0x01;
		byte[] payload = combine( ByteConverter.toByta( ByteSwapper.swap( x ) ) , ByteConverter.toByta( ByteSwapper.swap( y ) ) );

		mPacketQueue.add( addHeader( header , payload ) );
	}

	public void mouseUp() {
		byte header = 0x03;
		mPacketQueue.add( pad( new byte[] { header } , 9 ) );
	}

	byte[] combine( byte[] one , byte[] two ) {
		byte[] combined = new byte[ one.length + two.length ];

		for ( int i = 0; i < combined.length; ++i ) {
			combined[ i ] = i < one.length ? one[ i ] : two[ i - one.length ];
		}

		return combined;

	}

	byte[] addHeader( byte header , byte[] payload ) {
		byte[] packet = new byte[ payload.length + 1 ];
		packet[ 0 ] = header;
		for ( int i = 0; i < payload.length; i++ ) {
			packet[ i + 1 ] = payload[ i ];
		}
		return packet;
	}

	byte[] combine( byte one , byte two ) {
		byte[] combined = new byte[ 2 ];
		combined[ 0 ] = one;
		combined[ 1 ] = two;
		return combined;

	}

	byte[] pad( byte[] packet , int padSize ) {
		if ( packet.length > padSize )
			return packet;

		byte[] result = new byte[ padSize ];
		for ( int i = 0; i < padSize; i++ ) {
			if ( i < packet.length )
				result[ i ] = packet[ i ];
			else
				result[ i ] = (byte) 0xFF;
		}

		return result;

	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

}
