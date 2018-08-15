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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class QuestionController implements Initializable {
    private int currQuestionNo = 0;
    private static final int TOTALTIME = 120;
    private Timeline timeline;

    private ArrayList<Question> questionList = new ArrayList<>();

    private int[] userResponse = new int[10];
    private String currAns = "0";
    public static boolean isQuizAttempted = false;

    public static int score = 0;
    public static int correctAns = 0;
    public static int wrongAns = 0;

    private Button[] buttonList = new Button[10];
    private final ChangeListener timeCompletionListener = new ChangeListener() {
        @Override
        public void changed(ObservableValue observable, Object oldValue, Object newValue) {
            if (newValue.toString().equals("0")) {
                if (timeline != null) {
                    timeline.stop();
                }
                try {
                    timesUp();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private IntegerProperty time = new SimpleIntegerProperty(TOTALTIME);
    @FXML
    private Button qOne, qTwo, qThree, qFour, qFive, qSix, qSeven, qEgt, qNine, qTen;
    @FXML
    private Label timer, maxMarks, questionNo, question;
    @FXML
    private RadioButton opOne, opTwo, opThree, opFour;
    @FXML
    private Button nextButton, resetButton, bookmarkButton, finishButton;
    @FXML
    private ProgressBar progress;

    @FXML
    protected void onQOneClicked(ActionEvent event) throws Exception {
        onQButtonClicked(1);

    }

    @FXML
    protected void onQTwoClicked(ActionEvent event) throws Exception {
        onQButtonClicked(2);
    }

    @FXML
    protected void onQThreeClicked(ActionEvent event) throws Exception {
        onQButtonClicked(3);
    }

    @FXML
    protected void onQFourClicked(ActionEvent event) throws Exception {
        onQButtonClicked(4);
    }

    @FXML
    protected void onQFiveClicked(ActionEvent event) throws Exception {
        onQButtonClicked(5);
    }

    @FXML
    protected void onQSixClicked(ActionEvent event) throws Exception {
        onQButtonClicked(6);
    }

    @FXML
    protected void onQSevenClicked(ActionEvent event) throws Exception {
        onQButtonClicked(7);
    }

    @FXML
    protected void onQEgtClicked(ActionEvent event) throws Exception {
        onQButtonClicked(8);
    }

    @FXML
    protected void onQNineClicked(ActionEvent event) throws Exception {
        onQButtonClicked(9);
    }

    @FXML
    protected void onQTenClicked(ActionEvent event) throws Exception {
        onQButtonClicked(10);
    }

    @FXML
    protected void onNextClicked(ActionEvent event) throws Exception {
        getUserResponse();
        resetOptions();
        checkQuestionNo();
        clearPrevResponseNewQues(currQuestionNo - 1);
        updateProgressBar();
    }

    private void clearPrevResponseNewQues(int qNo) {
        int prev = userResponse[qNo];
        if (prev == 1) {
            opOne.setSelected(true);
        } else if (prev == 2) {
            opTwo.setSelected(true);
        } else if (prev == 3) {
            opThree.setSelected(true);
        } else if (prev == 4) {
            opFour.setSelected(true);
        }
        int res = userResponse[qNo];
        if (res != 0) {
            if (res == Integer.parseInt(currAns)) {
                correctAns--;
                if ((qNo + 1) <= 4) {
                    score--;
                } else if ((qNo + 1) <= 7) {
                    score -= 2;
                } else {
                    score -= 3;
                }
            } else {
                wrongAns--;
            }
        }
    }

    @FXML
    protected void onResetClicked(ActionEvent event) throws Exception {
        userResponse[currQuestionNo - 1] = 0;
        clearPrevResponseNewQues(currQuestionNo - 1);
        resetOptions();
        updateQButton(buttonList[currQuestionNo-1],'u');
        updateProgressBar();
    }


    @FXML
    protected void onBookmarkClicked(ActionEvent event) throws Exception {
        updateQButton(buttonList[currQuestionNo - 1], 'b');
        checkQuestionNo();

        updateProgressBar();

    }

    @FXML
    protected void onFinishClicked(ActionEvent event) throws Exception {
        Alert submitAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to submit the quiz",
                ButtonType.YES, ButtonType.NO);
        submitAlert.showAndWait();
        if (submitAlert.getResult() == ButtonType.YES) {
            if (timeline != null) {
                timeline.stop();
            }
            getUserResponse();
            openResult();

        }

    }

    private void onQButtonClicked(int qNo) {
        getUserResponse();
        resetOptions();
        currQuestionNo = qNo - 1;
        checkQuestionNo();
        clearPrevResponseNewQues(qNo - 1);
        updateProgressBar();

    }

    private void updateQButton(Button button, char cond) {
        if (cond == 'a') {
            button.setTextFill(Color.GREEN);
            button.setText("Attempted");
        } else if (cond == 'b') {
            button.setTextFill(Color.rgb(204,153,0));
            button.setText("Bookmarked");
        } else if (cond == 'u') {
            button.setTextFill(Color.BLACK);
            button.setText("Question " + currQuestionNo);
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
        if (userResponse[currQuestionNo - 1] != 0) {
            updateQButton(buttonList[currQuestionNo - 1], 'a');

            if (String.valueOf(userResponse[currQuestionNo - 1]).equals(currAns)) {
                checkAns(true);
            } else {
                checkAns(false);
            }
        } else {
            updateQButton(buttonList[currQuestionNo - 1], 'u');

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


    private void openResult() {

        LoginController lc = new LoginController();
        User userDetails = lc.getNewUser();
        userDetails.setScore(score);
        userDetails.setTimeLeft(String.valueOf(time.getValue().toString()));

        Parent root;
        try {

            root = FXMLLoader.load(getClass().getResource("result.fxml"));
            Stage resultStage = new Stage();
            resultStage.setTitle("Result");
            resultStage.setScene(new Scene(root, 800, 400));
            resultStage.show();
            Stage stage = (Stage) nextButton.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void timesUp() throws Exception {
        Alert timesUpAlert = new Alert(Alert.AlertType.INFORMATION, "Your time is completed, Click OK to calculate " +
                "and display the result.", ButtonType.OK);
        timesUpAlert.show();
        timesUpAlert.setOnHidden(event1 -> openResult());
    }

    private void checkQuestionNo() {

        resetOptions();
        if (currQuestionNo < 9) {
            updateQuestionNo();
        } else if(currQuestionNo==9){
            lastQuestion();
        }
    }

    private void lastQuestion() {

        updateQuestionNo();
        finishButton.setVisible(true);
        nextButton.setVisible(false);
    }

    private void updateProgressBar() {
        progress.setProgress((correctAns + wrongAns) / 10.0);
    }

    private void updateQuestionNo() {
        currQuestionNo++;
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
    }


    private void updateTime() {
        time.set(TOTALTIME);
        timeline = new Timeline();
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(TOTALTIME + 1),
                        new KeyValue(time, 0)));
        timeline.playFromStart();

    }


    private void printQuestion(String q, String op1, String op2, String op3, String op4) {
        question.setText(q);
        opOne.setText(op1);
        opTwo.setText(op2);
        opThree.setText(op3);
        opFour.setText(op4);
        showMarks();
    }

    private void createButtonList() {
        buttonList[0] = qOne;
        buttonList[1] = qTwo;
        buttonList[2] = qThree;
        buttonList[3] = qFour;
        buttonList[4] = qFive;
        buttonList[5] = qSix;
        buttonList[6] = qSeven;
        buttonList[7] = qEgt;
        buttonList[8] = qNine;
        buttonList[9] = qTen;
    }

    private void showMarks() {
        if (currQuestionNo <= 4) {
            maxMarks.setText("Marks: 1");
        } else if (currQuestionNo <= 7) {
            maxMarks.setText("Marks: 2");
        } else {
            maxMarks.setText("Marks: 3");
        }
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
        createButtonList();
        updateTime();
        progress.setProgress(0);
        isQuizAttempted = true;
    }

}