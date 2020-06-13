import java.io.IOException;
import java.net.ServerSocket;

public class ServerMain {
    public static void main(String[] args) {
        try(ServerSocket server = new ServerSocket(8189)) {
            System.out.println("Server started!");

            while(true)
                try(Handler handler = new Handler(server)) {

                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

//    private ServerSocket server;
//    private Socket socket;
//    private DataInputStream in;
//    private DataOutputStream out;
//
//    public ServerMain () {
//        try {
//            server = new ServerSocket(8189);
//            socket = server.accept();
//            System.out.println("Client accepted");
//
//            in = new DataInputStream(socket.getInputStream());
//            out = new DataOutputStream(socket.getOutputStream());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void downloadFile(Path path) throws IOException {
//        //String fileName = in.readUTF();
//        String fileName = path.toString();
//        System.out.println("fileName: " + fileName);
//
//        long length = in.readLong();
//        System.out.println("fileLength: " + length);
//
//        File file = new File(fileName);
//        if (!file.exists()) {
//            file.createNewFile();
//        }
//
//        FileOutputStream fos = new FileOutputStream(file);
//        //byte[] buffer = new byte[8192];
//        for (long i = 0; i < length; i++) {
//            fos.write(in.read());
//        }
//        fos.close();
//
//        out.writeUTF("File: " + fileName + ", downloaded!");
//    }
//
//    public static void main(String[] args) throws IOException {
//        new ServerMain();
//    }
}
