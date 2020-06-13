import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientMain extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/main.fxml"));
        primaryStage.setTitle("Java-Cloud-Storage [GeekBrains]");
        primaryStage.setScene(new Scene(root, 1280, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);

        try(Handler handler = new Handler("127.0.0.1", 8189)) {
            System.out.println("Connected to server");
        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
}
          