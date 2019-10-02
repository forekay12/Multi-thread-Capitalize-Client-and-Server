import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

// Kayla Foremski
// Hunter Rich

public class CapitalizeServer {

	public static void main(String[] args) throws Exception {

		try {

			// Variables
			int clientNumber = 1;
			ServerSocket listener = new ServerSocket(9898);
			SharedQueue que = new SharedQueue(50);
			new ThreadManager(que, 10, 20, 100, listener);

			// Print to console that the server is running
			System.out.println("The capitalization server is running.\n");

			// Process each connection
			while (true) {
				Socket socket = listener.accept();
				que.enqueue(new JobObject(socket, clientNumber++));
			}

		} catch (SocketException e) {
			System.out.println("The capitalization server is now OFFLINE.");
		}

	}

}