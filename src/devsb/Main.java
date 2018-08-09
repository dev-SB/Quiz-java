package devsb;

import com.mysql.jdbc.log.Log;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public UserData userData;
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.setTitle("Quiz");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();

        LoginController lc=new LoginController();
        User userDetails=lc.getNewUser();
        System.out.println("print"+userDetails.getName()+userDetails.getRollNo());
        saveUserData(userDetails.getName(),userDetails.getRollNo());

    }
    private void saveUserData(String name, String rollNo) {
        try {
            userData = new UserData("com.mysql.jdbc.Driver", "jdbc:mysql" +
                    "://localhost:3306/quiz?useSSL=false", "root", "root");
            System.out.println("save user data"+name+" "+rollNo);
            userData.addData(name,rollNo);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
