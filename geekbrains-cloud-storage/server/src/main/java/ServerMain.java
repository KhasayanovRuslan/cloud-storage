import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ServerMain {
    private Socket socket;
    private Handler handler;
    private static boolean isRunning = true;
    public ConcurrentLinkedDeque<Handler> clients;

    public ConcurrentLinkedDeque<Handler> getClients() {
        return clients;
    }

    public static void stop() {
        isRunning = false;
    }

    public void removeFromClientsList(Handler handler) {
        clients.remove(handler);
    }

    public void startServer() {
        clients = new ConcurrentLinkedDeque<>();

        try(ServerSocket server = new ServerSocket(8000)) {
            System.out.println("Server started!");
            while (isRunning) {
                socket = server.accept();
                System.out.println("Client with ip: " + socket.getInetAddress() + " accepted!");
                handler = new Handler(this, socket);
                new Thread(handler).start();
                clients.add(handler);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Thread(() -> {
            Scanner in = new Scanner(System.in);
            while (true) {
                String command = in.next();
                if (command.equals("quit")) {
                    stop();
                    break;
                }
            }
        }).start();
        new ServerMain().startServer();
    }
}


//        try(ServerSocket server = new ServerSocket(8189)) {
//            System.out.println("Server started!");
//
//            while(true)
//                try(Handler handler = new Handler(server)) {
//
//                }
//                catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//        }
//        catch(IOException e) {
//            throw new RuntimeException(e);
//        }


    //                    if (handler.readSignal() == "File transferred" ) {
//                        handler.receiveFile();
//                    }
//                    else {
//                        handler.sendFile();
//                    }

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

