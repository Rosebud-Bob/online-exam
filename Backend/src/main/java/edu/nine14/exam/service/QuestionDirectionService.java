package edu.nine14.exam.service;

import edu.nine14.common.ApiResult;
import edu.nine14.common.HttpCode;
import edu.nine14.exam.dao.QuestionDirectionRepository;
import edu.nine14.exam.entity.Question;
import edu.nine14.exam.entity.QuestionDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class QuestionDirectionService {
    @Autowired
    private QuestionDirectionRepository questionDirectionRepository;

    public Object getAllQuestion(){
        return questionDirectionRepository.findAll();
    }

    public Object selectById(Integer questionDirectionID){
        Optional<QuestionDirection> questionDirection = questionDirectionRepository.findById(questionDirectionID);
        if (questionDirection.isEmpty())
            throw new IllegalArgumentException("Question not found");
        return questionDirection.get();
    }

    /**
     * 根据专业方向返回题目列表
     * @param professionID 专业ID
     * @param type 题目类型
     * @return
     */
    public Object questionForProfession(Integer professionID,String type){
        return questionDirectionRepository.questionForProfession(professionID,type);
    }

    public Object questionForDirection(Integer directionID,String type,String direction1,String direction2){
        return questionDirectionRepository.questionForDirection(directionID,type,direction1,direction2);
    }

    /**
     * 随机返回查询的题目
     * @param num 题目数量
     * @param max_num 题库中该种题目的最大数量
     * @param questionList 该种题目的题目列表
     * @return
     */
    public List<Object> returnQuestion(Integer num,Integer max_num,List<Object> questionList){
        List<Object> questions=new ArrayList<>();
        Random random = new Random();
        Integer[] question_num = new Integer[num];
        Integer next_question;
        for (int i = 0; i < num; i++) {
            next_question = random.nextInt(max_num);
            int j;
            for (j = 0; j < i; j++) {
                if (question_num[j] == next_question)
                    break;
            }
            if (j == i)
                question_num[i] = next_question;
            else
                i--;
        }

        for (int i = 0; i < num; i++) {
            questions.add(questionList.get(question_num[i]));
        }

        return questions;
    }

    /**
     * 按照所需要的条件进行查找
     * @param page 页数，默认第0页
     * @param size 每页显示的条数
     * @param direction 专业方向
     * @param id 问题id
     * @param profession 专业
     * @param type 题目类型
     * @return 返回符合条件的所有题目
     */
    public Page<QuestionDirection> findByCondition(Integer page, Integer size, String direction,
                                                   Integer profession,Integer id, String type){
        Pageable pageable = PageRequest.of(page-1, size);
        return questionDirectionRepository.findAll((root, criteriaQuery, criteriaBuilder)->{
            List<Predicate> predicates = new ArrayList<Predicate>();
            if(direction!=null){
                predicates.add(criteriaBuilder.equal(root.get("direction").get("directionName"),direction));
            }
            if(id!=null){
                predicates.add(criteriaBuilder.equal(root.get("questionID"),id));
            }
            if(profession!=null){
                predicates.add(criteriaBuilder.equal(root.get("direction").get("profession").get("professionID"),profession));
            }
            if(type!=null){
                predicates.add(criteriaBuilder.equal(root.get("type"),type));
            }
            return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
        },pageable);
    }

}