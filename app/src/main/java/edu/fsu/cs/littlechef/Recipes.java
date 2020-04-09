package edu.fsu.cs.littlechef;

import java.util.ArrayList;
import java.util.List;

public class Recipes {

    String id;
    String recipeName;
    int timeTaken;
    List<String> ingredients;
    List<String> steps;

    public Recipes() {}

    public Recipes(String id, String name, int time, List<String> ingredients, List<String> steps) {
        this.id = id;
        this.recipeName = name;
        this.timeTaken = time;
        this.ingredients = new ArrayList<>(ingredients);
        this.steps = new ArrayList<>(steps);
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public List<String> getSteps() {
        return steps;
    }

    public String getId() {
        return id;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public int getTimeTaken() {
        return timeTaken;
    }

}
