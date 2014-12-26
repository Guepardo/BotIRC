package main;

import handle.ControllerInput;
import handle.HandleSocket;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Scanner;

import rede.Sims;
import rede.RoboEd;
import rede.SocketIRC;
import util.MessageFactory;
import util.Messager;

public class Main {
	public static void main(String[] args) throws UnsupportedEncodingException {

		// MessageFactory.createMessage(":Jaguar!Jaguar@D34913.6396D4.328BC8.EF30E9 PRIVMSG #brasil :nada");
		SocketIRC si = new SocketIRC("irc.icq.com", "#portuguese", "Emanuella", null, new ControllerInput());

		// SocketIRC si = new
		// SocketIRC("irc.icq.com","#brasil","JustATest",null, null);

		/*
		 * Sims r = new Sims();
		 * 
		 * Scanner s = new Scanner( System.in);
		 * 
		 * while(true){ System.out.println(r.send(s.nextLine())); }
		 */

	}
}