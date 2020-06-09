import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;

public class ServerMain {
    private ServerSocket server;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public ServerMain () {
        try {
            server = new ServerSocket(8189);
            socket = server.accept();
            System.out.println("Client accepted");

            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void downloadFile(Path path) throws IOException {
        //String fileName = in.readUTF();
        String fileName = path.toString();
        System.out.println("fileName: " + fileName);

        long length = in.readLong();
        System.out.println("fileLength: " + length);

        File file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
        }

        FileOutputStream fos = new FileOutputStream(file);
        //byte[] buffer = new byte[8192];
        for (long i = 0; i < length; i++) {
            fos.write(in.read());
        }
        fos.close();

        out.writeUTF("File: " + fileName + ", downloaded!");
    }

    public static void main(String[] args) throws IOException {
        new ServerMain();
    }
}
