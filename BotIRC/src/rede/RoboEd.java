package rede;

import java.io.IOException;

public class RoboEd extends HTTPRequestGeneric {
	private final static String URL = "http://www.ed.conpet.gov.br/mod_perl/bot_gateway.cgi";

	public RoboEd() {
		super(URL);
	};

	public String send(String msg) {
		String result = null;
		try {
			result = super.sendGeneric("server=0.0.0.0%3A8085&charset_post=utf-8&charset=utf-8&pure=1&js=0&tst=1&msg=" + encoder(msg, "UTF-8"))
					.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return removeHtml(result);
	};

}
