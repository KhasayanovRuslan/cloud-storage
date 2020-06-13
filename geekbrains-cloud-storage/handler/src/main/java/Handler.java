import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;

public class Handler implements Closeable {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public Handler(String ip, int port) {
        try {
            this.socket = new Socket(ip, port);
            this.in = createIn();
            this.out = createOut();
        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Handler(ServerSocket server) {
        try {
            this.socket = server.accept();
            this.in = createIn();
            this.out = createOut();
        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    private DataInputStream createIn() throws IOException {
        return new DataInputStream(socket.getInputStream());
    }

    private DataOutputStream createOut() throws IOException {
        return new DataOutputStream(socket.getOutputStream());
    }

    public void receiveFile(Path path) throws IOException {
        String fileName = in.readUTF();
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
    }

    public void sendFile(Path path) throws IOException {
        String filename = path.getFileName().toString();
        File file = new File(filename);

        out.writeUTF(filename);
        out.writeLong(file.length());

        FileInputStream fis = new FileInputStream(file);
        //byte [] buffer = new byte[8192];
        int x;
        while ((x = fis.read()) != -1) {
            out.write(x);
            out.flush();
        }
        fis.close();
    }

    public void deleteFile() {

    }

    @Override
    public void close() throws IOException {
        out.close();
        in.close();
        socket.close();
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
}
