package functions;

import java.util.ArrayList;

import util.Messager;

public class Replacer {
	private ArrayList<Messager> store;
	public Replacer() {
		store = new ArrayList<>();
	}

	public void feedStore(Messager m) {
		store.add(m);
		if (store.size() == 50)
			store.remove(0);
	}

	public String replacer(Messager m) {
		// 1 pegar o nome do usuário
		// 2 separar os tokens dos que serão subistituídos
		// 3 returnar/retornar null se houver algum erro.

		// replace[nickName] ..., ..., /...,....
		String name = m.getMsg().substring(8, m.getMsg().indexOf(']'));

		String preparing = m.getMsg().substring(m.getMsg().indexOf(']') + 1, m.getMsg().length());

		String[] sides = preparing.trim().split("/");
		String[] tokens = null;
		String[] swap = null;

		if (sides[0].contains(","))
			tokens = sides[0].split(",");
		else {
			tokens = new String[1];
			tokens[0] = sides[0];
		}
		if (sides[1].contains(","))
			swap = sides[1].split(",");
		else {
			swap = new String[1];
			swap[0] = sides[1];
		}

		Messager tempMessager = null;

		for (int a = store.size() - 1; a != 0; a--) {
			tempMessager = store.get(a);
			boolean isMessage = false;
			if (tempMessager.getNick().equalsIgnoreCase(name)){
				isMessage = true;
				for(String temp : tokens ){
					if(!tempMessager.getMsg().contains(temp)){
						isMessage = false;
						break;
					}
				}
			}
			if(isMessage)
				break;
		}
		if (tempMessager == null || tokens.length < swap.length)
			return null;

		for (int a = 0; a < tokens.length; a++) {
			tempMessager.setMsg(tempMessager.getMsg().replace(tokens[a], swap[a].toUpperCase()));
		}

		String result = "Correção! O " + tempMessager.getNick() + " quis dizer na verdade: \"" + tempMessager.getMsg() + "\"";
		return result;
	}
}
