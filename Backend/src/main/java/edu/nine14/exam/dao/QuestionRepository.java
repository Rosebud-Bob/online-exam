package edu.nine14.exam.dao;

import edu.nine14.exam.entity.DirectionSingle;
import edu.nine14.exam.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer>,
        JpaSpecificationExecutor<Question> {
    @Query(value = "select q.questionID,q.direction,q.body,q.answer,q.type from Question q where q.type=?1", nativeQuery = true)
    Optional<Question> selectQuestionByType(String type);

    @Transactional
    @Modifying
    @Query(value = "update Question q set q.type=?1,q.answer=?2,q.body=?3,q.direction=?4, " +
            "q.selection=?5 where q.questionID=?6")
    Optional<Question> editQuestion(String type, String answer, String body,
                                     Integer direction, String selection,Integer questionId);
}
