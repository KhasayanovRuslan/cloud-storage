import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientMain extends Application {
//    private PanelController data;
//    private Controller cr = new Controller();
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
//                        String command = cr.getCommands().get(0);
                        String command = data.getCommandsAndFileName().get(0);
                        if (command.equals("send")) {
                            handler.out.writeUTF(command);
                            String fileName = data.getCommandsAndFileName().get(1);
                            handler.out.writeUTF(fileName);
                        }
                        if (command.equals("receive")) {
                            handler.out.writeUTF(command);
                        }

                        String response = handler.in.readUTF();
                        if (response == "File transferred") {
                            command = "receive";
//                            handler.receiveFile();
                        }
                        else {
                            command = "send";
//                            handler.sendFile();
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
          