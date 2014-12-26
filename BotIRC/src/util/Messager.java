package util;

public class Messager {
	private String nick;
	private String user;
	private String ip;
	private String channel;
	private String msg;

	public Messager(String nick, String user, String ip, String channel, String msg) {
		this.nick = nick;
		this.user = user;
		this.ip = ip;
		this.channel = channel;
		this.msg = msg;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
