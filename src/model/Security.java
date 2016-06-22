package model;

import java.security.MessageDigest;

public class Security {
	public static String toMd5(String srcString){
		String md5str = null;
		try {
			//����һ���ṩ��ϢժҪ�㷨�Ķ��󣬳�ʼ��Ϊmd5�㷨����
			MessageDigest md = MessageDigest.getInstance("MD5");
			//��������ֽ�����
			byte[] buff = md.digest(srcString.getBytes());
			//������ÿһ�ֽڻ���16��������md5�ַ���
			md5str = bytesToHex(buff);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return md5str;
	}
	private static String bytesToHex(byte[] bytes) {
		StringBuffer md5str = new StringBuffer();
		//������ÿһ�ֽڻ���16��������md5�ַ���
		int digital;
		for (int i = 0; i < bytes.length; i++) {
			 digital = bytes[i];
			if(digital < 0) {
				digital += 256;
			}
			if(digital < 16){
				md5str.append("0");
			}
			md5str.append(Integer.toHexString(digital));
		}
		return md5str.toString();
	}
}
