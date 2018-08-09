package devsb;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.ArrayList;

public class LeaderboardController {
    @FXML
    private Button backButton;

    //todo query database for leaderboard only once.
    @FXML
    protected void onBackClicked(ActionEvent event) {

    }
private ArrayList<User> userList;
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
}
