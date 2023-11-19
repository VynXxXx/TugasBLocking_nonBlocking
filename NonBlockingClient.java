import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class NonBlockingClient {

    public static void main(String[] args) {
        String serverAddress = "localhost";
        int serverPort = 12345;

        try (SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(serverAddress, serverPort));
             Scanner scanner = new Scanner(System.in)) {

            socketChannel.configureBlocking(false);

            System.out.println("Connected to the server.");

            while (true) {
                System.out.print("Enter message to server (type 'bye' to exit): ");
                String message = scanner.nextLine();

                ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
                socketChannel.write(buffer);

                if ("bye".equalsIgnoreCase(message)) {
                    break;
                }

                buffer.clear();
                int bytesRead = socketChannel.read(buffer);

                if (bytesRead == -1) {
                    // Server has disconnected
                    System.out.println("Server disconnected.");
                    break;
                }

                buffer.flip();
                byte[] data = new byte[buffer.remaining()];
                buffer.get(data);
                String response = new String(data);

                System.out.println("Server response: " + response);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
