package devsb;

import java.sql.*;
import java.util.ArrayList;

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

    public void addData(String name,String rollNo/*, String score, String time*/) throws SQLException {
        try {
            System.out.println("add data");
            Statement statement = connection.createStatement();
            statement.execute("insert into leaderboard (name, roll_no, score, time) values ('"+name+"','"+rollNo+"'," +
                    "5,5);");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<User> getData() throws SQLException {
        ArrayList<User> userList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from leaderboard order by score desc,time desc ");
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String rollNo = resultSet.getString("roll_no");
                int score = resultSet.getInt("score");
                String time = resultSet.getString("time");
                User user = new User(name, rollNo, score, time);
                userList.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }
}
