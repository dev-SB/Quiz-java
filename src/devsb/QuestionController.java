package devsb;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.*;
import javax.swing.plaf.synth.SynthLookAndFeel;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class QuestionController implements Initializable {
    private int currQuestionNo = 0;
    private static final int FIVEMIN = 10;
    private Timeline timeline;
    private Integer time = FIVEMIN;
    private ArrayList<Integer> bookmarkQ = new ArrayList<>();
    @FXML
    private Label timer, maxMarks, questionNo, question;
    @FXML
    private RadioButton opOne, opTwo, opThree, opFour;
    @FXML
    private Button nextButton, resetButton, bookmarkButton, finishButton;
    @FXML
    private ProgressBar progress;

    @FXML
    protected void onNextClicked(ActionEvent event) throws Exception {
        checkQuestionNo();

    }

    @FXML
    protected void onResetClicked(ActionEvent event) throws Exception {

        resetOptions();
    }

    @FXML
    protected void onBookmarkClicked(ActionEvent event) throws Exception {
        bookmarkQ.add(currQuestionNo);
        checkQuestionNo();
    }

    @FXML
    protected void onFinishClicked(ActionEvent event) throws Exception {
        Alert submitAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to submit the quiz",
                ButtonType.YES, ButtonType.NO);
        submitAlert.showAndWait();
        if (submitAlert.getResult() == ButtonType.YES) {
            openResult(event);
        }

    }

    private void resetOptions() {
        opOne.setSelected(false);
        opTwo.setSelected(false);
        opThree.setSelected(false);
        opFour.setSelected(false);
    }

    private void openResult(ActionEvent event) {
        System.out.println("open result");
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("result.fxml"));
            Stage instructionStage = new Stage();
            instructionStage.setTitle("Leaderboard");
            instructionStage.setScene(new Scene(root, 400, 400));
            instructionStage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void timesUp(ActionEvent event) throws Exception {
        System.out.println("time up");
        Alert timesUpAlert = new Alert(Alert.AlertType.INFORMATION, "Your time is completed, Click OK to calculate " +
                "and display the result.", ButtonType.OK);
        timesUpAlert.show();
        timesUpAlert.setOnHidden(event1 -> openResult(event));

    }

    private void checkQuestionNo() {
            resetOptions();
        if (currQuestionNo < 9) {
            updateQuestionNo();
        } else {
            lastQuestion();
        }
    }

    private void lastQuestion() {
        updateQuestionNo();
        finishButton.setVisible(true);
        nextButton.setVisible(false);
    }

    private void updateQuestionNo() {
        currQuestionNo++;
        progress.setProgress(currQuestionNo / 11.0);
        questionNo.setText("Question #" + currQuestionNo);
    }

    private void updateTime() {
      /*  time = FIVEMIN;
        timer.setText(time.toString());
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                time--;
                int temp = time % 60;
                timer.setText((time / 60) + ":" + (temp > 10 ? temp : "0" + temp));
                if (time <= 0) {
                    try {
                        timesUp(event);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    timeline.stop();
                }
            }
        }));
        timeline.playFromStart();*/

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currQuestionNo = 0;
        finishButton.setVisible(false);
        updateQuestionNo();
        updateTime();

    }
}