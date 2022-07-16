package edu.nine14.exam.dao;

import edu.nine14.exam.entity.DirectionSingle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface DirectionSingleRepository extends JpaRepository<DirectionSingle, Integer> {
    @Transactional
    @Modifying
    @Query(value = "update DirectionSingle d set d.directionName=?1, d.professionId=?2 where d.directionID=?3")
    Optional<DirectionSingle> editDirection(String directionName, Integer professionId, Integer directionId);
}