package rede;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import com.json.exceptions.JSONParsingException;
import com.json.parsers.JSONParser;
import com.json.parsers.JsonParserFactory;

public class Sims extends HTTPRequestGeneric {
	private final static String URL = "http://www.mrpbrasil.com/LuizEduardo/Caipira/ss.php";
	private String uid;
	private String sid;
	private JSONParser parser;

	private boolean success;

	public Sims() {
		super(URL);
		success = true;

		parser = JsonParserFactory.getInstance().newJsonParser();

		do {
			try {
				Map jsonData = parser.parseJson(super.sendGeneric("").toString());

				this.uid = (String) jsonData.get("uid");
				this.sid = (String) jsonData.get("sid");
				success = false;

			} catch (IOException | JSONParsingException e) {
				e.printStackTrace();

			}

		} while (success);
	}

	public String send(String msg) {
		Map jsonData = null;
		parser = JsonParserFactory.getInstance().newJsonParser();
		String g = "sid=" + this.sid + "&uid=" + this.uid + "&msg=" + encoder(encodeURIComponent(msg), "UTF-8");
		String resp = null;
		success = true;
		do {
			try {

				g = super.sendGeneric(g).toString();
				System.out.println(g);
				jsonData = parser.parseJson(g);

				this.uid = (String) jsonData.get("uid");
				this.sid = (String) jsonData.get("sid");

				Map temp = (Map) jsonData.get("data");
				resp = (String) temp.get("sentence_resp");
				if (resp != null && !resp.isEmpty())
					success = false;
			} catch (IOException | JSONParsingException e) {
				e.printStackTrace();
			}
		} while (success);
		return resp.replaceAll("\n", "");
	};

}
