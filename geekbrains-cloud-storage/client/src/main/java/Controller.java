import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable, Closeable {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.socket = new Socket("localhost", 8000);
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    VBox leftPanel, rightPanel;

    public void btnExitAction(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void downloadBtnAction(ActionEvent actionEvent) {
        PanelController clientPC = (PanelController) leftPanel.getProperties().get("ctrl");
        PanelController serverPC = (PanelController) rightPanel.getProperties().get("ctrl");

        if (clientPC.getSelectedFilename() == null && serverPC.getSelectedFilename() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Ни один файл не был выбран", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        try {
            if (clientPC.getSelectedFilename() != null) {
                String command = "receive";
                out.writeUTF(command);
                String fileName = "server-files/" + clientPC.getSelectedFilename();
                out.writeUTF(fileName);
            }

            if  (serverPC.getSelectedFilename() != null) {
                String command = "send";
                out.writeUTF(command);
                String fileName = "client-files/" + serverPC.getSelectedFilename();
                out.writeUTF(fileName);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteBtnAction(ActionEvent actionEvent) {
        PanelController clientPC = (PanelController) leftPanel.getProperties().get("ctrl");
        PanelController serverPC = (PanelController) rightPanel.getProperties().get("ctrl");

        if (clientPC.getSelectedFilename() == null && serverPC.getSelectedFilename() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Ни один файл не был выбран", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        try {
            if (clientPC.getSelectedFilename() != null) {
                String command = "delete";
                out.writeUTF(command);
                String fileName = "clients-files/" + clientPC.getSelectedFilename();
                out.writeUTF(fileName);
            }

            if  (serverPC.getSelectedFilename() != null) {
                String command = "delete";
                out.writeUTF(command);
                String fileName = "server-files/" + serverPC.getSelectedFilename();
                out.writeUTF(fileName);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void close() {
        try{
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}



//        PanelController srcPC = null, dstPC = null;
//        if (clientPC.getSelectedFilename() != null) {
//            srcPC = clientPC;
//            dstPC = serverPC;
//        }
//        if (serverPC.getSelectedFilename() != null) {
//            srcPC = serverPC;
//            dstPC = clientPC;
//        }

//        Path srcPath = Paths.get(srcPC.getCurrentPath(), srcPC.getSelectedFilename());
//        Path dstPath = Paths.get(dstPC.getCurrentPath()).resolve(srcPath.getFileName().toString());
//
//        try {
//            Files.copy(srcPath, dstPath);
//            dstPC.updateList(Paths.get(dstPC.getCurrentPath()));
//        } catch (IOException e) {
//            Alert alert = new Alert(Alert.AlertType.ERROR, "Не удалось скопировать указанный файл", ButtonType.OK);
//            alert.showAndWait();
//        }

//        PanelController srcPC = null, dstPC = null;
//        if (leftPC.getSelectedFilename() != null) {
//            srcPC = leftPC;
//            dstPC = rightPC;
//        }
//        if (rightPC.getSelectedFilename() != null) {
//            srcPC = rightPC;
//            dstPC = leftPC;
//        }
//
//        Path srcPath = Paths.get(srcPC.getCurrentPath(), srcPC.getSelectedFilename());
//        Path dstPath = Paths.get(dstPC.getCurrentPath()).resolve(srcPath.getFileName().toString());
//

//        if (clientPC.getSelectedFilename() != null) {
//            commandsAndFileName.add(0,"send");
//            String fileName = clientPC.getSelectedFilename();
//            commandsAndFileName.add(1, fileName);
//        }
//        else {
//            commandsAndFileName.add(0,"receive");
//            String fileName = serverPC.getSelectedFilename();
//            commandsAndFileName.add(1, fileName);
//        }

//        fnc = new CreateFileNameAndCommands(commandsAndFileName);


//        if (srcPC == clientPC) {
//            commands.add(0,"send");
//        }
//        else {
//            commands.add(0,"receive");
//        }

//        String fileName = srcPC.getSelectedFilename();
//        fileNameList.add(0, fileName);

//        ArrayList<String> commands = new ArrayList<>();
//        commands.add("Send");
//        commands.add("Receive");
//        if (srcPC == clientPC) {
//            String command = commands.get(0);
//        }
//        else {
//            String command = commands.get(1);
//        }


//        PanelController leftPC = (PanelController) leftPanel.getProperties().get("ctrl");
//        PanelController rightPC = (PanelController) rightPanel.getProperties().get("ctrl");
//
//        if (leftPC.getSelectedFilename() == null && rightPC.getSelectedFilename() == null) {
//            Alert alert = new Alert(Alert.AlertType.ERROR, "Ни один файл не был выбран", ButtonType.OK);
//            alert.showAndWait();
//            return;
//        }

//    private ArrayList<String> commandsAndFileName = new ArrayList<>();
//    private CreateFileNameAndCommands fnc;
//
//    private ArrayList<String> commands;
//    private ArrayList<String> fileNameList;
//private Handler handler;

//    public Controller() {
//        commands = new ArrayList<>();
//        fileNameList = new ArrayList<>();
//    }

//    public ArrayList<String> getCommands() {
//        return commands;
//    }
//
//    public ArrayList<String> getFileNameList() {
//        return fileNameList;
//    }