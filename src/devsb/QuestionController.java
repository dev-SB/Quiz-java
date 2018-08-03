package devsb;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;


public class QuestionController implements Initializable {

    private static final int FIVEMIN = 10;
    private Timeline timeline;
    private Integer time = FIVEMIN;
    @FXML
    private Label timer, maxMarks, questionNo, question;
    @FXML
    private RadioButton opOne, opTwo, opThree, opFour;
    @FXML
    private Button nextButton, resetButton, bookmarkButton;
    @FXML
    private ProgressBar progress;

    @FXML
    protected void onNextClicked(ActionEvent event) throws Exception {

    }

    @FXML
    protected void onResetClicked(ActionEvent event) throws Exception {

    }

    @FXML
    protected void onBookmarkClicked(ActionEvent event) throws Exception {

    }

    private void timesUp() {
        System.out.println("timeOver"+time);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        time = FIVEMIN;
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
                    timeline.stop();
                    timesUp();
                }
            }
        }));
        timeline.playFromStart();
    }

}
