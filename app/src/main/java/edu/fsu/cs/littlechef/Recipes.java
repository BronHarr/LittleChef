package edu.fsu.cs.littlechef;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Recipes implements Serializable {

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

    public void setId(String id) {
        this.id = id;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public void setTimeTaken(int timeTaken) {
        this.timeTaken = timeTaken;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
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
