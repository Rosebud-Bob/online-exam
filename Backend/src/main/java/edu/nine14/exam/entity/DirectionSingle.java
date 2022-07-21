package edu.nine14.exam.entity;

import javax.persistence.*;

@Entity
@Table(name = "direction")
public class DirectionSingle {
    @Id
    @Column(name = "id", columnDefinition = "int")
    private Integer directionID;

    @Column(name = "name", columnDefinition = "char")
    private String directionName;

    @Column(name = "profession_id", columnDefinition = "int")
    private Integer professionId;

    public DirectionSingle(Integer directionID,String directionName,Integer professionId){
        this.directionID = directionID;
        this.directionName = directionName;
        this.professionId = professionId;
    }

    public DirectionSingle() {
        this.directionID = null;
        this.directionName = null;
        this.professionId = null;
    }

    public Integer getDirectionID() {
        return directionID;
    }

    public void setDirectionID(Integer directionID) {
        this.directionID = directionID;
    }

    public String getDirectionName() {
        return directionName;
    }

    public void setDirectionName(String directionName) {
        this.directionName = directionName;
    }

    public Integer getProfessionId() {
        return professionId;
    }

    public void setProfessionId(Integer professionId) {
        this.professionId = professionId;
    }
}
