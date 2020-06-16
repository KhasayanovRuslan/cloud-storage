import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Handler implements Closeable, Runnable {
    private Socket socket;
    DataInputStream in;
    DataOutputStream out;
    private boolean isRunning = true;
    private final int COMMAND = 0, SEND = 1, RECEIVE = 2;
    private int currentOption;
    private String serverPath = "server-files";
    private String clientPath = "client-files";
    private Controller cr = null;

    public Handler(String ip, int port) {
        try {
            this.socket = new Socket(ip, port);
            this.in = createIn();
            this.out = createOut();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Handler(ServerSocket server) {
        try {
            this.socket = server.accept();
            System.out.println("Client with ip: " + socket.getInetAddress() + " accepted!");
            this.in = createIn();
            this.out = createOut();
            currentOption = COMMAND;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void stop() {
        isRunning = false;
    }

    private DataInputStream createIn() throws IOException {
        return new DataInputStream(socket.getInputStream());
    }

    private DataOutputStream createOut() throws IOException {
        return new DataOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        try {
            while (isRunning) {
                if (currentOption == COMMAND) {
                    String command = in.readUTF();
                    if (command.equals("receive")) {
                        System.out.println("command receive!");
                        currentOption = RECEIVE;
                    }
                    if (command.equals("send")) {
                        System.out.println("command send!");
                        currentOption = SEND;
                    }
                }
                if (currentOption == SEND) {
                    sendFile();
                }
                if (currentOption == RECEIVE) {
                    receiveFile();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void receiveFile() throws IOException {
        String fileName = in.readUTF();
        System.out.println("fileName: " + fileName);

        long length = in.readLong();
        System.out.println("fileLength: " + length);

        File file = new File(clientPath + fileName);
        if (!file.exists()) {
            file.createNewFile();
        } else {
            throw new RuntimeException("file exist on server");
        }

        FileOutputStream fos = new FileOutputStream(file);
        byte[] buffer = new byte[8192];
        for (int i = 0; i < length / 8192; i++) {
            int read = in.read(buffer);
            fos.write(buffer, 0, read);
        }
        fos.close();
        currentOption = COMMAND;

        out.writeUTF("File received");
    }

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



    public void sendFile() throws IOException {
        String fileName = cr.getFileNameList().get(0);
//        String fileName = in.readUTF();
        System.out.println("fileName: " + fileName);

        File file = new File(serverPath + fileName);
        if (!file.exists()) {
            out.writeInt(-1);
            out.flush();
            currentOption = COMMAND;
        } else {
            out.writeUTF(fileName);
            out.flush();
            long length = file.length();
            out.writeLong(length);
            out.flush();
            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[8192];
            while (fis.available() > 0) {
                int read = fis.read(buffer);
                out.write(buffer, 0, read);
                out.flush();
            }
            fis.close();
            currentOption = COMMAND;
        }

        out.writeUTF("File transferred");
    }

//        String filename = path.getFileName().toString();
//        File file = new File(filename);
//
//        out.writeUTF(filename);
//        out.writeLong(file.length());
//
//        FileInputStream fis = new FileInputStream(file);
//        //byte [] buffer = new byte[8192];
//        int x;
//        while ((x = fis.read()) != -1) {
//            out.write(x);
//            out.flush();
//        }
//        fis.close();



//    public String readSignal() {
//        String signal = null;
//        try {
//            signal = in.readUTF();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return signal;
//    }

    public void deleteFile() {

    }

    @Override
    public void close() throws IOException {
        out.close();
        in.close();
        socket.close();
    }
}


//    private Socket socket;
//    private BufferedReader reader;
//    private BufferedWriter writer;
//
//    public Handler(String ip, int port) {
//        try {
//            this.socket = new Socket(ip, port);
//            this.reader = createReader();
//            this.writer = createWriter();
//        }
//        catch(IOException e) {
//           throw new RuntimeException(e);
//        }
//    }
//
//    public Handler(ServerSocket server) {
//        try {
//            this.socket = server.accept();
//            this.reader = createReader();
//            this.writer = createWriter();
//        }
//        catch(IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public void writeChars(char[] chars) {
//        try {
//            writer.write(chars);
//        }
//        catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
////    public void writeLine(String message) {
////        try {
////            writer.write(message);
////            writer.newLine();
////            writer.flush;
////        }
////        catch (IOException e) {
////            throw new RuntimeException(e);
////        }
////    }
//
//    public String readLine() {
//        try {
//            return reader.readLine();
//        }
//        catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public void readChars(char[] chars) {
//        try {
//            reader.read
//            reader.read(chars);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private BufferedReader createReader() throws IOException {
//        return new BufferedReader(new InputStreamReader(socket.getInputStream()));
//    }
//
//    private BufferedWriter createWriter() throws IOException {
//        return new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//    }
//
//    @Override
//    public void close() throws IOException {
//        writer.close();
//        reader.close();
//        socket.close();
//    }

