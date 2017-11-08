package com.graduation.even.graduationclient.util;

import java.nio.ByteBuffer;

public class TypeConvertUtil{

	public static byte[] shortToBytes(short shortValue){
		byte[] shortByte = new byte[2];
		shortByte[0] = (byte)(shortValue >> 8);
		shortByte[1] = (byte)(shortValue >> 0);
		return shortByte;
	}

	public static short byteToShort(byte[] byteValue){
		if(byteValue.length != 2){
			PLog.e("byte value length is not 2 is " + byteValue.length);
			return 0;
		}
		return (short) (((byteValue[0] & 0xff) << 8) | (byteValue[1] & 0xff));
	}

	public static byte[] longToBytes(long l){
		ByteBuffer buffer = ByteBuffer.allocate(8);
		buffer.putLong(0, l);
		return buffer.array();
	}

	public static long bytesToLong(byte[] bytes) {
		ByteBuffer buffer = ByteBuffer.allocate(8);
		buffer.put(bytes, 0, bytes.length);
		buffer.flip();//need flip
		return buffer.getLong();
	}

	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));

		}
		return d;
	}
	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	public static String bytesToHexString(byte[] src){
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}
}