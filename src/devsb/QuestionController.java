package devsb;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class QuestionController implements Initializable {
    private int currQuestionNo = 0;
    private static final int FIVEMIN = 3;
    private Timeline timeline;
    // private Integer time = FIVEMIN;
    private ArrayList<Question> questionList = new ArrayList<>();
    private ArrayList<Integer> bookmarkQ = new ArrayList<>();
    private int[] userResponse = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private String currAns = "0";

    public static int score = 0;
    public static int correctAns = 0;
    public static int wrongAns = 0;

    private final ChangeListener timeCompletionListener = new ChangeListener() {
        @Override
        public void changed(ObservableValue observable, Object oldValue, Object newValue) {
            if (newValue.toString().equals("0")) {
                if (timeline != null) {
                    timeline.stop();
                    System.out.println("executed");
                }
                try {
                    timesUp();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private IntegerProperty time = new SimpleIntegerProperty(FIVEMIN);
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
        getUserResponse();
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
            getUserResponse();
            openResult(/*event*/);
        }

    }

    private void getUserResponse() {
        if (opOne.isSelected()) {
            userResponse[currQuestionNo - 1] = 1;
        } else if (opTwo.isSelected()) {
            userResponse[currQuestionNo - 1] = 2;
        } else if (opThree.isSelected()) {
            userResponse[currQuestionNo - 1] = 3;
        } else if (opFour.isSelected()) {
            userResponse[currQuestionNo - 1] = 4;
        }
        if (String.valueOf(userResponse[currQuestionNo - 1]).equals(currAns)) {
            checkAns(true);
        } else if (userResponse[currQuestionNo - 1] != 0) {
            checkAns(false);
        }
    }

    private void checkAns(boolean res) {
        if (res) {
            if (currQuestionNo <= 4) {
                score += 1;
            } else if (currQuestionNo <= 7) {
                score += 2;
            } else {
                score += 3;
            }
            correctAns++;
        } else {
            wrongAns++;
        }
    }

    private void resetOptions() {
        opOne.setSelected(false);
        opTwo.setSelected(false);
        opThree.setSelected(false);
        opFour.setSelected(false);
    }

    private void openResult(/*ActionEvent event*/) {
        System.out.println("open result");
        LoginController lc = new LoginController();
        User userDetails = lc.getNewUser();
        userDetails.setScore(score);
        // userDetails.setTimeLeft("0");
        Parent root;
        try {

            root = FXMLLoader.load(getClass().getResource("result.fxml"));
            Stage resultStage = new Stage();
            resultStage.setTitle("Leaderboard");
            resultStage.setScene(new Scene(root, 400, 400));
            resultStage.show();
            Stage stage = (Stage) nextButton.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void timesUp() throws Exception {
        System.out.println("time up");
        Alert timesUpAlert = new Alert(Alert.AlertType.INFORMATION, "Your time is completed, Click OK to calculate " +
                "and display the result.", ButtonType.OK);
        timesUpAlert.show();
        timesUpAlert.setOnHidden(event1 -> openResult());
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
        updateQuestion();
    }

    private void updateQuestionNo() {
        currQuestionNo++;
        progress.setProgress(currQuestionNo / 11.0);
        questionNo.setText("Question #" + currQuestionNo);
        updateQuestion();
    }

    private void getQuestions() {

        try {
            QuestionExtractor questionExtractor = new QuestionExtractor("com.mysql.jdbc.Driver", "jdbc:mysql" +
                    "://localhost:3306/quiz?useSSL=false", "root", "root");
            questionList = questionExtractor.getQuestionList();
            questionExtractor.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void updateQuestion() {
        Question qObj = questionList.get(currQuestionNo - 1);
        String q = qObj.getQues();
        String op1 = qObj.getOpOne();
        String op2 = qObj.getOpTwo();
        String op3 = qObj.getOpThree();
        String op4 = qObj.getOpFour();
        currAns = qObj.getAns();
        printQuestion(q, op1, op2, op3, op4);
        /*System.out.println(questionList.get(currQuestionNo).getQues());
        System.out.println(currQuestionNo);*/
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
        timeline.playFromStart();*//*
         */
        time.set(FIVEMIN);
        timeline = new Timeline();
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(FIVEMIN + 1),
                        new KeyValue(time, 0)));
        timeline.playFromStart();

    }


    private void printQuestion(String q, String op1, String op2, String op3, String op4) {
        question.setText(q);
        opOne.setText(op1);
        opTwo.setText(op2);
        opThree.setText(op3);
        opFour.setText(op4);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currQuestionNo = 0;
        score = 0;
        correctAns = 0;
        wrongAns = 0;
        getQuestions();
        finishButton.setVisible(false);
        updateQuestionNo();
        timer.textProperty().bind(time.asString());
        time.addListener(timeCompletionListener);
        updateTime();
    }
}