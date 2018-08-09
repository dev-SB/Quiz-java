package devsb;

import javax.sql.ConnectionPoolDataSource;
import java.sql.*;
import java.util.ArrayList;

public class QuestionExtractor {
    private Connection connection;

    public QuestionExtractor(String driverClass, String url, String userName, String pass) throws SQLException,
            ClassNotFoundException {
        Class.forName(driverClass);
        connection = DriverManager.getConnection(url, userName, pass);
    }

    public void disconnect() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public ArrayList<Question> getQuestionList() throws SQLException {
        ArrayList<Question> questions = new ArrayList<>();
        int[] no = getRandomQNos();
        try {
            for (int i = 0; i < 10; i++) {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select * from questions where id='" + no[i] + "';");
                if (resultSet.next()) {
                    String question = resultSet.getString("question");
                    String optionOne = resultSet.getString("o_one");
                    String optionTwo = resultSet.getString("o_two");
                    String optionThree = resultSet.getString("o_three");
                    String optionFour = resultSet.getString("o_four");
                    String ans = resultSet.getString("ans");
                    Question q = new Question(question, optionOne, optionTwo, optionThree, optionFour, ans);
                    questions.add(q);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return questions;
    }

    private int[] getRandomQNos() {
        int[] no = new int[10];
        int minRange = 0, maxRange = 0;
        for (int i = 0; i < 10; i++) {
            if (i <= 1) {
                minRange = 1;
                maxRange = 5;
            } else if (i <= 3) {
                minRange = 16;
                maxRange = 20;
            } else if (i <= 5) {
                minRange = 6;
                maxRange = 10;
            } else if (i <= 6) {
                minRange = 21;
                maxRange = 25;
            } else if (i <= 7) {
                minRange = 11;
                maxRange = 15;
            } else {
                minRange = 26;
                maxRange = 30;
            }
            no[i] = (int) (Math.random() * ((maxRange - minRange) + 1)) + minRange;
            if (i >= 1 && no[i] == no[i - 1]) {
                i--;
            }
        }
        return no;
    }

}