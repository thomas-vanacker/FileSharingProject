package com.example.thomas.filesharingproject.quiz;

import java.util.ArrayList;

/**
 * Created by Thomas on 01/06/2015.
 */
public class Quiz {
    private String title;
    private ArrayList<Question> question_list = new ArrayList<>();

    public Quiz(String title, ArrayList<Question> question_list) {
        this.setTitle(title);
        this.setQuestion_list(question_list);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Question> getQuestion_list() {
        return question_list;
    }

    public void setQuestion_list(ArrayList<Question> question_list) {
        this.question_list = question_list;
    }

    public void addQuestion(Question question){
        ArrayList<Question> arrayList = this.getQuestion_list();
        arrayList.add(question);
        this.setQuestion_list(arrayList);
    }

}
