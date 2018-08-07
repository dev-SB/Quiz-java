package devsb;

public class Question {
    private String ques, opOne, opTwo, opThree, opFour, ans;

    public String getQues() {
        return ques;
    }

    public void setQues(String ques) {
        this.ques = ques;
    }

    public String getOpOne() {
        return opOne;
    }

    public void setOpOne(String opOne) {
        this.opOne = opOne;
    }

    public String getOpTwo() {
        return opTwo;
    }

    public void setOpTwo(String opTwo) {
        this.opTwo = opTwo;
    }

    public String getOpThree() {
        return opThree;
    }

    public void setOpThree(String opThree) {
        this.opThree = opThree;
    }

    public String getOpFour() {
        return opFour;
    }

    public void setOpFour(String opFour) {
        this.opFour = opFour;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    public Question(String ques, String opOne, String opTwo, String opThree, String opFour, String ans) {
        this.ques = ques;
        this.opOne = opOne;
        this.opTwo = opTwo;
        this.opThree = opThree;
        this.opFour = opFour;
        this.ans = ans;
    }

    public Question() {

    }
}
