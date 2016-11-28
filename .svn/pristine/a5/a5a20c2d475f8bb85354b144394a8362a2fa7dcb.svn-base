package com.hlzt.commons.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public final class FileTypeHelper {
	/** * Constructor */
	private FileTypeHelper() {
	}

	/** * 将文件头转换成16进制字符串 * * @param 原生byte * @return 16进制字符串 */
	private static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder();
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

	/** * 得到文件头 * * @param filePath * 文件路径 * @return 文件头 * @throws IOException */
	private static String getFileContent(InputStream inputStream) {
		byte[] b = new byte[28];
		try {
			inputStream.read(b, 0, 28);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return bytesToHexString(b);
	}

	/** * 判断文件类型 * * @param filePath * 文件路径 * @return 文件类型 */
	public static FileType getType(InputStream inputStream)  {
		String fileHead = getFileContent(inputStream);
		if (fileHead == null || fileHead.length() == 0) {
			return null;
		}
		fileHead = fileHead.toUpperCase();
		FileType[] fileTypes = FileType.values();
		for (FileType type : fileTypes) {
			if (fileHead.startsWith(type.getValue())) {
				return type;
			}
		}
		return null;
	}

	public static void main(String args[]) throws Exception {
		File file=new File("f:\\aa.gif");
		InputStream inputStream=new FileInputStream(file);    
		System.out.println(FileTypeHelper.getType(inputStream).name());
	}
}