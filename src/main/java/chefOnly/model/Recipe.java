package chefOnly.model;

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
    private String imagePath = "images/Slow cooked Beef.png";

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

    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }

    public void addPreparationStep(String preparation) {
        this.preparationStep.add(preparation);
    }

    public void setPrepTime(int prepTime) {
        this.prepTime = prepTime;
    }

    public void setCookTime(int cookTime) {
        this.cookTime = cookTime;
    }

    /**
     * calculate the quantities of the ingredients according to the serving amount
     * @param serveNum the serving amount
     */
    public void changeQuantity(int serveNum) {
        int currentNum = getServeNumber();
        for (Ingredient i : ingredients) {
            i.setQuantity(i.getQuantity() / currentNum * serveNum);
        }
        this.setServeNumber(serveNum);
    }

    /**
     *  Get the formatted ingredients' names
     * @return formatted string of names of ingredients
     */
    public String getFormattedIngredients() {
        StringBuilder toStringIngredients = new StringBuilder();
        for (Ingredient item : ingredients) {
            String itemReal = item.getIngredientName() +
                    "   " + item.getQuantity() +
                    " " + item.getUnit() +
                    "      " + item.getDescription();
            toStringIngredients.append(itemReal);
            toStringIngredients.append("\r\n");
        }
        return toStringIngredients.toString();
    }

    /**
     * Get the formatted preparation step.
     * @return formatted string of the preparation steps
     */
    public String getFormattedPreparationStep() {
        StringBuilder toStringPreparationStep = new StringBuilder();
        for (int i = 0; i < preparationStep.size(); i++) {
            String item = preparationStep.get(i);
            int m = i + 1;
            toStringPreparationStep.append(m).append(": ").append(item);
            toStringPreparationStep.append("\r\n");
        }
        return toStringPreparationStep.toString();
    }

    /**
     * Override the toString method to format the Recipe information
     */
    @Override
    public String toString() {

        String formattedNameAndFlavour = "Name: " + this.getRecipeName() + "\n" + "Flavour: " + this.getFlavour() + "\n";
        String formattedRecipeID = "Recipe ID: " + this.recipeID + "\n";

        StringBuilder formattedPreparationStep = new StringBuilder("Preparation Steps: \n");
        for (int i = 0; i < preparationStep.size(); i++) {
            String str = preparationStep.get(i);
            formattedPreparationStep.append("Step ").append(i + 1).append(": ").append(str).append("\n");
        }

        StringBuilder formattedIngredients = new StringBuilder("Ingredients: \n");
        for (Ingredient ingredient : ingredients) {
            formattedIngredients.append(ingredient);
        }

        String formattedPreparationTime = "Preparation Time: " + this.prepTime + " min" + "\n";
        String formattedCookingTime = "Cooking Time: " + this.prepTime + "\n";
        String formattedServingPeople = "Serving People:" + this.serveNumber + "\n";

        return formattedNameAndFlavour + formattedRecipeID + formattedIngredients + formattedPreparationStep + formattedPreparationTime
                + formattedCookingTime + formattedServingPeople ;
    }
}
