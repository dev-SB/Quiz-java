package devsb;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;


public class ResultController implements Initializable {
    @FXML
    private Button leaderboardResButton;
    @FXML
    private Label correctAnsLabel, wrongAnsLabel, scoreLabel;

    @FXML
    protected void onLeaderResClicked(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        correctAnsLabel.setText(String.valueOf(QuestionController.correctAns));
        wrongAnsLabel.setText(String.valueOf(QuestionController.wrongAns));
        scoreLabel.setText(String.valueOf(QuestionController.score));
    }
}
