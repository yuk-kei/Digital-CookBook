package chefOnly.model;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class IngredientTest {

    Ingredient ingredient;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        ingredient = new Ingredient("black pepper salt",5,"g","important ingredient");
    }
    @Test
    void getQuantity() {
        assertEquals(ingredient.getQuantity(),5);
    }

    @Test
    void testToString() {
        System.out.println(ingredient);
    }
}