package puzzle;

import java.util.ArrayList;
import java.util.Random;

// 1 Ativar o game apenas via super usuário; 
// 2 Abrir sessão para entrada de participantes no game; 
// 3 Desativer sessão 
public class Forca {

	private boolean isActive;
	private boolean ready;
	
	private ArrayList<String> listParticipants;
	private int idParticipantTarget;

	private String wordTarget;

	public Forca() {
		isActive = false;
		ready = false;
	};

	// Ativa o game.
	public void activeGame() {
		isActive = true;
		listParticipants = new ArrayList<>();
	};

	// Desativa o game.
	public void desableGame() {
		isActive = false;
	};

	// Adiciona um participante no game;
	public void addParticipant(String nickName) {
		if (isActive)
			listParticipants.add(nickName);
	};

	// Remove um participante do game.
	public void removeparticipant(String nickName) {
		if (isActive)
			listParticipants.remove(nickName);
	};

	// Randomiza o nome da pessoa que irá submetar a WordTarget para o game e
	// retorna o nome da mesma.
	public String randomParticipant() {
		if (!isActive)
			return "";
		Random rand = new Random();
		idParticipantTarget = rand.nextInt(listParticipants.size());
		return listParticipants.get(idParticipantTarget);
	};
	
	// Submete, mediante o nome do jogador, a letra desejada.
	public String submitChat(String nickName, String key) {
		String output =""; 
		char[] array = wordTarget.toCharArray(); 
		char   charKey = key.toCharArray()[0];
		
		
		for( int a = 0 ; a < array.length ; a++){
			
		}
		
		return null;
	}
}
