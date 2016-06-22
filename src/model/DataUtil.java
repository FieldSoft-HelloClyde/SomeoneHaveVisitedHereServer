package model;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * ����ת��
 * @author tanchong
 */
public class DataUtil {

	/**
	 * ���ַ���ת����GBK��ʽ
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getStringByGBK(String str) throws UnsupportedEncodingException{
		String data = new String(str.getBytes(),"GBK");
		return data;
	}
	
	/**
	 * ���ַ���ת����utf-8��ʽ
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getStringByUTF(String str) throws UnsupportedEncodingException{
		String data = new String(str.getBytes(),"UTF-8");
		return data;
	}
	
	/**
	 * �����͵����ݽ���ƴװ��ƴװΪpost��get���������Խ��з��͵���ʽ
	 * @param map ����һ��map���󣬶����keyΪid��valueΪֵ
	 * @return
	 */
	public static String getDataBySendData(Map<String, String> map){
		String data = "";
		Set<Entry<String, String>> set = map.entrySet();
		Iterator<Entry<String, String>> it = set.iterator();
		while(it.hasNext()){
			Entry<String, String> entry = it.next();
			data += entry.getKey() + "=" +entry.getValue() + "&";
		}
		return data.substring(0, data.length()-1);
	}
}
