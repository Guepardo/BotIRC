package puzzle;

import java.util.ArrayList;
import java.util.Random;

// 1 Ativar o game apenas via super usuário; 
// 2 Abrir sessão para entrada de participantes no game; 
// 3 Desativer sessão 
public class Forca {

	private boolean isActive;

	private ArrayList<String> listParticipants;
	private int idParticipantTarget;

	private String wordTarget;

	public Forca() {
		isActive = false;
	};

	public void activeGame() {
		isActive = true;
		listParticipants = new ArrayList<>();
	};

	public void desableGame() {
		isActive = false;
	};

	public void addParticipant(String nickName) {
		if (isActive)
			listParticipants.add(nickName);
	};

	public void removeparticipant(String nickName) {
		if (isActive)
			listParticipants.remove(nickName);
	};

	public void randomParticipant() {
		if (!isActive)
			return;
		Random rand = new Random();
		idParticipantTarget = rand.nextInt(listParticipants.size());
	}
	
}
