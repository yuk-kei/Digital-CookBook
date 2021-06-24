package chefOnly.model;

import javafx.scene.image.Image;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Yukkei Ho
 */

public class Recipe implements Serializable {

    private int recipeID;
    private String recipeName;
    private String flavour;
    private int serveNumber;
    private int prepTime;
    private int cookTime;
    private String imagePath = "images/Slow cooked Beef.png";
    private Image image;

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

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
     * changing the quantities of the ingredients according to the serving number
     * @param serveNum the serving number give by user or the software's default value
     */
    public void changeQuantity(int serveNum) {
        int currentNum = getServeNumber();
        for (Ingredient i : ingredients) {
            i.setQuantity(i.getQuantity() / currentNum * serveNum);
        }
        this.setServeNumber(serveNum);
    }

    /**
     * getting the names of ingredients' names
     * @return string of names of ingredients
     */
    public String toGetIngredients() {
        StringBuffer toStringIngredients = new StringBuffer();
        for (Ingredient item : ingredients) {
            StringBuffer itemReal = new StringBuffer();
            itemReal.append(item.getIngredientName());
            itemReal.append("   " + item.getQuantity());
            itemReal.append(" " + item.getUnit());
            itemReal.append("      " + item.getDescription());
            toStringIngredients.append(itemReal);
            toStringIngredients.append("\r\n");
        }
        return toStringIngredients.toString();
    }

    /**
     * getting the preparation step
     * @return string of the preparation steps
     */
    public String toGetPreparationStep() {
        StringBuffer toStringPreparationStep = new StringBuffer();
        for (int i = 0; i < preparationStep.size(); i++) {
            String item = preparationStep.get(i);
            int m = i + 1;
            toStringPreparationStep.append(m+": " + item);
            toStringPreparationStep.append("\r\n");
        }
        return toStringPreparationStep.toString();
    }

    /**
     * Override the toString method to give the Recipe information
     */
    @Override
    public String toString() {
        // To give the information of Name and Flavour
        String strNameandFlavour = "Name: " + this.getRecipeName() + "\n" + "Flavour: " + this.getFlavour() + "\n";

        // To give the ID of the Recipe
        String strRecipeID = "Recipe ID: " + this.recipeID + "\n";

        // This string is to give all steps of preparation
        String strPreparationStep = "Preparation Steps: \n";
        for (int i = 0; i < preparationStep.size(); i++) {
            String str = preparationStep.get(i);
            strPreparationStep = strPreparationStep + "Step " + (i + 1) + ": " + str + "\n";
        }


        // To give all the information of ingredients
        String strIngredients = "Ingredients: \n";
        for (Ingredient ingrePointer : ingredients) {
            // Using the override toString() method to change Ingredient into String
            strIngredients = strIngredients + ingrePointer;

        }

        // To give the preparation time of the recipe
        String strPreparationTime = "Preparation Time: " + this.prepTime + " min" + "\n";

        // To give the cooking time of the recipe
        String strCookingTime = "Cooking Time: " + this.prepTime + "\n";

        // To give the serving people of the recipe
        String strServingPeople = "Serving People:" + this.serveNumber + "\n";

        // Return all the recipe information
        return strNameandFlavour + strRecipeID + strIngredients + strPreparationStep + strPreparationTime
                + strCookingTime + strServingPeople ;
    }
}
