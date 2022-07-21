package edu.nine14.exam.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "profession")
public class Profession {
    @Id
    @Column(name = "id", columnDefinition = "int")
    private Integer professionID;

    @Column(name = "name", columnDefinition = "char")
    private String professionName;

    /*@OneToMany(mappedBy="profession_id")
    private List<Direction> directions;*/

    public Integer getProfessionID() {
        return professionID;
    }

    public void setProfessionID(Integer professionID) {
        this.professionID = professionID;
    }

    public String getProfessionName() {
        return professionName;
    }

    public void setProfessionName(String professionName) {
        this.professionName = professionName;
    }

   /* public List<Direction> getDirections() {
        return directions;
    }

    public void setDirections(List<Direction> directions) {
        this.directions = directions;
    }*/
}
