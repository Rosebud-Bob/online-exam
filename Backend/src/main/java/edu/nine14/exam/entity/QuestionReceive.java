package edu.nine14.exam.entity;

/**
 * 从前端发送来的题目的json数据，使用该对象接收
 */
public class QuestionReceive {
    private Integer questionID;
    private Direction direction;
    private String body;
    private String answer;
    private String type;
    private Option[] selection;

    public Integer getQuestionID() {
        return questionID;
    }

    public void setQuestionID(Integer questionID) {
        this.questionID = questionID;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Option[] getSelection() {
        return selection;
    }

    public void setSelection(Option[] selection) {
        this.selection = selection;
    }

    @Override
    public String toString(){
        //System.out.println(selection);
        return questionID + " " + direction + " " + body + " " + answer + " " + type ;
    }
}

