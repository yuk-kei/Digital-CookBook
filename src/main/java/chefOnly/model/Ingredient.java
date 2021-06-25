package chefOnly.model;

import java.text.DecimalFormat;

/**
 * The model of the MVC structure
 * Storing the data of one single ingredient.
 * @author Yuqi Ho
 */
public class Ingredient {

    private String ingredientName;
    private double quantity;
    private String unit = "g";
    private String description = "";

    public Ingredient(String ingredientName, double quantity, String unit, String description) {
        this.ingredientName = ingredientName;
        this.quantity = quantity;
        this.unit = unit;
        this.description = description;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) { this.ingredientName = ingredientName; }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit){
        this.unit = unit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description){
        this.description=description;
    }

    public double getQuantity() {
        DecimalFormat decimalFormat = new DecimalFormat( "0.00");
        return Double.parseDouble(decimalFormat.format(quantity));
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    /**
     * Override the toString method to format the Ingredient information
     */
    @Override
    public String toString() {

        String formattedString;
        formattedString = " " + this.ingredientName + ": " + this.quantity + " " + this.unit;

        if(!this.description.equals("None")){
            formattedString = formattedString + "     Description: " + this.description + "\n";
        }else{
            formattedString = formattedString + "\n";
        }

        return formattedString;
    }
}

