package devsb;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.*;

public class UserData {
    private Connection connection;

    public UserData(String driverClass, String url, String userName, String pass) throws ClassNotFoundException, SQLException {
        Class.forName(driverClass);
        connection = DriverManager.getConnection(url, userName, pass);
    }

    public void disconnect() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public void addData(String name, String rollNo, int score, String time) throws SQLException {
        try {
            System.out.println("add data");
            Statement statement = connection.createStatement();
            statement.execute("insert into leaderboard (name, roll_no, score, time) values ('"+name+"','"+rollNo+"'," +
                    "'"+score+"','"+time+"');");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<User> getData() throws SQLException {
        ObservableList<User> userList= FXCollections.observableArrayList();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from leaderboard order by score desc,time desc ");
            int pos=0;
            while (resultSet.next()) {
                pos++;
                String name = resultSet.getString("name");
                String rollNo = resultSet.getString("roll_no");
                int score = resultSet.getInt("score");
                String time = resultSet.getString("time");
                User user = new User(pos,name, rollNo, score, time);
                userList.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }
}
