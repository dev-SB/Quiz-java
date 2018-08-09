package devsb;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class User {
    private SimpleIntegerProperty pos=new SimpleIntegerProperty();
    private SimpleStringProperty name = new SimpleStringProperty();
    private SimpleStringProperty timeLeft = new SimpleStringProperty();
    private SimpleStringProperty rollNo = new SimpleStringProperty();
    private SimpleIntegerProperty score = new SimpleIntegerProperty();

    public int getPos() {
        return pos.get();
    }

    public SimpleIntegerProperty posProperty() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos.set(pos);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getRollNo() {
        return rollNo.get();
    }

    public void setRollNo(String rollNo) {
        this.rollNo.set(rollNo);
    }

    public int getScore() {
        return score.get();
    }

    public void setScore(int score) {
        this.score.set(score);
    }

    public String getTimeLeft() {
        return timeLeft.get();
    }

    public void setTimeLeft(String timeLeft) {
        this.timeLeft.set(timeLeft);
    }

    public User() {

    }

    public User(int pos,String name, String rollNo, int score, String timeLeft) {
        this.pos.set(pos);
        this.score.set(score);
        this.timeLeft.set(timeLeft);
        this.rollNo.set(rollNo);
        this.name.set(name);
    }
}
