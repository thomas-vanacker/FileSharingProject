package com.example.thomas.filesharingproject.quiz;

import java.util.ArrayList;

/**
 * Created by Thomas on 01/06/2015.
 */
public class Question {
    public String intitulate;
    public ArrayList<Answer> answer_list = new ArrayList<>();

    public Question(ArrayList<Answer> answer_list, String intitulate) {
        this.answer_list = answer_list;
        this.intitulate = intitulate;
    }

    public String getIntitulate() {
        return intitulate;
    }

    public void setIntitulate(String intitulate) {
        this.intitulate = intitulate;
    }

    public ArrayList<Answer> getAnswer_list() {
        return answer_list;
    }

    public void setAnswer_list(ArrayList<Answer> answer_list) {
        this.answer_list = answer_list;
    }
    public void addAnswer(Answer answer){
        ArrayList<Answer> arrayList = this.getAnswer_list();
        arrayList.add(0,answer);
        this.setAnswer_list(arrayList);
    }
}
