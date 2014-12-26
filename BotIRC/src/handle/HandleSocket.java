package handle;

import rede.SocketIRC;
import util.Messager;

public abstract class HandleSocket {
	public abstract void onMessage(Messager msg, SocketIRC si);
}
