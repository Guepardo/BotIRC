package handle;

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
	private boolean isEd;

	public ControllerInput() {
		isEd = true;
		r = new RoboEd();
		f = new Sims();
		lc = new LogController();
	}

	@Override
	public void onMessage(Messager msg, SocketIRC si) {
		// 1 Testar a origem da mensagem: privada ou não;
		// 2 Ações disponíveis para uso do chato público.

		// Tratando mensagens privadas.
		if (isPrivate(msg)) {

			// Mostrar o log de pessoas que estão conversando.
			if (msg.getMsg().startsWith("!MakeLog") && msg.getUser().equals(superUser)) {
				lc.showLog(msg.getMsg().substring(8));
				si.sendMessage(msg.getNick(),"Log Criado.");
				return;
			}
			// Desativando a educação do bot, ativando Simsims
			if (msg.getMsg().startsWith("!SemEdu") && msg.getUser().equals(superUser)) {
				isEd = false;
				si.sendMessage(msg.getNick(), "[Modo sem educação ATIVADO]");
			}
			// Ativando a educação do bot, ativando ED.
			if (msg.getMsg().startsWith("!ComEdu") && msg.getUser().equals(superUser)) {
				isEd = true;
				si.sendMessage(msg.getNick(), "[Modo sem educação DESATIVADO]");
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

		// Notificação para as pessoas que estão usando o marcador antigo.
		if (msg.getMsg().startsWith("!J")) {
			si.sendMessage(msg.getChannel(), "" + msg.getNick() + " você pode usar apenas '.' no início da frase invés de '!J'. Mais fácil né :p");
		}
		// Token para converar com os bots
		if (msg.getMsg().startsWith(".")) {
			String m = msg.getMsg().substring(1);
			m = (isEd) ? r.send(m) : f.send(m);
			si.sendMessage(msg.getChannel(), "" + msg.getNick() + ", " + Converter.firstLowerCase(m));
		}
		// Desativando a educação do bot, ativando Simsims
		if (msg.getMsg().startsWith("!SemEdu") && msg.getUser().equals(superUser)) {
			isEd = false;
			si.sendMessage(msg.getChannel(), "[Modo sem educação ATIVADO]");
		}
		// Ativando a educação do bot, ativando ED.
		if (msg.getMsg().startsWith("!ComEdu") && msg.getUser().equals(superUser)) {
			isEd = true;
			si.sendMessage(msg.getChannel(), "[Modo sem educação DESATIVADO]");
		}
		// Local reservado para uma amostragem simples do status do bot.
		if (msg.getMsg().startsWith("!Status") && msg.getUser().equals(superUser)) {
			String log = "Modo Educação: " + ((isEd) ? "Ativado" : "Desativado");
			si.sendMessage(msg.getChannel(), log);
		}
		// Mudar o nick do bot via comando de chat
		if (msg.getMsg().startsWith("!ChangeNick") && msg.getUser().equals(superUser)) {
			si.changeNick(msg.getMsg().substring(11));
		}
	}

	private boolean isPrivate(Messager m) {
		return (!m.getChannel().contains("#"));
	}
}
