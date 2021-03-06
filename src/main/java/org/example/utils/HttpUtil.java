package org.example.utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {
	
	/**
	 * 默认的http连接超时时间
	 */
	private final static int DEFAULT_CONN_TIMEOUT = 10000; // 10s
	/**
	 * 默认的http read超时时间
	 */
	private final static int DEFAULT_READ_TIMEOUT = 120000; // 120s
	/**
	 * 默认编码格式
	 */
	private final static String DEFAULT_ENCODING = "UTF-8";
	
	public static String postReq(String requrl, String reqStr) {
		HttpURLConnection conn = null;
		OutputStreamWriter out = null;
		BufferedReader in = null;
		try {
			try {
				URL url = new URL(requrl);
				conn = (HttpURLConnection) url.openConnection();
				conn.setDoInput(true);
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				conn.setUseCaches(false);
				conn.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
				conn.setConnectTimeout(DEFAULT_CONN_TIMEOUT);
				conn.setReadTimeout(DEFAULT_READ_TIMEOUT);
				conn.connect();
			}
			catch (Exception e) {
				System.out.println("连接超时");
				return null;
			}

			out = new OutputStreamWriter(conn.getOutputStream(), DEFAULT_ENCODING);
			out.write(reqStr);
			out.flush();
			out.close();

			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), DEFAULT_ENCODING));
			StringBuilder sb = new StringBuilder();
			char[] buff = new char[2048];
			int cnt = 0;
			while ((cnt = in.read(buff)) != -1) {
				sb.append(buff, 0, cnt);
			}
			in.close();
			return sb.toString();
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
			System.out.println("应答超时");
			return null;
		}
		finally {
			if (null != out) {
				try {
					out.close();
				}
				catch (IOException e) {
				}
			}
			if (null != in) {
				try {
					in.close();
				}
				catch (IOException e) {
				}
			}
		}
	}
	
	public static void main(String[] args) {
		HttpUtil.postReq("https://hpay.96225.com/PayOrder/download?date=20190321&token=853425422019032120190322172541240330100100320", null);
	}

}

