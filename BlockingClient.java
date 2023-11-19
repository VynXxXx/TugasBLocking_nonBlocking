import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class BlockingClient {

    public static void main(String[] args) {
        String serverAddress = "localhost";
        int serverPort = 12345;

        try (Socket socket = new Socket(serverAddress, serverPort);
             PrintWriter outputWriter = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Connected to the server.");

            while (true) {
                
                System.out.print("Enter message to server (type 'bye' to exit): ");
                String message = scanner.nextLine();

                
                outputWriter.println(message);

                
                String response = inputReader.readLine();
                System.out.println("Server response: " + response);

                if ("bye".equalsIgnoreCase(message)) {
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
