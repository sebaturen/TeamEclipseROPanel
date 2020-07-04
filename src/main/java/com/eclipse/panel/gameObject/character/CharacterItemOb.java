package com.eclipse.panel.gameObject.character;

import com.eclipse.panel.dbConnect.DBLoadObject;
import com.eclipse.panel.gameObject.Enchant;
import com.eclipse.panel.gameObject.roDB.Item;
import com.google.gson.JsonArray;

import java.util.Arrays;
import java.util.List;

public class CharacterItemOb {

    // ItemOb DB
    public static final String TABLE_NAME = "character_item_ob";
    public static final String TABLE_KEY = "id";

    // Attributes
    private int id;
    private int item_id;
    private int refine;
    private JsonArray carts;
    private JsonArray enchants;
    private int very_very_count;

    // In
    private List<Item> lCarts;
    private List<Enchant> lEnchants;

    public static class Builder extends DBLoadObject {

        private int id;
        public Builder(int id) {
            super(TABLE_NAME, CharacterItemOb.class);
            this.id = id;
        }

        public CharacterItemOb build() {
            return (CharacterItemOb) load(TABLE_KEY, id);
        }

    }

    private CharacterItemOb() {

    }

    @Override
    public String toString() {
        return "{\"_class\":\"ItemOb\", " +
                "\"id\":\"" + id + "\"" + ", " +
                "\"item_id\":\"" + item_id + "\"" + ", " +
                "\"refine\":\"" + refine + "\"" + ", " +
                "\"carts\":" + (carts == null ? "null" : carts) + ", " +
                "\"enchants\":" + (enchants == null ? "null" : enchants) + ", " +
                "\"very_very_count\":\"" + very_very_count + "\"" + ", " +
                "\"lCarts\":" + (lCarts == null ? "null" : Arrays.toString(lCarts.toArray())) + ", " +
                "\"lEnchants\":" + (lEnchants == null ? "null" : Arrays.toString(lEnchants.toArray())) +
                "}";
    }

}
