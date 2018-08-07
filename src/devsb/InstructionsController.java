package devsb;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class InstructionsController {

    @FXML
    private Button startQuizButton;
    @FXML
    protected void onStartQuizClicked(ActionEvent event) throws Exception{
      startQuiz(event);
    }
    private void startQuiz(ActionEvent event){
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("question.fxml"));
            Stage instructionStage = new Stage();
            instructionStage.setTitle("Leaderboard");
            instructionStage.setScene(new Scene(root, 400, 400));
            instructionStage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
