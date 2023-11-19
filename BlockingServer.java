import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class BlockingServer {

    public static void main(String[] args) {
        int portNumber = 12345;

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            System.out.println("Server is listening on port " + portNumber);

            
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected: " + clientSocket.getInetAddress());

            
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            
            PrintWriter outputWriter = new PrintWriter(clientSocket.getOutputStream(), true);

            String inputLine;
            while ((inputLine = inputReader.readLine()) != null) {
                System.out.println("Received from client: " + inputLine);

                
                outputWriter.println("Server says: " + inputLine);

                if ("bye".equalsIgnoreCase(inputLine)) {
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
