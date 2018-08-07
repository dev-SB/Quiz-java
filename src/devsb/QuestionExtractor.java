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
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from questions");

            while (resultSet.next()) {
                String question = resultSet.getString("question");
                String optionOne = resultSet.getString("o_one");
                String optionTwo = resultSet.getString("o_two");
                String optionThree = resultSet.getString("o_three");
                String optionFour = resultSet.getString("o_four");
                String ans = resultSet.getString("ans");
                Question q = new Question(question, optionOne, optionTwo, optionThree, optionFour, ans);
                questions.add(q);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return questions;
    }
}

