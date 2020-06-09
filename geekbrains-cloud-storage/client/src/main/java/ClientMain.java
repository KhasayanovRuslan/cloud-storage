import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

public class ClientMain {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public ClientMain() {
        try {
            socket = new Socket("localhost", 8189);
            System.out.println("Connected to server");

            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendFile(Path path) throws IOException {
        String fileName = path.toString();
        File file = new File(fileName);

        //out.writeUTF("fileName");
        out.writeLong(file.length());

        FileInputStream fis = new FileInputStream(file);
        //byte [] buffer = new byte[8192];
        int x;
        while ((x = fis.read()) != -1) {
            out.write(x);
        }
        fis.close();
    }

    public static void main(String[] args) throws IOException {
        new ClientMain();
    }
}
