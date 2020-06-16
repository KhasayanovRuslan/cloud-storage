import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;

import java.nio.file.Paths;
import java.util.ArrayList;

public class Controller {
    //private Handler handler;
    ArrayList<String> commands = new ArrayList<>();
    ArrayList<String> fileNameList = new ArrayList<>();

    public ArrayList<String> getCommands() {
        return commands;
    }

    public ArrayList<String> getFileNameList() {
        return fileNameList;
    }

    @FXML
    VBox leftPanel, rightPanel;

//    public void createCommand(ActionEvent actionEvent) {
//        if
//    }

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

//        PanelController leftPC = (PanelController) leftPanel.getProperties().get("ctrl");
//        PanelController rightPC = (PanelController) rightPanel.getProperties().get("ctrl");
//
//        if (leftPC.getSelectedFilename() == null && rightPC.getSelectedFilename() == null) {
//            Alert alert = new Alert(Alert.AlertType.ERROR, "Ни один файл не был выбран", ButtonType.OK);
//            alert.showAndWait();
//            return;
//        }

        PanelController srcPC = null, dstPC = null;
        if (clientPC.getSelectedFilename() != null) {
            srcPC = clientPC;
            dstPC = serverPC;
        }
        if (serverPC.getSelectedFilename() != null) {
            srcPC = serverPC;
            dstPC = clientPC;
        }

        if (srcPC == clientPC) {
            commands.add(0,"send");
        }
        else {
            commands.add(0,"receive");
        }

        String fileName = srcPC.getSelectedFilename();
        fileNameList.add(0, fileName);

//        ArrayList<String> commands = new ArrayList<>();
//        commands.add("Send");
//        commands.add("Receive");
//        if (srcPC == clientPC) {
//            String command = commands.get(0);
//        }
//        else {
//            String command = commands.get(1);
//        }

//        Path srcPath = Paths.get(srcPC.getCurrentPath(), srcPC.getSelectedFilename());
//        Path dstPath = Paths.get(dstPC.getCurrentPath()).resolve(srcPath.getFileName().toString());


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


        dstPC.updateList(Paths.get(dstPC.getCurrentPath()));
//        try {
////            handler.sendFile(srcPath);
////            handler.receiveFile(dstPath);
////            Files.copy(srcPath, dstPath);
//            dstPC.updateList(Paths.get(dstPC.getCurrentPath()));
//        } catch (IOException e) {
//            Alert alert = new Alert(Alert.AlertType.ERROR, "Не удалось скопировать указанный файл", ButtonType.OK);
//            alert.showAndWait();
//        }
    }

    public void deleteBtnAction(ActionEvent actionEvent) {

    }
}