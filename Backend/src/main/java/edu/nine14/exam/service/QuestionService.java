package edu.nine14.exam.service;


import edu.nine14.exam.entity.Option;
import edu.nine14.exam.entity.Question;
import edu.nine14.exam.dao.QuestionRepository;
import edu.nine14.exam.entity.QuestionReceive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    public Object getAllQuestion(){
        return questionRepository.findAll();
    }

    public Object selectById(Integer questionID){
        Optional<Question> question = questionRepository.findById(questionID);
        if (question.isEmpty())
            throw new IllegalArgumentException("Question not found");
        return question.get();
    }

    public Object selectByType(String type){
        Optional<Question> question = questionRepository.selectQuestionByType(type);
        if (question.isEmpty())
            throw new IllegalArgumentException("Question not found");
        return question;
    }

    public void editQuestion(QuestionReceive questionReceive){
        StringBuilder selection = new StringBuilder();
        Option[] tempSelection = questionReceive.getSelection();
        for(Option temp:tempSelection){
            selection.append(temp.getContent());
            //每个选项中间使用美元符号分隔
            selection.append("$");
        }
        Optional<Question> question = questionRepository.findById(questionReceive.getQuestionID());
        if (question.isEmpty())
            throw new IllegalArgumentException("Question not found");
        else{
            question.get().setAnswer(questionReceive.getAnswer());
            question.get().setBody(questionReceive.getBody());
            question.get().setDirection(questionReceive.getDirection().getDirectionID());
            question.get().setType(questionReceive.getType());
            question.get().setSelection(selection.toString());
            questionRepository.save(question.get());
        }
    }

    public void deleteQuestion(Integer questionID){
        Optional<Question> question = questionRepository.findById(questionID);
        if (question.isEmpty())
            throw new IllegalArgumentException("Question not found");
        questionRepository.delete(question.get());
    }

    public void addQuestion(QuestionReceive questionReceive){
        StringBuilder selection = new StringBuilder();
        Option[] tempSelection = questionReceive.getSelection();
        for(Option temp:tempSelection){
            selection.append(temp.getContent());
            //每个选项中间使用美元符号分隔
            selection.append("$");
        }
        try {
            Question question = new Question();
            //自动生成QuestionId
            question.setQuestionID((int) questionRepository.count());
            question.setSelection(selection.toString());
            question.setType(questionReceive.getType());
            question.setDirection(questionReceive.getDirection().getDirectionID());
            question.setBody(questionReceive.getBody());
            question.setAnswer(questionReceive.getAnswer());
            questionRepository.save(question);
        }catch (Exception e){
            throw e;
        }

    }
}
