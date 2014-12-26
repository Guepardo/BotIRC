package rede;

import handle.HandleSocket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import util.MessageFactory;

public class SocketIRC implements Runnable {
	private String server;
	private String channel;
	private String nick;

	private Integer port;
	private Socket s;

	private BufferedWriter w;
	private BufferedReader r;

	private HandleSocket hs;

	public SocketIRC(String server, String channel, String nick, Integer port, HandleSocket hs) {
		this.server = server;
		this.channel = channel;
		this.nick = nick;
		this.hs = hs;

		if (port == null)// Adiconando porta caso default;
			this.port = 6667;
		else
			this.port = port;

		try {
			connect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void connect() throws IOException {
		s = new Socket(this.server, this.port);

		// 1 Adicionar nome de user e nick;
		// 2 Pegar o retorno do servidor;
		// 2.1 Comparar o 433 e 004 (eu acho);
		// 3 Entrar no canal desejado;
		// 4 Criar Thread para ouvir o servidor.

		w = new BufferedWriter(new OutputStreamWriter(s.getOutputStream(), "UTF-8"));
		r = new BufferedReader(new InputStreamReader(s.getInputStream(), "UTF-8"));

		String line;

		w.write("NICK " + nick + " \n\r");
		w.write("USER " + nick + " * 8 : Bot \n\r");
		w.flush();

		while ((line = r.readLine()) != null) {
			System.out.println(line);

			if (line.contains("004")) {
				System.out.println("Você está logado no servidor; ");
				break;
			}
			if (line.contains("433")) {
				System.out.println("O seu nick já está sendo usado");
			}
			if (line.startsWith("PING"))
				sendPONG(line);
		}

		new Thread(this).start();

		w.write("JOIN " + channel + " \n\r");
		w.flush();
	};

	private void sendPONG(String line) {
		try {
			System.out.println(line.substring(6));
			w.write("PONG " + line.substring(6) + " \n\r");
			w.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	};

	private void onMessage(String msg) {
		if (msg.contains("PRIVMSG"))
			this.hs.onMessage(MessageFactory.createMessage(msg), this);
	};

	public void changeNick(String nick) {
		try {
			w.write("NICK " + nick + " \n\r");
			w.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void sendMessage(String target, String text) {
		try {
			w.write("PRIVMSG " + target + " :" + text + " \n\r");
			w.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	};

	@Override
	public void run() {
		try {
			String line;
			while ((line = r.readLine()) != null) {
				System.out.println(line);
				if (line.startsWith("PING"))
					sendPONG(line);
				else
					onMessage(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	};

	public String getChannel() {
		return channel;
	}
}
