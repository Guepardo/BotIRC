package handle;


import functions.Replacer;
import log.LogController;
import rede.Sims;
import rede.RoboEd;
import rede.SocketIRC;
import util.Converter;
import util.Messager;

public class ControllerInput extends HandleSocket {
	private final String superUser = "Jaguar";
	private RoboEd r;
	private Sims f;
	private LogController lc;
	private Replacer replacer; 
	private boolean isEd;
	
	public ControllerInput() {
		isEd = true;
		r = new RoboEd();
		f = new Sims();
		lc = new LogController();
		replacer = new Replacer(); 
	}

	@Override
	public void onMessage(Messager msg, SocketIRC si) {
		// 1 Testar a origem da mensagem: privada ou n�o;
		// 2 A��es dispon�veis para uso do chato p�blico.
		
		// Tratando mensagens privadas.
		if (isPrivate(msg)) {

			// Mostrar o log de pessoas que est�o conversando.
			if (msg.getMsg().startsWith("!MakeLog") && msg.getUser().equals(superUser)) {
				lc.showLog(msg.getMsg().substring(8));
				si.sendMessage(msg.getNick(),"Log Criado.");
				return;
			}
			// Desativando a educa��o do bot, ativando Simsims
			if (msg.getMsg().startsWith("!SemEdu") && msg.getUser().equals(superUser)) {
				isEd = false;
				si.sendMessage(msg.getNick(), "[Modo sem educa��o ATIVADO]");
			}
			// Ativando a educa��o do bot, ativando ED.
			if (msg.getMsg().startsWith("!ComEdu") && msg.getUser().equals(superUser)) {
				isEd = true;
				si.sendMessage(msg.getNick(), "[Modo sem educa��o DESATIVADO]");
			}
			
			String m = msg.getMsg();
			m = (isEd) ? r.send(m) : f.send(m);

			int sleep = (int) (m.length() * 125f);

			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			si.sendMessage(msg.getNick(), m);
			lc.addToLog(msg, m);
			return;
		}
		// Alimenteando Array do Replacer; 
		replacer.feedStore(msg);
		
		// Notifica��o para as pessoas que est�o usando o marcador antigo.
		if (msg.getMsg().startsWith("!J")) {
			si.sendMessage(msg.getChannel(), "" + msg.getNick() + " voc� pode usar apenas '.' no in�cio da frase inv�s de '!J'. Mais f�cil n� :p");
		}
		// Token para converar com os bots
		if (msg.getMsg().startsWith(".")) {
			String m = msg.getMsg().substring(1);
			m = (isEd) ? r.send(m) : f.send(m);
			si.sendMessage(msg.getChannel(), "" + msg.getNick() + ", " + Converter.firstLowerCase(m));
		}
		// Desativando a educa��o do bot, ativando Simsims
		if (msg.getMsg().startsWith("!SemEdu") && msg.getUser().equals(superUser)) {
			isEd = false;
			si.sendMessage(msg.getChannel(), "[Modo sem educa��o ATIVADO]");
		}
		// Ativando a educa��o do bot, ativando ED.
		if (msg.getMsg().startsWith("!ComEdu") && msg.getUser().equals(superUser)) {
			isEd = true;
			si.sendMessage(msg.getChannel(), "[Modo sem educa��o DESATIVADO]");
		}
		// Local reservado para uma amostragem simples do status do bot.
		if (msg.getMsg().startsWith("!Status") && msg.getUser().equals(superUser)) {
			String log = "Modo Educa��o: " + ((isEd) ? "Ativado" : "Desativado");
			si.sendMessage(msg.getChannel(), log);
		}
		// Mudar o nick do bot via comando de chat
		if (msg.getMsg().startsWith("!ChangeNick") && msg.getUser().equals(superUser)) {
			si.changeNick(msg.getMsg().substring(11));
		}
		
		//replace[nickName] ..., ..., /...,....
		if(msg.getMsg().startsWith("replace[") ){
			String text = replacer.replacer(msg); 
			if(text != null)
			si.sendMessage(msg.getChannel(),text);
		}
	}

	private boolean isPrivate(Messager m) {
		return (!m.getChannel().contains("#"));
	}
}
