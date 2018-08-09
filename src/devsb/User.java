package devsb;

public class User {
    private String name, rollNo, timeLeft;
    private int score;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(String timeLeft) {
        this.timeLeft = timeLeft;
    }

    public User() {

    }

    public User(String name, String rollNo, int score, String timeLeft) {
        this.name = name;
        this.rollNo = rollNo;
        this.score = score;
        this.timeLeft = timeLeft;
    }
}
