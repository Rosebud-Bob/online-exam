package edu.nine14.exam.dao;

import edu.nine14.exam.entity.QuestionDirection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface QuestionDirectionRepository extends JpaRepository<QuestionDirection, Integer>,
        JpaSpecificationExecutor<QuestionDirection> {
    @Query(value="select question_bank.id,question_bank.body,question_bank.answer,question_bank.type,question_bank.selection from question_bank,direction,profession where question_bank.direction_id=direction.id and direction.profession_id=profession.id and profession.id=?1 and question_bank.type=?2",nativeQuery = true)
    //@Query(value="select q.questionID,q.body,q.answer,q.type,q.selection from QuestionDirection q where q.direction.profession.professionID=?1 and q.type=?2")
    List<Map<String,Object>> questionForProfession(Integer directionID, String type);

    @Query(value="select question_bank.id,question_bank.body,question_bank.answer,question_bank.type,question_bank.selection from question_bank,direction,profession where question_bank.direction_id=direction.id and direction.profession_id=profession.id and profession.id=?1 and question_bank.type=?2 and direction.name IN (?3,?4)",nativeQuery = true)
    //@Query(value="select q.questionID,q.body,q.answer,q.type,q.selection from QuestionDirection q where q.direction.profession.professionID=?1 and q.type=?2 and q.direction.directionName IN (?3,?4)")
    List<Map<String,Object>> questionForDirection(Integer directionID,String type,String direction1,String direction2);
}