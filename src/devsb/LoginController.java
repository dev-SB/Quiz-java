package devsb;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    public String name, rollNo;
    @FXML
    private Button startButton, leaderboardButton;
    @FXML
    private TextField nameField, rollNoField;

    @FXML
    protected void onStartClicked(ActionEvent event) throws Exception {
        try {
            if (checkInput()) {
                name=nameField.getText().trim();
                rollNo=rollNoField.getText().trim().toLowerCase();
                openInstruction(event);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onLeaderboardClicked(ActionEvent event) throws Exception {
        nameField.setText("");
        rollNoField.setText("");
        openleaderboard();
    }
    //todo: if exit before completion save data in database.

    private void openleaderboard() {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("leaderboard.fxml"));
            Stage instructionStage = new Stage();
            instructionStage.setTitle("Leaderboard");
            instructionStage.setScene(new Scene(root, 400, 400));
            instructionStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openInstruction(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("instructions.fxml"));
            Stage instructionStage = new Stage();
            instructionStage.setTitle("Instructions");
            instructionStage.setScene(new Scene(root, 400, 400));
            instructionStage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//todo: stringproperty can update data in realtime and also a listener can be attached with it
    private boolean checkInput() {
        if (!nameField.getText().isEmpty() && !rollNoField.getText().isEmpty()) {
            System.out.println("not empty");
            if (!nameField.getText().trim().contains(" ")) {
                Alert fullNameAlert = new Alert(Alert.AlertType.ERROR, "Please enter Full Name", ButtonType.OK);
                fullNameAlert.showAndWait();
                return false;
            }
        } else {
            Alert fullNameAlert = new Alert(Alert.AlertType.ERROR, "Please enter your Name and Roll Number",
                    ButtonType.OK);
            fullNameAlert.showAndWait();
            return false;
        }
        return true;
    }
}