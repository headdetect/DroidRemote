package com.headdetect.computerremote.Utils;

import java.util.Arrays;

public class MiscUtils {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	public static byte[] concatAll(byte[] bs, byte[]... b) {
		int totalLength = bs.length;
		for (byte[] array : b) {
			totalLength += array.length;
		}
		byte[] result = Arrays.copyOf(bs, totalLength);
		int offset = bs.length;
		for (byte[] array : b) {
			System.arraycopy(array, 0, result, offset, array.length);
			offset += array.length;
		}
		return result;
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
