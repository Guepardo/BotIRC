package rede;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public abstract class HTTPRequestGeneric {
	private String url;

	public HTTPRequestGeneric(String url) {
		this.url = url;
	};

	protected StringBuffer sendGeneric(String parameters) throws IOException {
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.6; pl; rv:1.9.2.13) Gecko/20101203 Firefox/3.5.13");
		con.setRequestProperty("Accept-Language", "utf-8,pt-br;q=0.8");

		con.setUseCaches(false);
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setConnectTimeout(3000);

		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(parameters);
		wr.flush();
		wr.close();

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		return response;
	};

	protected String encoder(String value, String encode) {
		String resul = null;
		try {
			resul = URLEncoder.encode(value, encode);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return resul;
	};

	protected String removeHtml(String value) {
		return value.replaceAll("<[^>]*>", "");
	}

	public static final String ALLOWED_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-_.!~*'()";

	public static String encodeURIComponent(String input) {
		if (input == null) {
			return input;
		}

		int l = input.length();
		StringBuilder o = new StringBuilder(l * 3);
		try {
			for (int i = 0; i < l; i++) {
				String e = input.substring(i, i + 1);
				if (ALLOWED_CHARS.indexOf(e) == -1) {
					byte[] b = e.getBytes("utf-8");
					o.append(getHex(b));
					continue;
				}
				o.append(e);
			}
			return o.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return input;
	}

	private static String getHex(byte buf[]) {
		StringBuilder o = new StringBuilder(buf.length * 3);
		for (int i = 0; i < buf.length; i++) {
			int n = (int) buf[i] & 0xff;
			o.append("%");
			if (n < 0x10) {
				o.append("0");
			}
			o.append(Long.toString(n, 16).toUpperCase());
		}
		return o.toString();
	}

	public static String decodeURIComponent(String encodedURI) {
		char actualChar;

		StringBuffer buffer = new StringBuffer();

		int bytePattern, sumb = 0;

		for (int i = 0, more = -1; i < encodedURI.length(); i++) {
			actualChar = encodedURI.charAt(i);

			switch (actualChar) {
				case '%' : {
					actualChar = encodedURI.charAt(++i);
					int hb = (Character.isDigit(actualChar) ? actualChar - '0' : 10 + Character.toLowerCase(actualChar) - 'a') & 0xF;
					actualChar = encodedURI.charAt(++i);
					int lb = (Character.isDigit(actualChar) ? actualChar - '0' : 10 + Character.toLowerCase(actualChar) - 'a') & 0xF;
					bytePattern = (hb << 4) | lb;
					break;
				}
				case '+' : {
					bytePattern = ' ';
					break;
				}
				default : {
					bytePattern = actualChar;
				}
			}

			if ((bytePattern & 0xc0) == 0x80) { // 10xxxxxx
				sumb = (sumb << 6) | (bytePattern & 0x3f);
				if (--more == 0)
					buffer.append((char) sumb);
			} else if ((bytePattern & 0x80) == 0x00) { // 0xxxxxxx
				buffer.append((char) bytePattern);
			} else if ((bytePattern & 0xe0) == 0xc0) { // 110xxxxx
				sumb = bytePattern & 0x1f;
				more = 1;
			} else if ((bytePattern & 0xf0) == 0xe0) { // 1110xxxx
				sumb = bytePattern & 0x0f;
				more = 2;
			} else if ((bytePattern & 0xf8) == 0xf0) { // 11110xxx
				sumb = bytePattern & 0x07;
				more = 3;
			} else if ((bytePattern & 0xfc) == 0xf8) { // 111110xx
				sumb = bytePattern & 0x03;
				more = 4;
			} else { // 1111110x
				sumb = bytePattern & 0x01;
				more = 5;
			}
		}
		return buffer.toString();
	}
}
