package edu.nine14.exam.dao;

import edu.nine14.exam.entity.Direction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface DirectionRepository extends JpaRepository<Direction, Integer> {
    @Query(value = "select d from Direction d where d.profession.professionID=?1")
    List<Direction> selectDirectionByProfessionId(Integer professionId);

    @Query(value = "select d.directionID from Direction d where d.directionName=?1 and d.profession.professionID=?2")
    Optional<Integer> getDirectionIdByName(String directionName, Integer professionId);
}
