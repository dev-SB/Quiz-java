package devsb;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class InstructionsController implements Initializable {

    @FXML
    private Label instructionsLabel;
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
            instructionStage.setTitle("Questions");
            instructionStage.setScene(new Scene(root, 800, 400));
            instructionStage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instructionsLabel.setText("\t\t\t\t\t\t\t\t\t\t\tINSTRUCTIONS\n" +
                "•\tThe quiz consists of 10 multiple choices. There are four options for each question. Out of 10 " +
                "questions, 3 are easy, 3 \n\tare of medium difficulty and 4 are Hard.\n" +
                "•\tEasy questions give you 1 mark each, medium questions 2 marks each and hard questions 3 mark each.\n" +
                "•\tThere is no negative marking. Zero marks will be given for unattempted questions.\n" +
                "•\tYou will be given 120 seconds to answer all the questions.\n" +
                "•\tChoosing NEXT automatically saves your answers. If you close the quiz in-between, your score will be saved \n\tand you will be awarded 0 for unattempted questions.\n" +
                "•\tA progress bar will be shown to tell how many questions you have crossed.\n" +
                "•\tYou can bookmark a question if you wish to answer it afterwards by clicking the BOOKMARK button. " +
                "The \n\tbookmarked questions will be marked in right side of the screen. \n" +
                "\t\t\t\t\t\t\t\t\t\t\tGood Luck!!\n");
    }
}
