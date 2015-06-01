package com.example.thomas.filesharingproject.quiz;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thomas on 01/06/2015.
 */
public class Quizes {
    private static Quizes singleton;
    private List<Quiz> quizes = new ArrayList<>();

    public static Quizes getSingleton() {
        return singleton;
    }

    public static void setSingleton(Quizes singleton) {
        Quizes.singleton = singleton;
    }

    public List<Quiz> getQuizes() {
        return this.quizes;
    }

    public void setQuizes(List<Quiz> quizes) {
        this.quizes = quizes;
    }

}
