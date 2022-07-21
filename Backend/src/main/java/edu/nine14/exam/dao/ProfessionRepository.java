package edu.nine14.exam.dao;

import edu.nine14.exam.entity.Profession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@Repository
public interface ProfessionRepository extends CrudRepository<Profession, Integer> {
    @Query("select p.professionID from Profession p where p.professionName=?1")
    Object findByProfessionName(String professionName);
}