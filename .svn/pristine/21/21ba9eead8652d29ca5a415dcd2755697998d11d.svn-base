package com.hlzt.commons.helper;

import java.util.UUID;

public class UuidHelper {

	/**
	 * uuid
	 * 
	 * @return uuid
	 */
	public static String getRandomUUID() {
		String uuid = UUID.randomUUID().toString();
		return uuid.substring(0, 8) + uuid.substring(9, 13)
				+ uuid.substring(14, 18) + uuid.substring(19, 23)
				+ uuid.substring(24);
	}
	
	public static void main(String[] args) {
		System.out.println(getRandomUUID());
	}
}