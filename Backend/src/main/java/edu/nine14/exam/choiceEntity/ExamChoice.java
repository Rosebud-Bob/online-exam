package edu.nine14.exam.choiceEntity;

import java.util.ArrayList;

public class ExamChoice {
    private String profession;
    private boolean has_direction;
    private ArrayList<String> directions;
    private Integer single_choice_num;
    private Integer multiple_choice_num;
    private Integer true_false_num;
    private Integer short_answer_num;

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public boolean isHas_direction() {
        return has_direction;
    }

    public void setHas_direction(boolean has_direction) {
        this.has_direction = has_direction;
    }

    public ArrayList<String> getDirections() {
        return directions;
    }

    public void setDirections(ArrayList<String> directions) {
        this.directions = directions;
    }

    public Integer getSingle_choice_num() {
        return single_choice_num;
    }

    public void setSingle_choice_num(Integer single_choice_num) {
        this.single_choice_num = single_choice_num;
    }

    public Integer getMultiple_choice_num() {
        return multiple_choice_num;
    }

    public void setMultiple_choice_num(Integer multiple_choice_num) {
        this.multiple_choice_num = multiple_choice_num;
    }

    public Integer getTrue_false_num() {
        return true_false_num;
    }

    public void setTrue_false_num(Integer true_false_num) {
        this.true_false_num = true_false_num;
    }

    public Integer getShort_answer_num() {
        return short_answer_num;
    }

    public void setShort_answer_num(Integer short_answer_num) {
        this.short_answer_num = short_answer_num;
    }
}
