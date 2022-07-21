package edu.nine14.exam.entity;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Date;
import java.util.Map;

@Entity
@Table(name = "question_bank")
public class Question {
    @Id
    @Column(name = "id", columnDefinition = "int")
    private Integer questionID;


    @Column(name = "direction_id", columnDefinition = "int")
    private Integer direction;



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

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
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
