package chefOnly.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

/**
 * The model of the MVC structure
 * The Recipe model, used as a mediate to communicate among database, controllers and view page.
 *
 */

public class Recipe {

    private int recipeID;
    private String recipeName;
    private String flavour;
    private int serveNumber;
    private int prepTime;
    private int cookTime;
    private String imagePath = "images/chef.png";

    private ArrayList<Ingredient> ingredients = new ArrayList<>();

    private ArrayList<String> preparationStep = new ArrayList<>();

    public Recipe(String name, String flavour ,int serveNumber, int prepTime,int cookTime) {

        this.recipeName = name;
        this.flavour = flavour;
        this.prepTime = prepTime;
        this.serveNumber = serveNumber;
        this.cookTime = cookTime;

    }

    public Recipe(int recipeID, String recipeName, String flavour, int serveNumber, int prepTime, int cookTime, String imagePath, ArrayList<Ingredient> ingredients, ArrayList<String> preparationStep) {
        this.recipeID = recipeID;
        this.recipeName = recipeName;
        this.flavour = flavour;
        this.serveNumber = serveNumber;
        this.prepTime = prepTime;
        this.cookTime = cookTime;
        this.imagePath = imagePath;
        this.ingredients = ingredients;
        this.preparationStep = preparationStep;
    }

    public Recipe() {}


    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void setRecipeID(int recipeID) {
        this.recipeID = recipeID;
    }

    public void setServeNumber(int serveNumber) {
        this.serveNumber = serveNumber;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public void setFlavour(String flavour) {
        this.flavour = flavour;
    }

    public void setPreparationStep(ArrayList<String> preparationStep) {
        this.preparationStep = preparationStep;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public int getRecipeID() {
        return recipeID;
    }

    public int getServeNumber() {
        return serveNumber;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getFlavour() {
        return flavour;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public ArrayList<String> getPreparationStep() {
        return preparationStep;
    }

    public int getPrepTime() {
        return prepTime;
    }

    public int getCookTime() {
        return cookTime;
    }

    public void setPrepTime(int prepTime) {
        this.prepTime = prepTime;
    }

    public void setCookTime(int cookTime) {
        this.cookTime = cookTime;
    }

    /**
     * Calculate the quantities of the ingredients according to the serving amount
     * @param serveNumber the serving amount
     */
    public void calculateQuantity(int serveNumber) {
        int people = getServeNumber();
        BigDecimal bigDecimal;

        for (Ingredient ingredient : ingredients){
            bigDecimal = new BigDecimal(ingredient.getQuantity() / people * serveNumber);
            ingredient.setQuantity(bigDecimal.setScale(2, RoundingMode.HALF_UP).doubleValue());
        }
        this.setServeNumber(serveNumber);
    }

    /**
     * Get the formatted ingredients information. (Easily to be shown in the view Window)
     * @return formatted string of names of ingredients
     */
    public String getFormattedIngredients() {
        StringBuilder formattedIngredients = new StringBuilder();
        for (Ingredient ingredient : ingredients) {
            formattedIngredients.append(ingredient).append("\n");
        }
        return formattedIngredients.toString();
    }

    /**
     * Get the formatted preparation steps. (Easily to be shown in the view Window)
     * @return formatted string of the preparation steps
     */
    public String getFormattedPreparationStep() {
        StringBuilder formattedPreparationStep = new StringBuilder();
        for (int i = 0; i < preparationStep.size(); i++) {
            formattedPreparationStep.append("Step ").append(i + 1).append(":  ").append(preparationStep.get(i)).append("\n").append("\n");
        }
        return formattedPreparationStep.toString();
    }

    /**
     * Override the toString method to format the Recipe information( for print to test )
     */
    @Override
    public String toString() {

        String formattedNameAndFlavour = "Name: " + this.getRecipeName() + "\n" + "Flavour: " + this.getFlavour() + "\n";
        String formattedRecipeID = "Recipe ID: " + this.recipeID + "\n";
        String formattedPreparationTime = "Preparation Time: " + this.prepTime + " min" + "\n";
        String formattedCookingTime = "Cooking Time: " + this.prepTime + "\n";
        String formattedServingPeople = "Serving People:" + this.serveNumber + "\n";
        String formattedIngredients = "Ingredients: \n" + this.getIngredients() + "\n";
        String formattedPreparationStep = "Preparation Steps: \n" + this.getFormattedPreparationStep() + "\n";

        return formattedNameAndFlavour + formattedRecipeID + formattedServingPeople + formattedPreparationTime + formattedCookingTime  + formattedIngredients + formattedPreparationStep;
    }
}
