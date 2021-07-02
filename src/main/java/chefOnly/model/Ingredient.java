package chefOnly.model;

import java.text.NumberFormat;

/**
 * The model of the MVC structure
 * Storing the data of one single ingredient.
 *
 */
public class Ingredient {

    private final String ingredientName;
    private double quantity;
    private final String unit;
    private final String description ;

    public Ingredient(String ingredientName, double quantity, String unit, String description) {
        this.ingredientName = ingredientName;
        this.quantity = quantity;
        this.unit = unit;
        this.description = description;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public String getUnit() {
        return unit;
    }

    public String getDescription() {
        return description;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getQuantity() { return  quantity; }

    /**
     * Override the toString method to format the Ingredient information
     */
    @Override
    public String toString() {

        String formattedString;
        NumberFormat numberFormat =NumberFormat.getNumberInstance();

        formattedString = "⚪ " + this.ingredientName + ":  " + numberFormat.format(this.quantity) + " " + this.unit;

        if(!this.description.isEmpty()){
            formattedString = formattedString + "    \" " + this.description + " \" " + "\n";
        }else{
            formattedString = formattedString + "\n";
        }

        return formattedString;
    }
}

