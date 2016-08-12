package top.august1996.gifengine.util;

import java.security.MessageDigest;

public class MD5Utils {
	public static String md5(String text) {
		String result = null;
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			byte[] digest = messageDigest.digest(text.getBytes());
			
			StringBuffer sb = new StringBuffer(32);
			
			for (byte b : digest) {
				int i = b & 0xff;
				String hexString = Integer.toHexString(i);
				if (hexString.length() == 1) {
					sb.append("0" + hexString);
				} else {
					sb.append(hexString);
				}
			}
			result = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
