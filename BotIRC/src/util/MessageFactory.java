package util;

public class MessageFactory {

	public MessageFactory() {

	}

	public static Messager createMessage(String msg) {
		if (!msg.contains("PRIVMSG"))
			return null;

		String nick, user, ip, channel, text, temp[];

		text = msg.substring(msg.substring(1).indexOf(":") + 2);
		msg = msg.substring(1, msg.substring(1).indexOf(":"));

		temp = msg.split(" ");

		channel = temp[2];
		ip = temp[0].split("@")[1];

		nick = temp[0].split("@")[0];

		temp = nick.split("!");

		nick = temp[0];
		user = temp[1];

		return new Messager(nick, user, ip, channel, text);
	}
}
