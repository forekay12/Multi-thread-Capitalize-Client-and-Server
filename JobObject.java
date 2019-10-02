import java.net.Socket;

//Kayla Foremski
//Hunter Rich

public class JobObject {
	private int clientNumber;
	private Socket socket;

	public JobObject(Socket socket, int clientNumber) {
		this.socket = socket;
		this.clientNumber = clientNumber;
	}

	public Socket getSocket() {
		return socket;
	}

	public int getClientNumber() {
		return clientNumber;
	}

}