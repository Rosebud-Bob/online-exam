package edu.nine14.exam.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import edu.nine14.exam.entity.DirectionSingle;
import edu.nine14.exam.entity.Profession;
import edu.nine14.exam.dao.ProfessionRepository;
import edu.nine14.exam.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.security.auth.login.FailedLoginException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProfessionService {
    @Autowired
    private ProfessionRepository professionRepository;

    public Integer findProfessionID(String profession){
        return (int)professionRepository.findByProfessionName(profession);
    }

    public Object selectById(Integer professionID){
        Optional<Profession> profession = professionRepository.findById(professionID);
        if (profession.isEmpty())
            throw new IllegalArgumentException("Question not found");
        return profession.get();
    }
}
