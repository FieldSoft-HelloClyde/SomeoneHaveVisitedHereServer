package model;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;


public class HttpHostConnection {

	/**
	 * ����POST���з������ݣ�Ȼ����յ����ַ�������
	 * @param url ���͵�URL��ַ
	 * @param postData ���͵����ݣ�����map���з�װ
	 * @param encoding ���յ����ݣ�����ʲô���ͽ��н��գ���"GBK" or "UTF-8"
	 * @return
	 */
	public String sendPostData(String url, Map<String, String> map,String encoding)throws Exception {
		String data = null;
		URL dataUrl = new URL(url);
		HttpURLConnection con = (HttpURLConnection) dataUrl
				.openConnection();
		//�������ӵ�ͷ����Ϣ
		con.setRequestProperty("accept", "*/*");
		con.setRequestProperty("connection", "Keep-Alive");
		con.setRequestProperty("user-agent",
		"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
		//�õ����͵�����
		String post = DataUtil.getDataBySendData(map);
		// ���з�������
		sendConnectinData(con, post);
		// ���н�������
		data = getConnectionData(con, encoding);

		con.disconnect();
		return data;
	}

	/**
	 * ����GET���з������ݣ�Ȼ����յ����ַ�������
	 * @param url ���͵�URL��ַ
	 * @param postData ���͵����ݣ�����map���з�װ������ʹ��get���ͣ������ķ���
	 * @param encoding ���յ����ݣ�����ʲô���ͽ��н��գ���"GBK" or "UTF-8"
	 * @return
	 */
	public String sendGetData(String url, Map<String, String> map, String encoding) {
		String data = null;
		try {
			// ��URL����ƴװ
			url += "?" + DataUtil.getDataBySendData(map);
			//��������
			URL dataUrl = new URL(url);
			HttpURLConnection con = (HttpURLConnection) dataUrl
					.openConnection();
			//����ͷ����Ϣ
			con.setRequestProperty("accept", "*/*");
			con.setRequestProperty("connection", "Keep-Alive");
			con.setRequestProperty("user-agent",
			"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			// ֱ�ӽ��н�������
			data = getConnectionData(con, encoding);
			
			con.disconnect();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return data;
	}

	/**
	 * ��ȡĳһ���ӵ����ݣ�����Ҫ���з�������
	 * 
	 * @param url
	 *            ��Ҫ���ص�url��ַ
	 * @return
	 */
	public String getHostData(String url,String encoding) {
		String data = null;
		try {
			URL dataUrl = new URL(url);
			HttpURLConnection con = (HttpURLConnection) dataUrl
					.openConnection();
			con.setRequestProperty("accept", "*/*");
			con.setRequestProperty("connection", "Keep-Alive");
			con.setRequestProperty("user-agent",
			"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			// ���н�������
			data = getConnectionData(con, encoding);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return data;
	}

	/**
	 * ���з������ݣ����ݻ�ȡ�������Ӷ�����з�������
	 * @param con ���Ӷ���
	 * @param postData ���͵�����
	 * @throws Exception
	 */
	protected void sendConnectinData(HttpURLConnection con, String postData)
			throws Exception {
		con.setRequestMethod("POST");
		con.setDoOutput(true);
		con.setDoInput(true);
		// ���з�������
		OutputStream os = con.getOutputStream();
		DataOutputStream dos = new DataOutputStream(os);
		dos.write(postData.getBytes());
		dos.flush();
		// ������ɹر�����
		dos.close();

	}

	/**
	 * �������ӻ�ȡ�������е�����
	 * @param con ���Ӷ���
	 * @param encoding ���յ���ʽ������ʲô���ͽ��н�������
	 * @return �ɹ��õ�������
	 * @throws Exception
	 */
	protected String getConnectionData(HttpURLConnection con,String encoding) throws Exception {
		String str = "";
		// ֱ�ӽ��л�ȡ����
		InputStreamReader isr = new InputStreamReader(con.getInputStream(),encoding);
		BufferedReader br = new BufferedReader(isr);
		String temp = null;
		while ((temp = br.readLine()) != null) {
			if(temp.equals("")){
				str += "\r\n";
			}else{
				str += temp;
			}
		}
		con.disconnect();
		return str;
	}
}
