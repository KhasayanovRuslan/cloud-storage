import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientMain extends Application {
    private Controller cr = null;
    private static boolean isRunning = true;

    public void stop() {
        isRunning = false;
    }

    public void init() {
        try(Handler handler = new Handler("127.0.0.1", 8189)) {
            System.out.println("Connected to server");

            while (isRunning) {
                try {
                    while (isRunning) {
                        String command = cr.getCommands().get(0);
                        if (command.equals("send")) {
                            handler.out.writeUTF(command);
                        }
                        if (command.equals("receive")) {
                            handler.out.writeUTF(command);
                        }

                        String response = handler.in.readUTF();
                        if (response == "File transferred") {
                            handler.receiveFile();
                        }
                        else {
                            handler.sendFile();
                        }
                    }
                }
                catch(IOException e) {
                    throw new RuntimeException(e);
                }


            }
        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/main.fxml"));
        primaryStage.setTitle("Java-Cloud-Storage [GeekBrains]");
        primaryStage.setScene(new Scene(root, 1280, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);

        new ClientMain().init();

    }
}
          