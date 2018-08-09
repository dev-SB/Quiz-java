package devsb;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LeaderboardController implements Initializable {
    private ObservableList<User> userList;
    @FXML
    private Button backButton;
    @FXML
    private TableColumn name, rollNo, score, pos, timeLeft;
    @FXML
    private TableView leaderboardTable;

    //todo query database for leaderboard only once.
    @FXML
    protected void onBackClicked(ActionEvent event) {

    }

    private void getLeaderboardData() {
        try {
            UserData userData = new UserData("com.mysql.jdbc.Driver", "jdbc:mysql" +
                    "://localhost:3306/quiz?useSSL=false", "root", "root");
            userList = userData.getData();
            userData.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void populateData() {
        name.setCellValueFactory(new PropertyValueFactory<User,String>("name"));
        rollNo.setCellValueFactory(new PropertyValueFactory<User,String>("rollNo"));
        score.setCellValueFactory(new PropertyValueFactory<User,String>("score"));
        timeLeft.setCellValueFactory(new PropertyValueFactory<User,String>("timeLeft"));
        getLeaderboardData();
        leaderboardTable.setItems(userList);

                /*System.out.println("method called");*/
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getLeaderboardData();
        populateData();
//todo stop without starting quiz handle exception
        //todo change public variables to private and use get and set
    }
}
