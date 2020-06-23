import java.io.*;
import java.net.Socket;

public class Handler implements Runnable {
    private DataInputStream in;
    private DataOutputStream out;
    private ServerMain server;
    private Socket socket;
    private boolean isRunning = true;
    private final int COMMAND = 0, SEND = 1, RECEIVE = 2, DELETE = 3;
    private int currentOption;
//    private String serverPath = "server-files";
    //private String clientPath = "client-files";

    public Handler(ServerMain server, Socket socket) {
        try {
            this.server = server;
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            this.currentOption = COMMAND;
        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void stop() {
        isRunning = false;
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
                    if (command.equals("delete")) {
                        System.out.println("command delete!");
                        currentOption = DELETE;
                    }
                }
                if (currentOption == SEND) {
                    String fileName = in.readUTF();
                    System.out.println("fileName: " + fileName);

                    File file = new File(fileName);
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
//                        out.writeUTF("File transferred");
                    }

                }
                if (currentOption == RECEIVE) {
                    String fileName = in.readUTF();
                    System.out.println("fileName: " + fileName);

                    long length = in.readLong();
                    System.out.println("fileLength: " + length);

                    File file = new File(fileName);
                    if (!file.exists()) {
                        file.createNewFile();
                    } else {
                        throw new RuntimeException("file exist on server");
                    }

                    FileOutputStream fos = new FileOutputStream(file);
//                    byte[] buffer = new byte[8192];
                    for (long i = 0; i < length; i++) {
                        fos.write(in.read());
//                        int read = in.read(buffer);
//                        fos.write(buffer, 0, read);
                    }
                    fos.close();
                    currentOption = COMMAND;

//                    out.writeUTF("File received");
                }
                if (currentOption == DELETE) {
                    String fileName = in.readUTF();
                    System.out.println("fileName: " + fileName);

                    File file = new File(fileName);
                    boolean del = file.delete();

                    currentOption = COMMAND;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
                socket.close();
            }
            catch(IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}

//    public Handler(String ip, int port) {
//        try {
//            this.socket = new Socket(ip, port);
//            this.in = createIn();
//            this.out = createOut();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public Handler(ServerSocket server) {
//        try {
//            this.socket = server.accept();
//            System.out.println("Client with ip: " + socket.getInetAddress() + " accepted!");
//            this.in = createIn();
//            this.out = createOut();
//            currentOption = COMMAND;
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

//    private DataInputStream createIn() throws IOException {
//        return new DataInputStream(socket.getInputStream());
//    }
//
//    private DataOutputStream createOut() throws IOException {
//        return new DataOutputStream(socket.getOutputStream());
//    }


//    @Override
//    public void close() throws IOException {
//        out.close();
//        in.close();
//        socket.close();
//    }


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

