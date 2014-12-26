package log;

import util.Messager;

public class LogController {
	private Log log;

	public LogController() {
		log = new Log();
	};

	public void addToLog(Messager msg, String messageResponse) {
		log.addLog(msg.getNick(), msg.getMsg(),msg.getChannel(), messageResponse);
	}

	public void showLog(String keyName) {
		log.printLog(keyName);
	};

}
