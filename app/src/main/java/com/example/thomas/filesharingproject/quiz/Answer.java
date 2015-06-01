package com.example.thomas.filesharingproject.quiz;

/**
 * Created by Thomas on 01/06/2015.
 */
public class Answer {
    public String answer;
    public Boolean good_answer;

    public Answer(String answer, Boolean good_answer) {
        this.answer = answer;
        this.good_answer = good_answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Boolean isGood_answer() {
        return good_answer;
    }

    public void setGood_answer(Boolean good_answer) {
        this.good_answer = good_answer;
    }
}
