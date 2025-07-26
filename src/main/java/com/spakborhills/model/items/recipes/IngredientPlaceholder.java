package com.spakborhills.model.items.recipes;

import com.spakborhills.model.items.Item;

import java.util.Objects;

public class IngredientPlaceholder extends Item {
    public IngredientPlaceholder(String categoryName){
        super(categoryName);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IngredientPlaceholder that = (IngredientPlaceholder) o;
        return Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public String toString() {
        return "Category:" + getName();
    }
}
