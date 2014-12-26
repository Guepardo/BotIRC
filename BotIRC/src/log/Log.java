package log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Log implements Serializable {
	private HashMap<String, ArrayList<String>> logMap;
	public Log(){
		logMap = new HashMap<>(); 
	}; 
	
	public void addLog(String emissor, String emissorMsg, String remetente, String remetenteMsg) {
		if (logMap.get(emissor) == null) {
			ArrayList<String> array = new ArrayList<>();
			array.add(emissor + ": " + emissorMsg);
			array.add(remetente + ": " + remetenteMsg);

			logMap.put(emissor, array);
		} else {
			logMap.get(emissor).add(emissor + ": " + emissorMsg);
			logMap.get(emissor).add(remetente + ": " + remetenteMsg);
		}
	};

	public void printLog(String keyName) {
		ArrayList<String> temp = logMap.get(keyName.trim());

		if (temp == null)
			return;

		for (String a : temp) {
			System.out.println(a);
		}
	};

}
