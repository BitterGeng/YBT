package com.sinosoft.service.business.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class Base64DecoderS {

	/**
	 * base64解码
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String decode(String str) throws UnsupportedEncodingException {
		// System.out.println(str);
		byte[] bt = null;
		String res = null;
		try {
			sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
			bt = decoder.decodeBuffer(str);
			res = new String(bt);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}
}
