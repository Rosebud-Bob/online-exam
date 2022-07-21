package edu.nine14.exam.entity;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Date;
import java.util.Map;

@Entity
@Table(name = "question_bank")
public class QuestionDirection {
    @Id
    @Column(name = "id", columnDefinition = "int")
    private Integer questionID;

    @JoinColumn
    @ManyToOne(cascade=CascadeType.ALL)
    private Direction direction;

    @Column(name = "body",columnDefinition = "text")
    private String body;

    @Column(name = "answer",columnDefinition = "char")
    private String answer;

    @Column(name = "type",columnDefinition = "char")
    private String type;

    @Column(name = "selection",columnDefinition = "char")
    private String selection;

    public Integer getQuestionID() {
        return questionID;
    }

    public void setQuestionID(Integer questionID) {
        this.questionID = questionID;
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

    public String getSelection() {
        return selection;
    }

    public void setSelection(String selection) {
        this.selection = selection;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public QuestionReceive toQuestionReceive(){
        QuestionReceive questionReceive = new QuestionReceive();
        String[] letter = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N"};
        //将选项用Option对象来封装
        String[] selectionTemp = null;
        Option[] option = null;
        if(selection!=null){
            selectionTemp = selection.split("[ $]+");
            option = new Option[selectionTemp.length];
            System.out.println(selectionTemp.length);
            for(int i=0;i<selectionTemp.length;i++){
                option[i] = new Option();
                option[i].setContent(selectionTemp[i]);
                option[i].setPrefix(letter[i]);
            }
        }

        questionReceive.setQuestionID(questionID);
        questionReceive.setDirection(direction);
        questionReceive.setAnswer(answer);
        questionReceive.setBody(body);
        questionReceive.setSelection(option);
        questionReceive.setType(type);
        return questionReceive;
    }
    /*public Question(String body, String[] option){
        this.body=body;
        this.option=option;
    }*/

    /*@Override
    public boolean equals(Object obj){
        if(this==obj){
            return true;
        }
        if(obj instanceof Question){
            Question anotherQue=(Question)obj;
            if(this.body.equals(anotherQue.body)){
                return true;
            }
        }
        return false;
    }*/
}
